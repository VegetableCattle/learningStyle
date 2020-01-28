package com.ysu.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.hadoop.hive.ql.parse.HiveParser_IdentifiersParser.stringLiteralSequence_return;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ysu.dao.WifiDao;
import com.ysu.util.DateUtil;
import com.ysu.util.Json;
import com.ysu.util.Point;
import com.ysu.util.localToHbase;

@Controller
@RequestMapping("/wifi/")
public class WifiController {
	@RequestMapping("/upload.action")
	public Json upload(MultipartFile file, HttpSession session, HttpServletRequest request) throws Exception {
		Json json = new Json();

		localToHbase.pickwords(file.getOriginalFilename());

		// System.out.println(filedate);
		// InputStream in = file.getInputStream();

		// hadoopDB.upload(in, dir+"/"+fileBean.getName());

		// in.close();
		json.setSuccess(true);
		return json;
	}
	
	@RequestMapping("/jiSuan.action")
	public String jiSuan(HttpSession session) throws Exception {
		String startDate = (String) session.getAttribute("startDate");
		String endDate = (String) session.getAttribute("endDate");
		if (startDate != null && startDate != "" && endDate != null && endDate != "") {
			WifiDao wifiDao=new WifiDao();
			wifiDao.setxIsDates(startDate, endDate);
			wifiDao.setxIsMac(startDate, endDate);
			wifiDao.setSjpp(startDate, endDate);
			
			String strS = startDate.substring(8);
			String strE = endDate.substring(8);
			for (int i = Integer.valueOf(strS); i <= Integer.valueOf(strE); i++) {
				String nowDate = "2017-10-" + i;
			wifiDao.setZdsc(nowDate);
			wifiDao.setLgk(nowDate);
			}
		}
		return "redirect:/jsp/index.jsp";
	}
	
	
	
	
	
	
	
	@RequestMapping("/setSession.action")
	public String setSession(String startDate,String endDate,HttpSession session) throws Exception {
		if(startDate!=null&&startDate!=""&&endDate!=null&&endDate!=""){
			session.setAttribute("startDate",startDate );
			session.setAttribute("endDate",endDate );
			String nowDate=startDate;
			session.setAttribute("nowDate",nowDate );
		}
		
		
		return "redirect:/jsp/index.jsp";
	}
	
	@RequestMapping("/setSession2.action")
	public String setSession2(String nowDate, HttpSession session) throws Exception {
		Json json = new Json();
		if (nowDate != null && nowDate != "") {
			session.setAttribute("nowDate", nowDate);
		}
		return "redirect:/jsp/index.jsp";
	}

	@RequestMapping("/setSeleDate.action")
	@ResponseBody
	public Json setSeleDate(HttpSession session, HttpServletRequest request) throws Exception {
		Json json = new Json();
		String startDate = (String) session.getAttribute("startDate");
		String endDate = (String) session.getAttribute("endDate");
		String nowDate = (String) session.getAttribute("nowDate");
		System.out.println(startDate);
		System.out.println(endDate);
		System.out.println(nowDate);
		if (startDate != null && startDate != "" && endDate != null && endDate != "") {

			String strS = startDate.substring(8);
			String strE = endDate.substring(8);
			ArrayList<String> dateList = new ArrayList<String>();
			ArrayList<Point> pList = new ArrayList<Point>();

			for (int i = Integer.valueOf(strS); i <= Integer.valueOf(strE); i++) {
				String strDate = "2017-10-" + i;
				Point point = new Point();

				// x为日期，y为客流量，y1为入店量，y2为入店率，y3跳出率，y4深访率
				point.setX("2017-10-" + i);
				pList.add(point);
				dateList.add(strDate);
				// System.out.println(strDate);
			}

			json.setMsg("sele");
			json.setObj(dateList);
			/*
			 * String str[]=new String[dateList.size()]; int i=0; for (String
			 * string : dateList) { str[i]=string; i++;
			 * System.out.println(string); } json.setObj(str);
			 */
			json.setSuccess(true);
			if (nowDate != null && nowDate != "") {
				String seleDate = nowDate;
				request.setAttribute("seleDate", seleDate);
				System.out.println(seleDate);
			} else {
				String seleDate = startDate;
				request.setAttribute("seleDate", seleDate);
				System.out.println(seleDate);
			}
		}
		return json;
	}

	/*
	 * @RequestMapping("/setSession2.action") public String setSession2(String
	 * nowDate,HttpSession session) throws Exception {
	 * session.setAttribute("nowDate", nowDate); return "jsp/"; }
	 */
	/**
	 * 
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 *             计算客流量 入店量 入店率 横坐标都为时间
	 */
	@RequestMapping("/showLiuLiangData.action")
	@ResponseBody
	public Json showLiuLiangData(HttpSession session, HttpServletRequest request) throws Exception {

		String startDate = (String) session.getAttribute("startDate");
		String endDate = (String) session.getAttribute("endDate");

		Json json = new Json();

		if (startDate != null && startDate != "" && endDate != null && endDate != "") {
			ArrayList<Point> pList = new ArrayList<Point>();
			WifiDao wifiDao = new WifiDao();
			pList=wifiDao.getxIsDates(startDate, endDate);
			/*String strS = startDate.substring(8);
			String strE = endDate.substring(8);

			for (int i = Integer.valueOf(strS); i <= Integer.valueOf(strE); i++) {
				String strDate = "2017-10-" + i + "-00:00:00";
				Date date = DateUtil.StringToDate(strDate);
				int ll = wifiDao.getKeLiuLiang(date);
				int rdl = wifiDao.getRuDianLiang(date);
				double rdlv = wifiDao.getRuDianLv(date);

				double tcl = wifiDao.getTiaoChuLv(date);
				double sfl = wifiDao.getShenFangLv(date);

				Point point = new Point();
				// x为日期，y为客流量，y1为入店量，y2为入店率，y3跳出率，y4深访率
				point.setX("2017-10-" + i);
				point.setY(ll);
				point.setY1(rdl);
				point.setY2(rdlv);

				point.setY3(tcl);
				point.setY4(sfl);

				pList.add(point);
			}*/
			json.setObj(pList);

			json.setSuccess(true);
		} else {
			json.setSuccess(false);
		}
		return json;
	}

	/**
	 * 
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 *             计算来访周期 活跃度 横坐标为mac
	 */
	@RequestMapping("/showLaifangData.action")
	@ResponseBody
	public Json showLaifangData(HttpSession session, HttpServletRequest request) throws Exception {

		String startDate = (String) session.getAttribute("startDate");
		String endDate = (String) session.getAttribute("endDate");

		Json json = new Json();

		if (startDate != null && startDate != "" && endDate != null && endDate != "") {
			WifiDao wifiDao = new WifiDao();
			ArrayList<Point> pList = wifiDao.getxIsMac(startDate, endDate);
			json.setObj(pList);
			json.setSuccess(true);
		} else {
			json.setSuccess(false);
		}
		return json;
	}
	/*@RequestMapping("/showLaifangData.action")
	@ResponseBody
	public Json showLaifangData(HttpSession session, HttpServletRequest request) throws Exception {

		String startDate = (String) session.getAttribute("startDate");
		String endDate = (String) session.getAttribute("endDate");

		Json json = new Json();

		if (startDate != null && startDate != "" && endDate != null && endDate != "") {
			Date sdate = DateUtil.StringToDate(startDate + "-00:00:00");
			Date edate = DateUtil.StringToDate(endDate + "-00:00:00");

			ArrayList<Point> pList = new ArrayList<Point>();
			WifiDao wifiDao = new WifiDao();
			List<String> macList = wifiDao.getMac(startDate, endDate);
			for (String mac : macList) {
				long lfzq = wifiDao.getLaiFangZhouQi(mac, sdate, edate);
				double hyd = wifiDao.HuoYueDu(mac, sdate, edate);
				Point point = new Point();
				point.setX(mac);
				DecimalFormat df = new DecimalFormat("#.000");  
		        //System.out.println(df.format(lfzq/1000/60));  
				point.setY(Double.valueOf(df.format(lfzq/1000/360)));
				point.setY1(Double.valueOf(hyd));
				pList.add(point);
			}
			json.setObj(pList);
			json.setSuccess(true);
		} else {
			json.setSuccess(false);
		}
		return json;
	}*/
	@RequestMapping("/showShiChangData.action")
	@ResponseBody
	public Json showShiChangData(HttpSession session, HttpServletRequest request) throws Exception {

		String nowDate = (String) session.getAttribute("nowDate");

		Json json = new Json();
		if (nowDate != null && nowDate != "") {
			WifiDao wifiDao = new WifiDao();
			ArrayList<Point> pList = wifiDao.getZdsc(nowDate);
			json.setObj(pList);
			json.setSuccess(true);
		} else {
			json.setSuccess(false);
		}
		return json;
	}
	/*@RequestMapping("/showShiChangData.action")
	@ResponseBody
	public Json showShiChangData(HttpSession session, HttpServletRequest request) throws Exception {

		String nowDate = (String) session.getAttribute("nowDate");

		Json json = new Json();
		if (nowDate != null && nowDate != "") {
			Date ndate = DateUtil.StringToDate(nowDate + "-00:00:00");
			WifiDao wifiDao = new WifiDao();
			List<String> macList = wifiDao.getMac(nowDate, nowDate);
			ArrayList<Point> pList = new ArrayList<Point>();

			for (String mac : macList) {
				long sc = wifiDao.getShiChang(mac, ndate);
				DecimalFormat df = new DecimalFormat("#.000");  
		        System.out.println(Double.valueOf(df.format(sc/1000/360)));  
		        
				Point point = new Point();
				if(Double.valueOf(df.format(sc/1000/360))<=24) {
					point.setX(mac);
					point.setY(Double.valueOf(df.format(sc/1000/360)));
					pList.add(point);
				}
			}
			json.setObj(pList);
			json.setSuccess(true);
		} else {
			json.setSuccess(false);
		}
		return json;
	}*/
	@RequestMapping("/showLaoGuKeData.action")
	@ResponseBody
	public Json showLaoGuKeData(HttpSession session, HttpServletRequest request) throws Exception {
		String nowDate = (String) session.getAttribute("nowDate");
		Json json = new Json();
		if (nowDate != null && nowDate != "") {
			WifiDao wifiDao = new WifiDao();
			Point point = wifiDao.getLgk(nowDate);
			json.setObj(point);
			json.setSuccess(true);
		}
		else {
			json.setSuccess(false);
		}
		return json;
	}
	/*@RequestMapping("/showLaoGuKeData.action")
	@ResponseBody
	public Json showLaoGuKeData(HttpSession session, HttpServletRequest request) throws Exception {

		String nowDate = (String) session.getAttribute("nowDate");

		Json json = new Json();

		if (nowDate != null && nowDate != "") {

			Date ndate = DateUtil.StringToDate(nowDate + "-00:00:00");
			WifiDao wifiDao = new WifiDao();
			List<String> macList = wifiDao.getMac(nowDate, nowDate);
			// y为老顾客数量，y1为新顾客数量
			int y = 0;
			int y1 = 0;

			for (String mac : macList) {
				boolean ilgk = wifiDao.isLaoGuKe(mac, ndate);
				if (ilgk) {
					y++;
				} else {
					y1++;
				}
			}
			Point point = new Point();
			point.setY(y);
			point.setY(y1);

			json.setObj(point);
			json.setSuccess(true);
		}

		else {
			json.setSuccess(false);
		}
		return json;
	}*/
	@RequestMapping("/showPinPaiData.action")
	@ResponseBody
	public Json showPinPaiData(HttpSession session, HttpServletRequest request) throws Exception {

		String startDate = (String) session.getAttribute("startDate");
		String endDate = (String) session.getAttribute("endDate");
		Json json = new Json();

		if (startDate != null && startDate != "" && endDate != null && endDate != "") {
			
			WifiDao wifiDao = new WifiDao();
			ArrayList<Point> pList = wifiDao.getSjpp(startDate, endDate);
			json.setObj(pList);
			json.setSuccess(true);
		} else {
			json.setSuccess(false);
		}
		return json;
	}
	/*@RequestMapping("/showPinPaiData.action")
	@ResponseBody
	public Json showPinPaiData(HttpSession session, HttpServletRequest request) throws Exception {

		String startDate = (String) session.getAttribute("startDate");
		String endDate = (String) session.getAttribute("endDate");
		Json json = new Json();

		if (startDate != null && startDate != "" && endDate != null && endDate != "") {
			ArrayList<Point> pList = new ArrayList<Point>();
			WifiDao wifiDao = new WifiDao();
			List<String> macList = wifiDao.getMac(startDate, endDate);
			ArrayList<String> ppList = new ArrayList<String>();
			for (String mac : macList) {
				String pp = wifiDao.getPinPai(mac);

				if (!ppList.contains(pp)) {
					if(pp != "NaN") {
						ppList.add(pp);
						Point point = new Point();
						point.setX(pp);
						point.setY(1);
						pList.add(point);
					}
				} else {
					for (Point pt : pList) {
						if (pp.equals(pt.getX())) {
							pt.setY(pt.getY() + 1);
						}
					}
				}
			}
			json.setObj(pList);
			json.setSuccess(true);
		} else {
			json.setSuccess(false);
		}
		return json;
	}*/
}
