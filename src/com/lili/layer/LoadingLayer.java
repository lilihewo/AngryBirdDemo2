package com.lili.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGSize;

import android.view.MotionEvent;

/**
 * ����ͼ��
 * @author shiyang
 *
 */
public class LoadingLayer extends CCLayer {
	
	private CGSize winSize = CCDirector.sharedDirector().winSize(); // ��Ļ���
	
	public LoadingLayer()
	{
		// ---�������ر�������
		CCSprite loadingSprite = CCSprite.sprite("loading_layer/loading.png");
		loadingSprite.setPosition(winSize.width / 2, winSize.height / 2);
		this.addChild(loadingSprite);
		
		// ---�򿪼����¼�
		setIsTouchEnabled(true);
	}
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event) 
	{
		// ---�л�����ʼ����
		CCScene startScene = CCScene.node(); // ������ʼ����
		startScene.addChild(new StartLayer()); // ��ӿ�ʼͼ��
		CCFadeTransition transition = CCFadeTransition.transition(1, 
				startScene); // �л�Ч�� ���뵭��
		CCDirector.sharedDirector().replaceScene(transition); // �л�����ʼ����
		
		return super.ccTouchesBegan(event);
	}
	
	
}







