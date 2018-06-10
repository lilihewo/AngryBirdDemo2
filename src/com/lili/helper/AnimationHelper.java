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
	// 播放进度条动画
	public void playProgress(CCLayer layer, CCSprite bar) {
		layer.setIsTouchEnabled(false); // 关闭点击事件
		
		this.layer = layer;
		this.bar = bar;
		
		// 进度条
		bar.setPosition(winSize.width/2, winSize.height/2);
		bar.setScale(0);
		layer.addChild(bar);

		// 放大动作
		CCScaleTo scaleTo = CCScaleTo.action(0.6f, 1.0f);
		// 旋转动作
		CCRotateBy rotateBy = CCRotateBy.action(0.6f, 360);
		CCSpawn spawn = CCSpawn.actions(scaleTo, rotateBy);
		CCCallFunc callFunc = CCCallFunc.action(this, "foreverRotateAction");
		CCSequence sequence = CCSequence.actions(spawn, callFunc);
		bar.runAction(sequence);
	}
	// 永远旋转动作
	public void foreverRotateAction() {
		CCRotateBy rotateBy = CCRotateBy.action(0.6f, 360);
		bar.runAction(CCRepeatForever.action(rotateBy));
	}
	
	
	
	// 停止进度条动画
	public void stopProgress() {
		CCFadeOut fadeOut = CCFadeOut.action(0.8f);
		CCCallFunc callFunc = CCCallFunc.action(this, "destroy");
		CCSequence sequence = CCSequence.actions(fadeOut, callFunc);
		bar.runAction(sequence);
	}
	// 销毁进度条的回调函数
	public void destroy() {
		layer.setIsTouchEnabled(true); // 打开点击事件
		if (bar != null) {
			bar.removeSelf(); // 移除精灵
		}
	}
}
