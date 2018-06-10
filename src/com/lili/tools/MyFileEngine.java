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
 * �ҵ��ļ�����
 */
public class MyFileEngine 
{
	/**
	 * ��Assets�ļ����еõ�String����
	 * @param path �ļ�·��
	 * @return String����
	 */
	public String getDataFromAssets(String path)
	{
		// ����һ��String���͵Ļ�����
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		// �õ�BuffereReader����
		BufferedReader bufferedReader = getBufferedReaderFromAssets(path);
		
		// append��������ӵ�����
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

		// ��stringBufferת��Ϊһ��String����
		return stringBuffer.toString();
	}
	
	
	/**
	 * ��Assets�ļ��еõ�BuffereReader����
	 * @param path �ļ�·��
	 * @return BuffereReader����
	 */
	public BufferedReader getBufferedReaderFromAssets(String path)
	{
		BufferedReader bufferedReader = null; // ������
		CCDirector director = CCDirector.sharedDirector(); // �õ����ݶ���
	
		// ������
		try {
			bufferedReader = new BufferedReader
					(new InputStreamReader(director.getActivity().getAssets().open(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bufferedReader; // ����BuffereReader����
	}

	
	
	/**
	 * ����û������ͷ�Ĵ�����
	 */
	public ArrayList<Object> parseNoHeaderJArray(String filePath, Object object) {
	    // �õ�����JSON ��ת��String
		String strByJson = getDataFromAssets(filePath);
	    //Json�Ľ��������
	    JsonParser parser = new JsonParser();
	    //��JSON��String ת��һ��JsonArray����
	    JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();

	    Gson gson = new Gson();
	    ArrayList<Object> objectList = new ArrayList<Object>();

	    // ��ǿforѭ������JsonArray
	    for (JsonElement jsonElement : jsonArray) {
	        // ʹ��GSON��ֱ��ת��Bean����
	    	objectList.add(gson.fromJson(jsonElement, object.getClass()));
	    }

		return objectList;
	}
	

}


