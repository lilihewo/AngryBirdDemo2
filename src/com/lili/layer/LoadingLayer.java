package com.lili.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGSize;

import android.view.MotionEvent;

/**
 * 加载图层
 * @author shiyang
 *
 */
public class LoadingLayer extends CCLayer {
	
	private CGSize winSize = CCDirector.sharedDirector().winSize(); // 屏幕宽高
	
	public LoadingLayer()
	{
		// ---创建加载背景精灵
		CCSprite loadingSprite = CCSprite.sprite("loading_layer/loading.png");
		loadingSprite.setPosition(winSize.width / 2, winSize.height / 2);
		this.addChild(loadingSprite);
		
		// ---打开监听事件
		setIsTouchEnabled(true);
	}
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event) 
	{
		// ---切换到开始场景
		CCScene startScene = CCScene.node(); // 创建开始场景
		startScene.addChild(new StartLayer()); // 添加开始图层
		CCFadeTransition transition = CCFadeTransition.transition(1, 
				startScene); // 切换效果 淡入淡出
		CCDirector.sharedDirector().replaceScene(transition); // 切换到开始场景
		
		return super.ccTouchesBegan(event);
	}
	
	
}







