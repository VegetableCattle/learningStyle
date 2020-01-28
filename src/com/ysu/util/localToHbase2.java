package com.ysu.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.apache.hadoop.hive.ql.parse.HiveParser_IdentifiersParser.stringLiteralSequence_return;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet.DL;

import com.ysu.dao.WifiDao;
import com.ysu.dao.learningDao;
import com.ysu.dao.macDao;
import com.ysu.model.LearningBean;
import com.ysu.model.WifiInfoBean;
import com.ysu.model.macInfoBean;

//璇诲彇婧愭暟鎹紝杩囨护锛屽瓨鍌紝鐢熸垚鏃ュ織
public class localToHbase2 {

	public static void main(String[] args) {
		//pickMac();
		String inputFileName="dl.txt";
		pickwords(inputFileName);
	}
	
	public static void pickwords(String inputFileName){
		String inputPath=Constants.PATH_INPUT+"\\"+inputFileName;
		File file = new File(inputPath);
		System.out.println(inputPath);
		//File file2 = new File(Constants.PATH_OUTPUT);
		
		//String filedate=inputFileName.substring(0,5);
		//String filedate=inputFileName.replaceAll(str, "-");
		//System.out.println(str);
		//System.out.println(filedate);
		try {
			/*if (!file2.exists()) {
				file2.createNewFile();
			}*/
			//FileWriter fw = new FileWriter(file2, true);
			//BufferedWriter bw = new BufferedWriter(fw);
			BufferedReader br = new BufferedReader(new FileReader(file));// 鏋勯�犱竴涓狟ufferedReader绫绘潵璇诲彇鏂囦欢
			String s = null;
			ArrayList<LearningBean>lList = new ArrayList<LearningBean>();
			for(int i=10001;i<=10250;i++){
				LearningBean learningBean=new LearningBean();
				String id=i+"";
				learningBean.setLearningId(id);
				learningBean.setDlyl("yl");
			}
			while ((s = br.readLine()) != null) {// 浣跨敤readLine鏂规硶锛屼竴娆¤涓�琛�
				// 鍦ㄨ繖鍒囧垎
				String[] tmpStr = s.split("	");
				//WifiInfoBean wifiInfoBean=new WifiInfoBean();
				LearningBean learningBean=new LearningBean();
				/*for (String string : tmpStr) {
					System.out.println(string);
				}*/
				
				// 濡傛灉鏄┖琛岋紝鍒欒烦杩�
				//System.out.println(tmpStr[2].equals("page view"));
				//System.out.println(tmpStr[2]=="page view");
				if(tmpStr[2].equals("page view")||tmpStr[2].equals("blog view")){
					//System.out.println(1);
			    learningBean.setLearningId(tmpStr[0]);
			    learningBean.setDlyl("dl");	
			    learningDao learning=new learningDao();
				learningDao.insertDlyl(learningBean);	
				//learningBean.toString();	
				/*String signal="";
				signal=tmpStr[8].replaceAll("-", "");
				signal=signal.replaceAll("dB", "");
				wifiInfoBean.setLogtime(filedate+"-"+tmpStr[0].substring(0,8));
				wifiInfoBean.setSignal(Integer.valueOf(signal));
				wifiInfoBean.setMac(tmpStr[14].substring(3));
				wifiInfoBean.setWifiname(tmpStr[19]);
				
				
				// 淇濆瓨鍒癶base
				WifiDao wifiDao=new WifiDao();
				wifiDao.insert(wifiInfoBean);
				
				// 淇濆瓨鍒版湰鍦版枃浠讹紝鏍煎紡涓�2017-10-01.log
				String content = wifiInfoBean.getLogtime() + " " + wifiInfoBean.getSignal() + " " 
				                 + wifiInfoBean.getMac() + " " + wifiInfoBean.getWifiname() + "\n";
				bw.write(content);*/
				}
			
				
			}
			//bw.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*public static void pickwords() {
		File file = new File(Constants.PATH_INPUT);
		File file2 = new File(Constants.PATH_OUTPUT);
		try {
			if (!file2.exists()) {
				file2.createNewFile();
			}
			FileWriter fw = new FileWriter(file2, true);
			BufferedWriter bw = new BufferedWriter(fw);
			BufferedReader br = new BufferedReader(new FileReader(file));// 鏋勯�犱竴涓狟ufferedReader绫绘潵璇诲彇鏂囦欢
			String s = null;
			while ((s = br.readLine()) != null) {// 浣跨敤readLine鏂规硶锛屼竴娆¤涓�琛�
				// 鍦ㄨ繖鍒囧垎
				String[] tmpStr = s.split(" ");
				WifiInfoBean wifiInfoBean = new WifiInfoBean();

				// 濡傛灉鏄┖琛岋紝鍒欒烦杩�
				if (tmpStr.length > 23) {
					// 灏佽鍒皐ifiInfoBean
					String signal = "";
					signal = tmpStr[8].replaceAll("-", "");
					signal = signal.replaceAll("dB", "");
					wifiInfoBean.setLogtime("2017-10-14-"+tmpStr[0].substring(0, 8));
					wifiInfoBean.setSignal(Integer.valueOf(signal));
					wifiInfoBean.setMac(tmpStr[12].substring(6));
					wifiInfoBean.setWifiname(tmpStr[23]);
					// 淇濆瓨鍒癶base
					WifiDao wifiDao = new WifiDao();
					wifiDao.insert(wifiInfoBean);

					// 淇濆瓨鍒版湰鍦版枃浠讹紝鏍煎紡涓�2017-10-01.log
					String content = wifiInfoBean.getLogtime() + " " + wifiInfoBean.getSignal() + " "
							+ wifiInfoBean.getMac() + " " + wifiInfoBean.getWifiname() + "\n";
					bw.write(content);
				}
			}
			bw.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	// 瀵煎叆mac鍜屽巶鍟嗗搴旂殑hbase锛岃鍏ユ枃浠秓ui.csv
	public static void pickMac() {
		File file = new File(Constants.PATH_MAC_INPUT);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// 鏋勯�犱竴涓狟ufferedReader绫绘潵璇诲彇鏂囦欢
			String s = null;
			while ((s = br.readLine()) != null) {// 浣跨敤readLine鏂规硶锛屼竴娆¤涓�琛�
				// 鍦ㄨ繖鍒囧垎
				String[] tmpStr = s.split(",");
				macInfoBean macInfoBean = new macInfoBean();
				// 濡傛灉鏄┖琛岋紝鍒欒烦杩�
				if (tmpStr.length > 3) {
					// 灏佽鍒癿acInfoBean
					String tmp = tmpStr[1].toLowerCase();
					macInfoBean.setMac(tmp.substring(0, 2)+":" + tmp.substring(2, 4)+":"+ tmp.substring(4, 6));
					//macInfoBean.setMac(tmpStr[1]);
					String productor = "";
					productor  = tmpStr[2];
					if(productor.contains("\"")) {
						productor = productor.replace("\"", "");
					}
					macInfoBean.setProductor(productor);
					// 淇濆瓨鍒癶base
					//System.out.println(macInfoBean.getMac()+" " + macInfoBean.getProductor());
					macDao.insert(macInfoBean);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
