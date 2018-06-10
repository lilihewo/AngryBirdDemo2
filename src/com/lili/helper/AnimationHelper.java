package com.lili.helper;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

public class AnimationHelper {
	private CGSize winSize = CCDirector.sharedDirector().winSize();
	
	private CCLayer layer;
	private CCSprite bar = null;
	// ���Ž���������
	public void playProgress(CCLayer layer, CCSprite bar) {
		layer.setIsTouchEnabled(false); // �رյ���¼�
		
		this.layer = layer;
		this.bar = bar;
		
		// ������
		bar.setPosition(winSize.width/2, winSize.height/2);
		bar.setScale(0);
		layer.addChild(bar);

		// �Ŵ���
		CCScaleTo scaleTo = CCScaleTo.action(0.6f, 1.0f);
		// ��ת����
		CCRotateBy rotateBy = CCRotateBy.action(0.6f, 360);
		CCSpawn spawn = CCSpawn.actions(scaleTo, rotateBy);
		CCCallFunc callFunc = CCCallFunc.action(this, "foreverRotateAction");
		CCSequence sequence = CCSequence.actions(spawn, callFunc);
		bar.runAction(sequence);
	}
	// ��Զ��ת����
	public void foreverRotateAction() {
		CCRotateBy rotateBy = CCRotateBy.action(0.6f, 360);
		bar.runAction(CCRepeatForever.action(rotateBy));
	}
	
	
	
	// ֹͣ����������
	public void stopProgress() {
		CCFadeOut fadeOut = CCFadeOut.action(0.8f);
		CCCallFunc callFunc = CCCallFunc.action(this, "destroy");
		CCSequence sequence = CCSequence.actions(fadeOut, callFunc);
		bar.runAction(sequence);
	}
	// ���ٽ������Ļص�����
	public void destroy() {
		layer.setIsTouchEnabled(true); // �򿪵���¼�
		if (bar != null) {
			bar.removeSelf(); // �Ƴ�����
		}
	}
}
