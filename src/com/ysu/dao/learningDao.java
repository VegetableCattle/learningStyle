package com.ysu.dao;

import java.io.IOException;

import com.ysu.db.HBaseDB;
import com.ysu.model.LearningBean;
import com.ysu.model.WifiInfoBean;
import com.ysu.util.Constants;

public class learningDao {
	public static boolean insertDlyl(LearningBean learningBean) {
		try {
			if(learningBean.getDlyl()!=null)
			HBaseDB.add(Constants.HBASE_TABLE_LEARNING, learningBean.getLearningId(), Constants.HBASE_FAMILY_LEARNING_LEARNING,
					Constants.HBASE_COLUMN_LEARNING_DLYL, learningBean.getDlyl());
			System.out.println(1);
			if(learningBean.getGwzj()!=null)
				HBaseDB.add(Constants.HBASE_TABLE_LEARNING, learningBean.getLearningId(), Constants.HBASE_FAMILY_LEARNING_LEARNING,
						Constants.HBASE_COLUMN_LEARNING_GWZJ, learningBean.getGwzj());
			System.out.println(2);
			if(learningBean.getHycs()!=null)
				HBaseDB.add(Constants.HBASE_TABLE_LEARNING, learningBean.getLearningId(), Constants.HBASE_FAMILY_LEARNING_LEARNING,
						Constants.HBASE_COLUMN_LEARNING_HYCS, learningBean.getHycs());
			System.out.println(3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
