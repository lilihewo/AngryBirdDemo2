package com.lili.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import com.lili.helper.MenuHelper;

public class LuckyLayer extends CCLayer {
	
	private CGSize winSize = CCDirector.sharedDirector().winSize(); // ��Ļ���

	
	public LuckyLayer() {
		init();
	}

	private void init() {
		CCSprite bg = CCSprite.sprite("lucky_layer/dialog.png");
		bg.setPosition(winSize.width / 2, winSize.height/2);
		this.addChild(bg);
		
		
		CGPoint point = CGPoint.ccp(winSize.width / 2, 100);
		CCMenu ok = new MenuHelper().getMenu("lucky_layer/button.png",
				"lucky_layer/button.png", point, "goLevelLayer", this);
		this.addChild(ok);
		CCLabel nextLevel = CCLabel.makeLabel("����", "hkbd.ttf", 40);
		nextLevel.setPosition(point);
		this.addChild(nextLevel);
		
		CCLabel good = CCLabel.makeLabel("Very good!", "hkbd.ttf", 60);
		good.setPosition(winSize.width / 2, winSize.height/2);
		this.addChild(good);
		
		this.setIsTouchEnabled(true);
	}
	
	
	public void goLevelLayer(Object obj) {
		// ---ת��������
		CCScene scene = CCScene.node(); // ����ѡ�س���
		scene.addChild(new LevelLayer()); // ���ѡ��ͼ��
		CCFadeTransition transition = CCFadeTransition.transition(1, scene);
		CCDirector.sharedDirector().replaceScene(transition);

	}
	
}
