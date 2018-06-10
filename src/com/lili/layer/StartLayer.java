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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import cn.bmob.v3.BmobUser;

import com.lili.angrybirddemo2.LoginActivity;
import com.lili.angrybirddemo2.R;
import com.lili.helper.MenuHelper;

/**
 * ��Ϸ��ʼͼ��
 * @author shiyang
 *
 */
public class StartLayer extends CCLayer {
	
	public StartLayer()
	{
		CGSize winSize = CCDirector.sharedDirector().winSize(); // ��Ļ���
		CGPoint center = CGPoint.ccp(winSize.width / 2, winSize.height / 2);
		// ��������
		CCSprite backgroundSprite = CCSprite.sprite("start_layer/startbg.png");
		backgroundSprite.setPosition(center);
		this.addChild(backgroundSprite); // ��Ӿ���
		
		// ��ʼ��ť
		CCMenu startGameMenu = new MenuHelper().getMenu("start_layer/start.png", 
				"start_layer/start.png", 
				center, "startGame", this);
		this.addChild(startGameMenu);
		
		BmobUser bmobUser = BmobUser.getCurrentUser();
		if(bmobUser != null){
			CCLabel userName = CCLabel.makeLabel(bmobUser.getUsername()+"����ӭ����", "hkbd.ttf", 30);
			userName.setPosition(winSize.width / 2, winSize.height-20.0f);
			this.addChild(userName);
		} else {
			// ����һ����ʱ����0.1�����ʾ�Ի���
			this.schedule("show", 0.1f);
		}
		
	}
	
	public void show(float f) {
		// ���ٶ�ʱ��
		this.unschedule("show");
		
		// ��ʾ�û��Ƿ�Ҫ��¼
		CCDirector.theApp.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				showDialog();
			}
		});
	}
	
	
	// ��ʼ��Ϸ�˵���ť�Ļص�����
	public void startGame(Object obj) {
		// ---ת��������
		CCScene scene = CCScene.node(); // ����ѡ�س���
		scene.addChild(new LevelLayer()); // ���ѡ��ͼ��
		CCFadeTransition transition = CCFadeTransition.transition(1, scene);
		CCDirector.sharedDirector().replaceScene(transition);
	}
	
	// ��ʾ�Ƿ�Ҫ��¼�Ի���
	private void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(CCDirector.theApp);
		builder.setIcon(R.drawable.icon);
		builder.setTitle("�͹�Ҫ��¼��");
		builder.setPositiveButton("Ҫ", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				Intent intent = new Intent();
				intent.setClass(CCDirector.theApp, LoginActivity.class);
				intent.putExtra("username", "");
				CCDirector.theApp.startActivity(intent);
				
				CCDirector.theApp.finish();
			}
		});


		builder.setNegativeButton("��Ҫ", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{

			}
		});
		// ��ʾ���öԻ���
		builder.show();
	}

	
}


