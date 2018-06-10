package com.lili.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.cocos2d.nodes.CCDirector;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 我的文件引擎
 */
public class MyFileEngine 
{
	/**
	 * 从Assets文件夹中得到String对象
	 * @param path 文件路径
	 * @return String对象
	 */
	public String getDataFromAssets(String path)
	{
		// 定义一个String类型的缓冲区
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		// 得到BuffereReader对象
		BufferedReader bufferedReader = getBufferedReaderFromAssets(path);
		
		// append方法是添加到后面
		try {
			while ((line = bufferedReader.readLine()) != null)
			{
				stringBuffer.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null)
			{
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// 将stringBuffer转换为一个String对象
		return stringBuffer.toString();
	}
	
	
	/**
	 * 从Assets文件中得到BuffereReader对象
	 * @param path 文件路径
	 * @return BuffereReader对象
	 */
	public BufferedReader getBufferedReaderFromAssets(String path)
	{
		BufferedReader bufferedReader = null; // 缓冲流
		CCDirector director = CCDirector.sharedDirector(); // 得到导演对象
	
		// 缓冲流
		try {
			bufferedReader = new BufferedReader
					(new InputStreamReader(director.getActivity().getAssets().open(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bufferedReader; // 返回BuffereReader对象
	}

	
	
	/**
	 * 解析没有数据头的纯数组
	 */
	public ArrayList<Object> parseNoHeaderJArray(String filePath, Object object) {
	    // 拿到本地JSON 并转成String
		String strByJson = getDataFromAssets(filePath);
	    //Json的解析类对象
	    JsonParser parser = new JsonParser();
	    //将JSON的String 转成一个JsonArray对象
	    JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();

	    Gson gson = new Gson();
	    ArrayList<Object> objectList = new ArrayList<Object>();

	    // 加强for循环遍历JsonArray
	    for (JsonElement jsonElement : jsonArray) {
	        // 使用GSON，直接转成Bean对象
	    	objectList.add(gson.fromJson(jsonElement, object.getClass()));
	    }

		return objectList;
	}
	

}


