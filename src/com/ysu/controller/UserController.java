package com.ysu.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/user/")
//会话范围，窗口在值一直存在 有时间
@SessionAttributes(value="username")
public class UserController {
	
	@Autowired
	private UserDao ud;

	@RequestMapping("login.action")
	public ModelAndView login(String username,String password,HttpServletRequest request){
		System.out.println(username);
		System.out.println(password);
		//UserDao ud=new UserDao();
		UserBean userBean=new UserBean();
		boolean result=ud.login(username, password);
		ModelAndView mv=new ModelAndView("redirect:/jsp/index.jsp");
		if(result){
			mv.addObject("str", "success");
			userBean.setUserId(ud.getIdByName(username));
			userBean.setUserName(username);
			userBean.setUserPassWord(password);
		}
		else{
			mv.addObject("str", "defeated");
		}
		//mv.addObject("userBean", userName);
		request.getSession().setAttribute(Constants.SESSION_USER_BEAN, userBean);
		System.out.println(userBean);
		mv.addObject("username", username);
		
		return mv;//代表的是，跳转的视图名   /success.jsp
	}
	@RequestMapping("register.action")
	public ModelAndView register(String username,String password){
		//System.out.println(u);
		//UserDao ud=new UserDao();
		System.out.println(username);
		System.out.println(password);
		boolean result=ud.insert(username,password);
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("message", username);
		mv.addObject("str1", "success");
		mv.setViewName("sign-up3");
		
		return mv;
	}
	@RequestMapping("checkName.action")
	@ResponseBody
	public String checkName(String username){
		System.out.println(username);
		//UserDao ud=new UserDao();

		boolean result=ud.checkName(username);
		System.out.println(result);
		if(result==false){
		return "success";
		}
		else{
			return "error";
		}
	}
}
