package com.queue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import com.jdbc.JDBCUtils;

public class ReadWriteThead {
	public static void main(String[] args) throws Exception {

		/*ReadThread readThread=new ReadThread();
		Thread thread=new Thread(readThread);
		thread.start();
		
		WriteThread writeThread=new WriteThread();
		Thread thread2=new Thread(writeThread);
		thread2.start();
		*/
		
		
		
		FileInputStream fileInputStream = new FileInputStream("worldcity.txt");
		InputStreamReader reader = new InputStreamReader(fileInputStream);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line = null;
		WorldCity city = null;
		while ((line = bufferedReader.readLine()) != null) {
			String[] cityArr = line.split(";");

			if (cityArr.length < 3) {
				break;
			}

			String cityId = cityArr[2].substring(1, cityArr[2].length() - 1);
			city = new WorldCity();
			city.setId(Integer.valueOf(cityId));
			city.setCity(cityArr[1].substring(1, cityArr[1].length() - 1));
			city.setCountry(cityArr[0].substring(1, cityArr[0].length() - 1));

			DataQueue.cityQueue.offer(city);
		}

		bufferedReader.close();

		List<String[]> cityValueList = new ArrayList<>();
		String[] valueArr = null;
		while (!DataQueue.cityQueue.isEmpty()) {
			valueArr = new String[3];
			WorldCity existedCity = DataQueue.cityQueue.poll();
			valueArr[0] = existedCity.getId().toString();
			valueArr[1] = existedCity.getCity();
			valueArr[2] = existedCity.getCountry();
			cityValueList.add(valueArr);
		}

		JDBCUtils jdbcUtils = JDBCUtils.getInstance();
		String insertSql="insert into world_city(id,city,country) values(?,?,?)";
		int result= jdbcUtils.executeBatchInsert(insertSql,cityValueList);

		System.out.println(result);
		System.out.println(DataQueue.cityQueue.size());
	}
}

class DataQueue {
	public static Deque<WorldCity> cityQueue = new LinkedList<>();
}

class WorldCity {
	private Integer id;
	private String city;
	private String country;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
