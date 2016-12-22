package com.modana.manage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.modana.manage.alsmc.ExeUppaal;
import com.modana.manage.util.ResponseData;

@Controller
@RequestMapping(value = "/dalsmc")
public class DalSmcController extends BaseController {


	@RequestMapping(value = "/Sampling")
	@ResponseBody
	public Object Sampling(HttpServletRequest request,Integer learnTraceNum,
			Integer extractTraceNum,Double extractTraceProbability) {
		ExeUppaal.exe(learnTraceNum,extractTraceNum,extractTraceProbability);
		return ResponseData.newSuccess();
}
	@RequestMapping(value = "/ClientTrace")
	@ResponseBody
	public Object ClientTrace(HttpServletRequest request,Integer TraceNum,
			Integer extractTraceNum,Double extractTraceProbability) {
		List<Integer> traceSet = ExeUppaal.clientTraceFlag(TraceNum, extractTraceNum, extractTraceProbability);
		return traceSet;
}
	@RequestMapping(value = "/finalResult")
	@ResponseBody
	public Object finalResult(ArrayList<Integer> traArrayList) {
		return ExeUppaal.getFinalResult(traArrayList);
}
}
