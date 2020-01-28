package com.ysu.util;

import java.io.IOException;

import com.ysu.db.HBaseDB;

public class try11 {

	public static void main(String[] args) {
		long id=2;
		try {
			HBaseDB.add(Constants.HBASE_TABLE_WIFIUSER, id, Constants.HBASE_FAMILY_WIFIUSER_WIFIUSER, Constants.HBASE_COLUMN_WIFIUSER_NAME, "zhangshizhu");
			HBaseDB.add(Constants.HBASE_TABLE_WIFIUSER, id, Constants.HBASE_FAMILY_WIFIUSER_WIFIUSER, Constants.HBASE_COLUMN_WIFIUSER_PWD, "123456");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
