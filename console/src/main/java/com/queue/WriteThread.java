package com.queue;

import java.util.ArrayList;
import java.util.List;

import com.jdbc.JDBCUtils;

public class WriteThread implements Runnable {

	@Override
	public void run() {
		try {
			List<String[]> cityValueList = new ArrayList<>();
			String[] valueArr = null;
			while (!DataQueue.cityQueue.isEmpty()) {
				valueArr = new String[3];
				WorldCity existedCity = DataQueue.cityQueue.poll();
				valueArr[0] = existedCity.getId().toString();
				valueArr[1] = existedCity.getCity();
				valueArr[2] = existedCity.getCountry();
				cityValueList.add(valueArr);

				Thread.sleep(100);
			}

			JDBCUtils jdbcUtils = JDBCUtils.getInstance();
			String insertSql = "insert into world_city(id,city,country) values(?,?,?)";
			int result = jdbcUtils.executeBatchInsert(insertSql, cityValueList);
			
			System.out.println(result);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
