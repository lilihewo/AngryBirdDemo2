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
 * �ؿ�ͼ����
 * @author shiyang
 *
 */
public class LevelLayer extends CCLayer 
{
	private static int LEVEL_NUMBER = 1; // ��Ϸ���ܹؿ���
	private static int SUCCESS_LEVEL_NUMBER = 1; // �Ѿ������Ĺ���
	private CGSize winSize = CCDirector.sharedDirector().winSize(); // ��Ļ���
	private AnimationHelper helper; // ��������

	public LevelLayer()
	{
		init();
		helper = new AnimationHelper();
	}
	
	private void init() {
        // ---������������
        CCSprite backgroundSprite = CCSprite.sprite("level_layer/bj3.png");
        backgroundSprite.setPosition(winSize.width / 2, winSize.height / 2); // ������Ļ����
        this.addChild(backgroundSprite); // ��Ӿ���
        
        // ---���Ϲؿ�
        String imgPath = null;
        for (int i = 0; i < LEVEL_NUMBER; i++)  // �����ؿ�
        {
        	if (i < SUCCESS_LEVEL_NUMBER)
        	{
        		imgPath = "level_layer/level.png"; // �Ѿ�ͨ�ص�
        		
        		// ---����
        		String numberString = i+1+"";
        		CCLabel lable = CCLabel.makeLabel(numberString, "������κ", 30);
        		float x = 60+i%7*60;
        		float y = 320-60-i/7*80;
        		lable.setPosition(x, y);
        		this.addChild(lable, 2);
        	} else {
        		imgPath = "level_layer/lock.png"; // �����Ĺؿ�
        	}

        	// ---�ؿ���ť
        	CCSprite levelSprite = CCSprite.sprite(imgPath); 
        	float x = 60+i%7*60;
        	float y = 320-60-i/7*80;
        	levelSprite.setPosition(x, y);
        	levelSprite.setScale(0.6f);
        	this.addChild(levelSprite, 1, i+1);
        }
        
        // ---�򿪼����¼�
        setIsTouchEnabled(true);
	}
	
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event)
	{
		//ת������
		CGPoint convertTouchToNodeSpace = convertTouchToNodeSpace(event);
		
		// �����ؿ�
		for (int i = 0; i < LEVEL_NUMBER; i++)
		{
			// �õ��ؿ�����
			CCSprite levelSprite = (CCSprite) this.getChildByTag(i+1);
			
			// �����ָ���ڹؿ���
			if (CGRect.containsPoint(levelSprite.getBoundingBox(), convertTouchToNodeSpace))
			{
				// ���������ǽ����Ĺؿ�
				if (levelSprite.getTag() <= SUCCESS_LEVEL_NUMBER )
				{
					System.out.println("ѡ��"+(i+1)+"��");
					this.setIsTouchEnabled(false); // �رյ���¼�
					// ��������
					this.runAction(CCCallFunc.action(this, "loadData"));
					
				} else {
					final int lockLevel = i;
					// ---��ʾ�û��ؿ�δ����
					System.out.println("�� "+(i+1)+" ��δ������");
					CCDirector.theApp.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(CCDirector.theApp, "��"+(lockLevel+1)+"��δ����", Toast.LENGTH_SHORT).show();
						}
					});
				}
				
			}
		}
		
		return super.ccTouchesBegan(event);
	}
	
	// ��������
	public void loadData() {
		CCSprite bar = CCSprite.sprite("start_layer/sc_publish_spin.png");
		helper.playProgress(this, bar);
		// ����JSON�������õ�һ������
		ArrayList<Object> list = new MyFileEngine().parseNoHeaderJArray("data/level1.json", new SpriteData());
		helper.stopProgress();
		// ---�л���ս������
		CCScene fightScene = CCScene.node(); // ����ս����������
		fightScene.addChild(new BirdLayer(list)); // ���ս��ͼ��
		CCFadeTransition transition = CCFadeTransition.transition(1, fightScene); // ���뵭��
		CCDirector.sharedDirector().replaceScene(transition); // �л���ս������
	}
}





















