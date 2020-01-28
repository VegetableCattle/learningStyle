package com.ysu.util;

/**
 * 瀹氫箟椤圭洰涓殑涓�浜涘父閲�
 * @author Administrator
 *
 */
public class Constants {

	public static final String SESSION_USER_BEAN = "userBean";
	
	//瀹氫箟hbase涓敤鍒扮殑琛�
	public static final String HBASE_TABLE_WIFIGID = "gid";
	public static final String HBASE_TABLE_WIFIUSER = "wifiuser";
	public static final String HBASE_TABLE_WIFIINFO = "wifiinfo";
	public static final String HBASE_TABLE_MACINFO = "macinfo";
	public static final String HBASE_TABLE_XISDATES = "xisdates";
	public static final String HBASE_TABLE_XISMAC = "xismac";
	public static final String HBASE_TABLE_ZHUDIANSHICHANG = "zhudianshichang";
	public static final String HBASE_TABLE_LAOFUKE = "laoguke";
	public static final String HBASE_TABLE_SHOUJIPINPAI = "shoujipinpai";
	
	public static final String HBASE_TABLE_LEARNING = "learning";
	//瀹氫箟鍒楁棌
	public static final String HBASE_FAMILY_WIFIGID_WIFIGID = "gid";
	public static final String HBASE_FAMILY_WIFIUSER_WIFIUSER = "wifiuser";
	public static final String HBASE_FAMILY_WIFIINFO_WIFIINFO = "wifiinfo";
	public static final String HBASE_FAMILY_MACINFO_MACINFO = "macinfo";
	public static final String HBASE_FAMILY_XISDATES_XISDATES = "xisdates";
	public static final String HBASE_FAMILY_XISMAC_XISMAC = "xismac";
	public static final String HBASE_FAMILY_ZHUDIANSHICHANG_ZHUDIANSHICHANG = "zhudianshichang";
	public static final String HBASE_FAMILY_LAOFUKE_LAOFUKE = "laoguke";
	public static final String HBASE_FAMILY_SHOUJIPINPAI_SHOUJIPINPAI = "shoujipinpai";
	
	public static final String HBASE_FAMILY_LEARNING_LEARNING = "learning";
	//瀹氫箟琛屽仴
	public static final String HBASE_ROW_KEY_WIFIGID_GID = "gid";
	public static final String HBASE_ROW_KEY_WIFIGID_INFOID = "infoid";
	public static final String HBASE_ROW_KEY_MACINFO_MAC = "mac";
	
	//瀹氫箟鍒�
	public static final String HBASE_COLUMN_WIFIGID_GID = "gid";
	public static final String HBASE_COLUMN_WIFIGID_INFOID = "infoid";

	public static final String HBASE_COLUMN_WIFIUSER_PWD = "pwd";
	public static final String HBASE_COLUMN_WIFIUSER_NAME = "name";

	public static final String HBASE_COLUMN_WIFIINFO_LOGTIME = "logtime";
	public static final String HBASE_COLUMN_WIFIINFO_SIGNAL = "signal";
	public static final String HBASE_COLUMN_WIFIINFO_MAC = "mac";
	public static final String HBASE_COLUMN_WIFIINFO_WIFINAME = "wifiname";
	
	public static final String HBASE_COLUMN_MACINFO_PRODUCTOR = "productor";
	
	
	public static final String HBASE_COLUMN_XISDATES_KELUILIANG= "keliuliang";
	public static final String HBASE_COLUMN_XISDATES_RUDIANLIANG = "rudianliang";
	public static final String HBASE_COLUMN_XISDATES_RUDIANLV = "rudianlv";
	public static final String HBASE_COLUMN_XISDATES_TIAOCHULV = "tiaochulv";
	public static final String HBASE_COLUMN_XISDATES_SHENFANGLV = "shenfanglv";
	
	public static final String HBASE_COLUMN_XISMAC_LAIFANGZHOUQI = "laifangzhouqi";
	public static final String HBASE_COLUMN_XISMAC_HUOYUEDU = "huoyuedu";
	
	public static final String HBASE_COLUMN_ZHUDIANSHICHANG_ZHUDIANSHICHANG = "zhudianshichang";
	public static final String HBASE_COLUMN_LAOFUKE_LAOFUKE = "laoguke";
	public static final String HBASE_COLUMN_LAOFUKE_XINGUKE = "xinguke";
	
	public static final String HBASE_COLUMN_SHOUJIPINPAI_SHOUJIPINPAI = "shoujipinpai";
	
	
	public static final String HBASE_COLUMN_LEARNING_DLYL = "dlyl";
	public static final String HBASE_COLUMN_LEARNING_GWZJ = "gwzj";
	public static final String HBASE_COLUMN_LEARNING_HYCS = "hycs";
	//hdfs璺緞
	public static final String PATH_PRE = "hdfs://li:9000";
	
	//HBASE
	public static final String HBASE_PATH_PRE = "hdfs://li:9000/hbase";
	public static final String HBASE_NAME = "li";
	
	//鎶撳彇鏁版嵁浠ュ強瀛樺偍鏁版嵁鐨刾ath
	public static final String PATH_INPUT = "C:\\Users\\LiYong\\Desktop\\learningStyle\\data";
	public static final String PATH_OUTPUT = "C:\\Users\\LiYong\\Desktop\\learningStyle\\output.txt";
	
	//  鎶撳彇mac鍦板潃鍜屽巶鍟嗗搴旂殑鏁版嵁
	public static final String PATH_MAC_INPUT = "C:\\Users\\qjx\\Desktop\\wifi\\oui.csv";
	
	//hive
	public static final String HIVE_URL= "jdbc:hive2://192.168.32.100:10000/default";
	public static final String HIVE_USER = "qjx";
	public static final String HIVE_PASSWORD = "123456";
	
}
