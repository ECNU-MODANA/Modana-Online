package com.wls.manage.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobao.api.ApiException;
import com.wls.manage.dao.FileDataMapper;
import com.wls.manage.dao.MessageMapper;
import com.wls.manage.dao.OrdersMapper;
import com.wls.manage.dao.RdcShareMapper;
import com.wls.manage.dao.UserMapper;
import com.wls.manage.dto.BaseDto;
import com.wls.manage.dto.OrdersDTO;
import com.wls.manage.dto.RdcShareDTO;
import com.wls.manage.entity.FileDataEntity;
import com.wls.manage.entity.MessageEntity;
import com.wls.manage.entity.OrdersEntity;
import com.wls.manage.entity.UserEntity;
import com.wls.manage.service.FtpService;
import com.wls.manage.service.RdcShareService;
import com.wls.manage.util.CometUtil;
import com.wls.manage.util.ResponseData;
import com.wls.manage.util.SessionUtil;
import com.wls.manage.util.SetUtil;
import com.wls.manage.util.StringUtil;
import com.wls.manage.util.TelephoneVerifyUtil;

@Controller
@RequestMapping(value = "/orders")
public class OrdersController extends BaseController {
	@Autowired
	private OrdersMapper orderDao;
	@Autowired
	private UserMapper userDao;
	@Autowired
	private RdcShareMapper rsmDao;
	@Autowired
	private MessageMapper msgDao;
    @Autowired
	private FileDataMapper fileDataDao;
    @Autowired
    private RdcShareService rdcShareService;
	@RequestMapping(value = "/findOrdersByUserId")
	@ResponseBody
	public Object findOrdersByUserId(@RequestParam int userID,
			@RequestParam int pageNum, @RequestParam int pageSize,
			@RequestParam(value = "keyword", required = false) String keyword) {
		PageHelper.startPage(pageNum, pageSize);
		if (keyword!=null) {
			keyword = keyword.equals("")? null:keyword ;
		}
		Page<OrdersEntity> ordersList = orderDao.findOrdersByPersonId(userID,keyword);
		Page<OrdersDTO> ordersDTOList = new Page<OrdersDTO>();
		for (int i = 0; i < ordersList.size(); i++) {
			OrdersDTO ordersDTO = new OrdersDTO();
			ordersDTO.setOrders(ordersList.get(i));
			RdcShareDTO rsd = rsmDao.getSEByID(""
					+ ordersList.get(i).getShareinfoid());
			if (rsd!=null) {
				List<FileDataEntity> files = this.fileDataDao.findByBelongIdAndCategory(rsd.getId(), FileDataMapper.CATEGORY_SHARE_PIC);
				if (files!=null) {
					 if(SetUtil.isnotNullList(files)){
							List<String> filelist =new ArrayList<String>();
							for (FileDataEntity file : files) {
								filelist.add(FtpService.READ_URL+file.getLocation());
							}
							ordersDTO.setFiles(filelist);
							ordersDTO.setLogo(filelist.get(0));
					} 
				}
			}
			ordersDTO.setRsd(rsd);
			ordersDTOList.add(ordersDTO);
			ordersDTOList.setTotal(ordersList.getTotal());
		}
		PageInfo<OrdersDTO> data = new PageInfo<OrdersDTO>(ordersDTOList);
		return ResponseData.newSuccess(data);
	}
	
	/**
	 * 
	 * @param orderID
	 * @return
	 */
	@RequestMapping(value = "/findOrderByOrderId")
	@ResponseBody
	public Object findOrderByOrderId(HttpServletRequest request,@RequestParam String id,Integer uid) {
		HashMap<String, Object> dataMap=new HashMap<String, Object>();
		if(uid==null||uid==0){
			UserEntity user =(UserEntity) SessionUtil.getSessionAttbuter(request, "user");//警告 ->调用该方法必须登录
			if(user==null||user.getId()==0){return ResponseData.newFailure("请登录后查看信息");}else{
				uid=user.getId();
			}
		}
		OrdersEntity oEntity = this.orderDao.findOrderByOrderId(Integer.parseInt(id),uid);	
		if(oEntity!=null){
		    UserEntity ownerUser = this.userDao.findUserById( oEntity.getOwnerid());
		    UserEntity orderUser = this.userDao.findUserById( oEntity.getUserid());
		    RdcShareDTO      rsd = this.rdcShareService.getSEByID(oEntity.getShareinfoid()+"");
		    ownerUser.setPassword(null);
		    orderUser.setPassword(null);
			dataMap.put("rsd", rsd);
			dataMap.put("orders", oEntity);
			dataMap.put("ownerUser", ownerUser);
			dataMap.put("orderUser", orderUser);
			return ResponseData.newSuccess(dataMap);
		}else{
			return ResponseData.newFailure("没有订单信息！");
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param user
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/generateOrder")
	@ResponseBody
	public Object generateOrder(HttpServletRequest request, int userid,
			String username, String telephone, String address, int rsdid, int dataType,
			String typeText, int releaseID, String title) {
		OrdersEntity order = new OrdersEntity();
		OrdersDTO ordersDTO = new OrdersDTO();
		try {
			Calendar calendar = Calendar.getInstance();
			order.setOrderid("" + calendar.getTime().getTime());
			String ordername = title;
			order.setOrdername(ordername);
			order.setOwnerid(releaseID);
			UserEntity owner = userDao.findUserById(releaseID);
			if (owner!=null&&StringUtil.isNull(owner.getTelephone())) {
				return ResponseData.newFailure("下单失败！发布者没有留下联系方式！");
			}
			order.setOwnername(owner.getUsername());
			order.setOwnertele(owner.getTelephone());
			order.setUserid(userid);
			order.setUsername(username);
			order.setUsertele(telephone);
			order.setShareinfoid(rsdid);
			RdcShareDTO rsd = rsmDao.getSEByID("" + rsdid);
			if(rsd==null){
				return ResponseData.newFailure("下单失败！当前发布信息已失效！");
			}
			List<FileDataEntity> files = this.fileDataDao.findByBelongIdAndCategory(rsd.getId(), FileDataMapper.CATEGORY_SHARE_PIC);
			if (rsd != null) {
			if(SetUtil.isnotNullList(files)){
					List<String> filelist =new ArrayList<String>();
					for (FileDataEntity file : files) {
						filelist.add(FtpService.READ_URL+file.getLocation());
					}
						ordersDTO.setFiles(filelist);
						ordersDTO.setLogo(filelist.get(0));
					}
					
			} 
			ordersDTO.setOrders(order);
			ordersDTO.setRsd(rsd);
			ordersDTO.setUseraddress(address);
			ordersDTO.setOwneraddress(owner.getAddress());
			orderDao.insertOrder(order);
			MessageEntity message = new MessageEntity();
			message.setUserid(order.getUserid());
			message.setMsgdata(order.getOrdername()+":您已经抢到来自"+order.getOwnername()+"的订单");
			message.setMsgcategory(1);
			message.setMsgcount(1);
			msgDao.insertMessage(message);
			new CometUtil().pushTo(message);
			// return ResponseData.newSuccess("验证码已发送到您的手机！请注意查收！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseData.newFailure("下单失败！当前发布信息已失效！");
		}
		OrdersDTO data = ordersDTO;
		return ResponseData.newSuccess(data);
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteByOrderID")
	public Object deleteByOrderID(Integer orderID) {
		if (orderID <= 0) {
			return new BaseDto(-1);
		}
		orderDao.deleteByOrderID(orderID);
		return new BaseDto(0);
	}

	
	@RequestMapping(value = "/getTelephone")
	@ResponseBody
	public Object getTelephone(@RequestParam int orderid,@RequestParam String ownerTele,@RequestParam String userTele,@RequestParam String ownerName,@RequestParam String userName)  {
		OrdersEntity order = orderDao.findOrderById(orderid);
		if (order!=null) {
			if (order.getTeletimes()<2) {
				TelephoneVerifyUtil tVerifyUtil = new TelephoneVerifyUtil();
				try {
					tVerifyUtil.callUser(userTele, userName, ownerTele, ownerName);
					tVerifyUtil.callOwner(userTele, userName, ownerTele, ownerName);
				} catch (ApiException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				order.setTeletimes(order.getTeletimes()+1);
				orderDao.updateOrderTimes(order);
				return ResponseData.newSuccess("对方联系人的手机号已经发送到您的手机，请及时联系！");
			}
			else {
				return ResponseData.newFailure("您已经获得过联系方式，不可重复获取，请查看手机短信！");
			}
		}
		else {
			return ResponseData.newFailure("获取联系方式失败！");
		}
	}

}
