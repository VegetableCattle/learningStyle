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
public class localToHbase {

	public static void main(String[] args) {
		//pickMac();
		String inputFileName="dl.txt";
		String inputFileName2="gw.txt";
		pickwords(inputFileName);
		pickwords2(inputFileName2);
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
				learningDao learning=new learningDao();
				learningDao.insertDlyl(learningBean);
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
	public static void pickwords2(String inputFileName){
		String inputPath=Constants.PATH_INPUT+"\\"+inputFileName;
		File file = new File(inputPath);
		System.out.println(inputPath);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// 鏋勯�犱竴涓狟ufferedReader绫绘潵璇诲彇鏂囦欢
			String s = null;
			ArrayList<LearningBean>lList = new ArrayList<LearningBean>();
			for(int i=10001;i<=10250;i++){
				LearningBean learningBean=new LearningBean();
				String id=i+"";
				learningBean.setLearningId(id);
				learningBean.setGwzj("zj");
				learningDao learning=new learningDao();
				learningDao.insertDlyl(learningBean);
			}
			while ((s = br.readLine()) != null) {// 浣跨敤readLine鏂规硶锛屼竴娆¤涓�琛�
				String[] tmpStr = s.split("	");
				LearningBean learningBean=new LearningBean();
				
				if(Integer.valueOf(tmpStr[1])>3){
					//System.out.println(1);
			    learningBean.setLearningId(tmpStr[0]);
			    learningBean.setGwzj("gw");
			    learningDao learning=new learningDao();
				learningDao.insertDlyl(learningBean);	
				}
			
				
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
