package com.lili.layer;

import java.util.ArrayList;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.view.MotionEvent;
import android.widget.Toast;

import com.lili.helper.AnimationHelper;
import com.lili.sprite.SpriteData;
import com.lili.tools.MyFileEngine;

/**
 * 关卡图层类
 * @author shiyang
 *
 */
public class LevelLayer extends CCLayer 
{
	private static int LEVEL_NUMBER = 1; // 游戏的总关卡数
	private static int SUCCESS_LEVEL_NUMBER = 1; // 已经解锁的关数
	private CGSize winSize = CCDirector.sharedDirector().winSize(); // 屏幕宽高
	private AnimationHelper helper; // 动画助手

	public LevelLayer()
	{
		init();
		helper = new AnimationHelper();
	}
	
	private void init() {
        // ---创建背景精灵
        CCSprite backgroundSprite = CCSprite.sprite("level_layer/bj3.png");
        backgroundSprite.setPosition(winSize.width / 2, winSize.height / 2); // 设置屏幕居中
        this.addChild(backgroundSprite); // 添加精灵
        
        // ---加上关卡
        String imgPath = null;
        for (int i = 0; i < LEVEL_NUMBER; i++)  // 遍历关卡
        {
        	if (i < SUCCESS_LEVEL_NUMBER)
        	{
        		imgPath = "level_layer/level.png"; // 已经通关的
        		
        		// ---数字
        		String numberString = i+1+"";
        		CCLabel lable = CCLabel.makeLabel(numberString, "华文新魏", 30);
        		float x = 60+i%7*60;
        		float y = 320-60-i/7*80;
        		lable.setPosition(x, y);
        		this.addChild(lable, 2);
        	} else {
        		imgPath = "level_layer/lock.png"; // 加锁的关卡
        	}

        	// ---关卡按钮
        	CCSprite levelSprite = CCSprite.sprite(imgPath); 
        	float x = 60+i%7*60;
        	float y = 320-60-i/7*80;
        	levelSprite.setPosition(x, y);
        	levelSprite.setScale(0.6f);
        	this.addChild(levelSprite, 1, i+1);
        }
        
        // ---打开监听事件
        setIsTouchEnabled(true);
	}
	
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event)
	{
		//转换坐标
		CGPoint convertTouchToNodeSpace = convertTouchToNodeSpace(event);
		
		// 遍历关卡
		for (int i = 0; i < LEVEL_NUMBER; i++)
		{
			// 得到关卡精灵
			CCSprite levelSprite = (CCSprite) this.getChildByTag(i+1);
			
			// 如果手指落在关卡上
			if (CGRect.containsPoint(levelSprite.getBoundingBox(), convertTouchToNodeSpace))
			{
				// 如果点击的是解锁的关卡
				if (levelSprite.getTag() <= SUCCESS_LEVEL_NUMBER )
				{
					System.out.println("选择"+(i+1)+"关");
					this.setIsTouchEnabled(false); // 关闭点击事件
					// 加载数据
					this.runAction(CCCallFunc.action(this, "loadData"));
					
				} else {
					final int lockLevel = i;
					// ---提示用户关卡未解锁
					System.out.println("第 "+(i+1)+" 关未解锁！");
					CCDirector.theApp.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(CCDirector.theApp, "第"+(lockLevel+1)+"关未解锁", Toast.LENGTH_SHORT).show();
						}
					});
				}
				
			}
		}
		
		return super.ccTouchesBegan(event);
	}
	
	// 加载数据
	public void loadData() {
		CCSprite bar = CCSprite.sprite("start_layer/sc_publish_spin.png");
		helper.playProgress(this, bar);
		// 解析JSON数据来得到一个数组
		ArrayList<Object> list = new MyFileEngine().parseNoHeaderJArray("data/level1.json", new SpriteData());
		helper.stopProgress();
		// ---切换到战斗场景
		CCScene fightScene = CCScene.node(); // 创建战斗场景对象
		fightScene.addChild(new BirdLayer(list)); // 添加战斗图层
		CCFadeTransition transition = CCFadeTransition.transition(1, fightScene); // 淡入淡出
		CCDirector.sharedDirector().replaceScene(transition); // 切换到战斗场景
	}
}





















