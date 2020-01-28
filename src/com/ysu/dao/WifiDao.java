package com.ysu.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.ysu.db.HBaseDB;
import com.ysu.db.HiveDB;
import com.ysu.model.WifiInfoBean;
import com.ysu.model.macInfoBean;
import com.ysu.util.Constants;
import com.ysu.util.DateUtil;
import com.ysu.util.Point;

public class WifiDao {

	public static void main(String[] args) {
		WifiDao wifiDao = new WifiDao();
		String mac = "b0:e5:ed:bb:b1:6f";
		Date date = DateUtil.StringToDate("2017-10-14-00:00:00");
		Date startdate = DateUtil.StringToDate("2017-10-10-00:00:00");
		Date enddate = DateUtil.StringToDate("2017-10-15-00:00:00");

		/*
		 * List<WifiInfoBean> wifis = wifiDao.getAllMac(date); for (WifiInfoBean
		 * wifiInfoBean : wifis) { System.out.println(wifiInfoBean.toString());
		 * }
		 */

		/*
		 * List<String> wifis = wifiDao.getMac(date); for (String string :
		 * wifis) { System.out.println(string); }
		 */
		/*
		 * int res = wifiDao.getKeLiuLiang(date); System.out.println(res);
		 */
		/*
		 * int res = wifiDao.getRuDianLiang(date); System.out.println(res);
		 */
		// wifiDao.getRuDianLv(date);

		// 有错！！！！
		long res = wifiDao.getLaiFangZhouQi(mac, startdate, enddate);
		System.out.println(res);

		/*
		 * boolean res = wifiDao.isLaoGuKe(mac, date); System.out.println(res);
		 */

		// .....
		/*
		 * double res = wifiDao.getHuoYueDu(mac, startdate, enddate);
		 * System.out.println(res);
		 */

		/*
		 * long res = wifiDao.getShiChang(mac, date);
		 * System.out.println(DateUtil.longToString("yyyy-MM-dd-HH:mm:ss",
		 * res));
		 */

		/*
		 * mac = "2a:09:9a"; String res = wifiDao.getPinPai(mac);
		 * System.out.println(res);
		 */

		/*
		 * double res = wifiDao.getTiaoChuLv(date); System.out.println(res);
		 */

		/*
		 * double res = wifiDao.getShenFangLv(date); System.out.println(res);
		 */

		// wifiDao.isShenFangLiang(mac, date);

	}

	public boolean insert(WifiInfoBean wifiInfoBean) {
		long id = 0;
		try {
			id = HBaseDB.getId(Constants.HBASE_TABLE_WIFIGID, Constants.HBASE_ROW_KEY_WIFIGID_INFOID,
					Constants.HBASE_FAMILY_WIFIGID_WIFIGID, Constants.HBASE_COLUMN_WIFIGID_INFOID);
			HBaseDB.add(Constants.HBASE_TABLE_WIFIINFO, id, Constants.HBASE_FAMILY_WIFIINFO_WIFIINFO,
					Constants.HBASE_COLUMN_WIFIINFO_LOGTIME, wifiInfoBean.getLogtime());
			HBaseDB.add(Constants.HBASE_TABLE_WIFIINFO, id, Constants.HBASE_FAMILY_WIFIINFO_WIFIINFO,
					Constants.HBASE_COLUMN_WIFIINFO_SIGNAL, wifiInfoBean.getSignal());
			HBaseDB.add(Constants.HBASE_TABLE_WIFIINFO, id, Constants.HBASE_FAMILY_WIFIINFO_WIFIINFO,
					Constants.HBASE_COLUMN_WIFIINFO_MAC, wifiInfoBean.getMac());
			HBaseDB.add(Constants.HBASE_TABLE_WIFIINFO, id, Constants.HBASE_FAMILY_WIFIINFO_WIFIINFO,
					Constants.HBASE_COLUMN_WIFIINFO_WIFINAME, wifiInfoBean.getWifiname());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 
	 * @param date
	 *            一般是以这个date到date+1天的数据
	 * @return
	 */

	// 获取到date后一个小时的mac地址，包括mac重复数据，因为到时候要通过这段数据的时间计算别的数据
	public List<WifiInfoBean> getAllMac(Date date) {
		List<WifiInfoBean> wifiInfoBeans = new ArrayList<>();
		String sql = "select * from wifiinfo";
		try {
			ResultSet res = null;
			Statement statement = HiveDB.getStmt();
			res = statement.executeQuery(sql);
			while (res.next()) {
				String tmpdate = res.getString(1);
				if (DateUtil.StringToDate(tmpdate).getTime() >= date.getTime()
						&& DateUtil.StringToDate(tmpdate).getTime() / 1000 - date.getTime() / 1000 <= 3600 * 24) {
					WifiInfoBean wifiInfoBean = new WifiInfoBean();
					wifiInfoBean.setLogtime(res.getString(1));
					wifiInfoBean.setSignal(res.getInt(2));
					wifiInfoBean.setMac(res.getString(3));
					wifiInfoBean.setWifiname(res.getString(4));
					wifiInfoBeans.add(wifiInfoBean);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wifiInfoBeans;
	}

	public List<String> getMac(String startDate, String endDate) {
		List<String> mac = new ArrayList<>();
		String strS = startDate.substring(8);
		String strE = endDate.substring(8);
		for (int i = Integer.valueOf(strS); i <= Integer.valueOf(strE); i++) {
			String strDate = "2017-10-" + i + "-00:00:00";
			Date date = DateUtil.StringToDate(strDate);
			List<WifiInfoBean> wifiInfoBeans = getAllMac(date);
			for (WifiInfoBean wifiInfoBean : wifiInfoBeans) {
				if (!mac.contains(wifiInfoBean.getMac())) {
					mac.add(wifiInfoBean.getMac());
				}
			}
		}

		return mac;
	}

	// 获取到七个计算的结果
	// 计算客流量
	public int getKeLiuLiang(Date date) {
		List<WifiInfoBean> wifiInfoBeans = getAllMac(date);
		List<String> mac = new ArrayList<>();
		for (WifiInfoBean wifiInfoBean : wifiInfoBeans) {
			if (!mac.contains(wifiInfoBean.getMac())) {
				mac.add(wifiInfoBean.getMac());
			}
		}
		return mac.size();
	}

	// 入店量
	public int getRuDianLiang(Date date) {
		List<WifiInfoBean> wifiInfoBeans = getAllMac(date);
		List<String> mac = new ArrayList<>();
		for (WifiInfoBean wifiInfoBean : wifiInfoBeans) {
			if (wifiInfoBean.getSignal() > 30 && !mac.contains(wifiInfoBean.getMac())) {
				mac.add(wifiInfoBean.getMac());
			}
		}
		return mac.size();
	}

	// 获取入店率，入店量/客流量
	public double getRuDianLv(Date date) {
		return (double) getRuDianLiang(date) / getKeLiuLiang(date);
	}

	// 来访周期
	public long getLaiFangZhouQi(String mac, Date startdate, Date enddate) {
		String sql = "select * from wifiinfo where mac = " + "\'" + mac + "\'";
		List<Long> ret = new ArrayList<>();
		try {
			ResultSet res = null;
			Statement statement = HiveDB.getStmt();
			res = statement.executeQuery(sql);
			// && getShiChang(mac, DateUtil.StringToDate(tmpdate)) / 1000 > 3600
			while (res.next()) {
				String tmpdate = res.getString(1);
				if (!ret.contains(DateUtil.StringToDate(res.getString(1)).getTime())
						&& DateUtil.StringToDate(tmpdate).getTime() >= startdate.getTime()
						&& DateUtil.StringToDate(tmpdate).getTime() < enddate.getTime()) {
					ret.add(DateUtil.StringToDate(res.getString(1)).getTime());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		long sum = 0;
		if (ret.size() <= 1) {
			return 0;
		}
		for (int i = 1; i < ret.size(); i++) {
			sum += ret.get(i) - ret.get(i - 1);
		}
		return sum / (ret.size() - 1);
	}

	// 新老顾客，true是老顾客，false是新顾客
	public boolean isLaoGuKe(String mac, Date date) {
		String sql = "select * from wifiinfo where mac = " + "\'" + mac + "\'";
		try {
			ResultSet res = null;
			Statement statement = HiveDB.getStmt();
			res = statement.executeQuery(sql);
			while (res.next()) {
				String tmpdate = res.getString(1);
				if (DateUtil.StringToDate(tmpdate).getTime() < date.getTime()) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 顾客活跃度，来访周期/1000的倒数
	public double HuoYueDu(String mac, Date startdate, Date enddate) {
		return (double) 1 / (getLaiFangZhouQi(mac, startdate, enddate) / 1000);
	}

	// 驻店时长，返回ms数据而不是时间格式
	public long getShiChang(String mac, Date date) {
		String sql = "select * from wifiinfo where mac = " + "\'" + mac + "\' and signal > 30";
		List<WifiInfoBean> wifiInfoBeans = getAllMac(date);
		Map<String, Long> map = new HashMap<>();
		try {
			ResultSet res = null;
			Statement statement = HiveDB.getStmt();
			res = statement.executeQuery(sql);
			while (res.next()) {
				long t = 0;
				if ((t = DateUtil.StringToDate(res.getString(1)).getTime() - date.getTime()) >= 0) {
					map.put(mac, t);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (map.isEmpty()) {
			map.put(mac, (long) 0);
		}
		return map.get(mac);
	}

	// 手机品牌，解析mac地址前三位
	public String getPinPai(String mac) {
		macInfoBean macInfoBean = new macInfoBean();
		macInfoBean = macDao.getMacInfo(mac.substring(0, 8));
		// System.out.println(mac.substring(0, 8) + macInfoBean.getProductor());
		return macInfoBean.getProductor();
	}

	// 跳出率,为一个地点的数据
	public double getTiaoChuLv(Date date) {
		Map<String, Boolean> map = new HashMap<>();
		long sum = 0;
		List<WifiInfoBean> wifis = getAllMac(date);
		for (WifiInfoBean wifiInfoBean : wifis) {
			if (!map.containsKey(wifiInfoBean.getMac())) {
				if (!isShenFangLiang(wifiInfoBean.getMac(), date)) {
					map.put(wifiInfoBean.getMac(), true);
					sum++;
				}
				map.put(wifiInfoBean.getMac(), false);
			}
		}
		return (double) sum / getRuDianLiang(date);
	}

	// 深访率
	public double getShenFangLv(Date date) {
		Map<String, Boolean> map = new HashMap<>();
		long sum = 0;
		List<WifiInfoBean> wifis = getAllMac(date);
		for (WifiInfoBean wifiInfoBean : wifis) {
			if (!map.containsKey(wifiInfoBean.getMac())) {
				if (isShenFangLiang(wifiInfoBean.getMac(), date)) {
					map.put(wifiInfoBean.getMac(), true);
					sum++;
				}
				map.put(wifiInfoBean.getMac(), false);
			}
		}
		return (double) sum / getRuDianLiang(date);
	}

	// 判断mac是否为深访量，驻店时长大于1个小时算深访
	public boolean isShenFangLiang(String mac, Date date) {
		return (getShiChang(mac, date) / 1000) >= 3600;
	}
	public boolean setxIsDates(String startDate, String endDate) {
			String strS = startDate.substring(8);
			String strE = endDate.substring(8);

			for (int i = Integer.valueOf(strS); i <= Integer.valueOf(strE); i++) {
				String strDate = "2017-10-" + i + "-00:00:00";
				Date date = DateUtil.StringToDate(strDate);
				int kll = getKeLiuLiang(date);
				int rdl =getRuDianLiang(date);
				double rdlv =getRuDianLv(date);

				double tcl =getTiaoChuLv(date);
				double sfl =getShenFangLv(date);
				// x为日期，y为客流量，y1为入店量，y2为入店率，y3跳出率，y4深访率
				
				String rowkey=startDate+"_"+endDate+"_"+"2017-10-" + i;
				try {
					HBaseDB.add(Constants.HBASE_TABLE_XISDATES, rowkey, Constants.HBASE_FAMILY_XISDATES_XISDATES,
							Constants.HBASE_COLUMN_XISDATES_KELUILIANG, kll);
					HBaseDB.add(Constants.HBASE_TABLE_XISDATES, rowkey, Constants.HBASE_FAMILY_XISDATES_XISDATES,
							Constants.HBASE_COLUMN_XISDATES_RUDIANLIANG, rdl);
					HBaseDB.add(Constants.HBASE_TABLE_XISDATES, rowkey, Constants.HBASE_FAMILY_XISDATES_XISDATES,
							Constants.HBASE_COLUMN_XISDATES_RUDIANLV, rdlv);
					HBaseDB.add(Constants.HBASE_TABLE_XISDATES, rowkey, Constants.HBASE_FAMILY_XISDATES_XISDATES,
							Constants.HBASE_COLUMN_XISDATES_TIAOCHULV, tcl);
					HBaseDB.add(Constants.HBASE_TABLE_XISDATES, rowkey, Constants.HBASE_FAMILY_XISDATES_XISDATES,
							Constants.HBASE_COLUMN_XISDATES_SHENFANGLV, sfl);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return true;
	}
	public boolean setxIsMac(String startDate, String endDate) {
			Date sdate = DateUtil.StringToDate(startDate + "-00:00:00");
			Date edate = DateUtil.StringToDate(endDate + "-00:00:00");

			List<String> macList = getMac(startDate, endDate);
			for (String mac : macList) {
				String rowkey=startDate+"_"+endDate+"_"+"2017-10-" + mac;
				
				
				long lfzq = getLaiFangZhouQi(mac, sdate, edate);
				double hyd = HuoYueDu(mac, sdate, edate);
				
				
				Point point = new Point();
				point.setX(mac);
				DecimalFormat df = new DecimalFormat("#.000");  
		        //System.out.println(df.format(lfzq/1000/60));  
				point.setY(Double.valueOf(df.format(lfzq/1000/360)));
				point.setY1(Double.valueOf(hyd));
				
				try {
					HBaseDB.add(Constants.HBASE_TABLE_XISMAC, rowkey, Constants.HBASE_FAMILY_XISMAC_XISMAC,
							Constants.HBASE_COLUMN_XISMAC_LAIFANGZHOUQI, point.getY());
					HBaseDB.add(Constants.HBASE_TABLE_XISMAC, rowkey, Constants.HBASE_FAMILY_XISMAC_XISMAC,
							Constants.HBASE_COLUMN_XISMAC_HUOYUEDU, point.getY1());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			return true;
	}
	public boolean setZdsc(String nowDate) {
			Date ndate = DateUtil.StringToDate(nowDate + "-00:00:00");
			List<String> macList = getMac(nowDate, nowDate);
			for (String mac : macList) {
				long sc = getShiChang(mac, ndate);
				DecimalFormat df = new DecimalFormat("#.000");  
				
				Point point = new Point();
				if(Double.valueOf(df.format(sc/1000/360))<=24) {
					point.setX(mac);
					point.setY(Double.valueOf(df.format(sc/1000/360)));
					String rowkey=nowDate+ mac;
					try {
						HBaseDB.add(Constants.HBASE_TABLE_ZHUDIANSHICHANG, rowkey, Constants.HBASE_FAMILY_ZHUDIANSHICHANG_ZHUDIANSHICHANG,
								Constants.HBASE_COLUMN_ZHUDIANSHICHANG_ZHUDIANSHICHANG, point.getY());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return true;
	}
	public boolean setLgk(String nowDate) {
		Date ndate = DateUtil.StringToDate(nowDate + "-00:00:00");
		List<String> macList = getMac(nowDate, nowDate);
		int laoguke = 0;
		int xinguke = 0;

		for (String mac : macList) {
			boolean ilgk = isLaoGuKe(mac, ndate);
			if (ilgk) {
				laoguke++;
			} else {
				xinguke++;
			}
		}
		String rowkey=nowDate;
		try {
			HBaseDB.add(Constants.HBASE_TABLE_LAOFUKE, rowkey, Constants.HBASE_FAMILY_LAOFUKE_LAOFUKE,
					Constants.HBASE_COLUMN_LAOFUKE_LAOFUKE, laoguke);
			HBaseDB.add(Constants.HBASE_TABLE_LAOFUKE, rowkey, Constants.HBASE_FAMILY_LAOFUKE_LAOFUKE,
					Constants.HBASE_COLUMN_LAOFUKE_XINGUKE, xinguke);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
}
	public boolean setSjpp(String startDate, String endDate) {
		ArrayList<Point> pList = new ArrayList<Point>();
		List<String> macList = getMac(startDate, endDate);
		ArrayList<String> ppList = new ArrayList<String>();
		for (String mac : macList) {
			String pp = getPinPai(mac);

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
		for (Point pt : pList) {
			String rowkey=startDate+"_"+endDate+"_"+"2017-10-" + pt.getX();
			try {
				HBaseDB.add(Constants.HBASE_TABLE_SHOUJIPINPAI, rowkey, Constants.HBASE_FAMILY_SHOUJIPINPAI_SHOUJIPINPAI,
						Constants.HBASE_COLUMN_SHOUJIPINPAI_SHOUJIPINPAI, pt.getY());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return true;
}
	public ArrayList<Point> getxIsDates(String startDate, String endDate) {
			String strS = startDate.substring(8);
			String strE = endDate.substring(8);
			ArrayList<Point> pList = new ArrayList<Point>();
			
			for (int i = Integer.valueOf(strS); i <= Integer.valueOf(strE); i++) {
				String rowkey=startDate+"_"+endDate+"_"+"2017-10-" + i;
				Table table;
				try {
					table = HBaseDB.getConn().getTable(TableName.valueOf(Constants.HBASE_TABLE_XISDATES));
			
				Get get = new Get(Bytes.toBytes(rowkey));
				get.addFamily(Bytes.toBytes(Constants.HBASE_FAMILY_XISDATES_XISDATES));
				
				Result result = table.get(get);
				if(!result.isEmpty()) {
					Point point = new Point();
					point.setX("2017-10-" + i);
					point.setY(Bytes.toInt(result.getValue(Bytes.toBytes(Constants.HBASE_FAMILY_XISDATES_XISDATES), Bytes.toBytes(Constants.HBASE_COLUMN_XISDATES_KELUILIANG))));
					point.setY1(Bytes.toInt(result.getValue(Bytes.toBytes(Constants.HBASE_FAMILY_XISDATES_XISDATES), Bytes.toBytes(Constants.HBASE_COLUMN_XISDATES_RUDIANLIANG))));
					point.setY2(Bytes.toDouble(result.getValue(Bytes.toBytes(Constants.HBASE_FAMILY_XISDATES_XISDATES), Bytes.toBytes(Constants.HBASE_COLUMN_XISDATES_RUDIANLV))));
					point.setY3(Bytes.toDouble(result.getValue(Bytes.toBytes(Constants.HBASE_FAMILY_XISDATES_XISDATES), Bytes.toBytes(Constants.HBASE_COLUMN_XISDATES_TIAOCHULV))));
					point.setY4(Bytes.toDouble(result.getValue(Bytes.toBytes(Constants.HBASE_FAMILY_XISDATES_XISDATES), Bytes.toBytes(Constants.HBASE_COLUMN_XISDATES_SHENFANGLV))));
					pList.add(point);
				}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return pList;
	}
	public ArrayList<Point> getxIsMac(String startDate, String endDate) {

			List<String> macList = getMac(startDate, endDate);
			ArrayList<Point> pList = new ArrayList<Point>();
			for (String mac : macList) {
				String rowkey=startDate+"_"+endDate+"_"+"2017-10-" + mac;
				Table table;
				try {
					table = HBaseDB.getConn().getTable(TableName.valueOf(Constants.HBASE_TABLE_XISMAC));
				
				Get get = new Get(Bytes.toBytes(rowkey));
				get.addFamily(Bytes.toBytes(Constants.HBASE_FAMILY_XISMAC_XISMAC));
				
				Result result = table.get(get);
				if(!result.isEmpty()) {	
				Point point = new Point();
				point.setX(mac);
		        //System.out.println(df.format(lfzq/1000/60));  
				point.setY(Bytes.toDouble(result.getValue(Bytes.toBytes(Constants.HBASE_FAMILY_XISMAC_XISMAC), Bytes.toBytes(Constants.HBASE_COLUMN_XISMAC_LAIFANGZHOUQI))));
				point.setY1(Bytes.toDouble(result.getValue(Bytes.toBytes(Constants.HBASE_FAMILY_XISMAC_XISMAC), Bytes.toBytes(Constants.HBASE_COLUMN_XISMAC_HUOYUEDU))));
				pList.add(point);
				}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return pList;
	}
	public ArrayList<Point> getZdsc(String nowDate) {
		List<String> macList = getMac(nowDate, nowDate);
		ArrayList<Point> pList = new ArrayList<Point>();
		
		for (String mac : macList) {
			    
				String rowkey=nowDate+ mac;
				Table table;
				try {
					table = HBaseDB.getConn().getTable(TableName.valueOf(Constants.HBASE_TABLE_ZHUDIANSHICHANG));
				
				Get get = new Get(Bytes.toBytes(rowkey));
				get.addFamily(Bytes.toBytes(Constants.HBASE_FAMILY_ZHUDIANSHICHANG_ZHUDIANSHICHANG));
				
				Result result = table.get(get);
				if(!result.isEmpty()) {	
				Point point = new Point();
				point.setX(mac);
				point.setY(Bytes.toDouble(result.getValue(Bytes.toBytes(Constants.HBASE_FAMILY_ZHUDIANSHICHANG_ZHUDIANSHICHANG), Bytes.toBytes(Constants.HBASE_COLUMN_ZHUDIANSHICHANG_ZHUDIANSHICHANG))));
				pList.add(point);
				}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
		return pList;
}
public Point getLgk(String nowDate) {
	String rowkey=nowDate;
	Point point = new Point();
	Table table;
	try {
		table = HBaseDB.getConn().getTable(TableName.valueOf(Constants.HBASE_TABLE_LAOFUKE));
	
	Get get = new Get(Bytes.toBytes(rowkey));
	get.addFamily(Bytes.toBytes(Constants.HBASE_FAMILY_LAOFUKE_LAOFUKE));
	
	Result result = table.get(get);
	
	if(!result.isEmpty()) {	
	point.setY(Bytes.toDouble(result.getValue(Bytes.toBytes(Constants.HBASE_FAMILY_ZHUDIANSHICHANG_ZHUDIANSHICHANG), Bytes.toBytes(Constants.HBASE_COLUMN_LAOFUKE_LAOFUKE))));
	point.setY1(Bytes.toDouble(result.getValue(Bytes.toBytes(Constants.HBASE_FAMILY_ZHUDIANSHICHANG_ZHUDIANSHICHANG), Bytes.toBytes(Constants.HBASE_COLUMN_LAOFUKE_XINGUKE))));
	}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return point;
}
public ArrayList<Point> getSjpp(String startDate, String endDate) {
	List<String> macList = getMac(startDate, endDate);
	ArrayList<Point> pList = new ArrayList<Point>();
	
	for (String mac : macList) {
		    
		    String rowkey=startDate+"_"+endDate+"_"+"2017-10-" + mac;
			Table table;
			try {
			table = HBaseDB.getConn().getTable(TableName.valueOf(Constants.HBASE_TABLE_SHOUJIPINPAI));
			
			Get get = new Get(Bytes.toBytes(rowkey));
			get.addFamily(Bytes.toBytes(Constants.HBASE_FAMILY_SHOUJIPINPAI_SHOUJIPINPAI));
			
			Result result = table.get(get);
			if(!result.isEmpty()) {	
			Point point = new Point();
			point.setX(mac);
			point.setY(Bytes.toDouble(result.getValue(Bytes.toBytes(Constants.HBASE_FAMILY_SHOUJIPINPAI_SHOUJIPINPAI), Bytes.toBytes(Constants.HBASE_COLUMN_SHOUJIPINPAI_SHOUJIPINPAI))));
			pList.add(point);
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
	return pList;
}
}
