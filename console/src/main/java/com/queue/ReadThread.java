package com.queue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ReadThread implements Runnable {

	@Override
	public void run() {

		try {

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

				Thread.sleep(50);
			}

			bufferedReader.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
