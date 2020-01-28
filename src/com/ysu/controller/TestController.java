package com.ysu.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ysu.dao.UserDao;
import com.ysu.model.UserBean;
import com.ysu.util.Constants;
import com.ysu.util.Json;




//@Repository
//@Service
@Controller
@RequestMapping("/test/")
public class TestController {
	@RequestMapping("try1.action")
	@ResponseBody
	public Json try1(Model model){
		ArrayList<Integer> row1=new ArrayList<Integer>();
		Json json=new Json();
		int []row=new int[10];
		for(int i=0;i<10;i++){
			row[i]=i;
			row1.add(i);
		}
		json.setSuccess(true);
		json.setMsg("row");
		json.setObj(row1);
		//model.addAttribute("row", row);
		//model.addAttribute("row1", row1);
		return json;
	}
	@RequestMapping("try2.action")
	public ModelAndView try2(){
		ArrayList<Integer> row1=new ArrayList<Integer>();
		int []row=new int[10];
		for(int i=0;i<10;i++){
			row[i]=i;
			row1.add(i);
		}
		ModelAndView mv=new ModelAndView();
		mv.addObject("row1", row1);
		mv.setViewName("test");
		
		return mv;
	}
}
