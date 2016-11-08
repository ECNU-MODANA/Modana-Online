package com.wls.manage.entity;

import java.util.Date;
/**
 * 消息分类实体类（现在数据库中使用字符串，暂未使用该实体类）
 * @author jiangkaiqiang
 * @version 创建时间：2016-10-21 下午2:11:11 
 *
 */
public class MsgCategoryEntity {
	private int id;
	private String categoryname;//分类名称
	private int orderid;//上级分类id
	private Date inserttime;//添加时间
	private String categorypic = "app/img/food.jpg";//消息分类对应图片
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public Date getInserttime() {
		return inserttime;
	}
	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}
	public String getCategorypic() {
		return categorypic;
	}
	public void setCategorypic(String categorypic) {
		this.categorypic = categorypic;
	}
}
