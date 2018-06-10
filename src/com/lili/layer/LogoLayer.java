package com.lili.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGSize;

import android.view.MotionEvent;


/**
 * logoͼ����
 * @author shiyang
 *
 */
public class LogoLayer extends CCLayer {
	
	private CGSize winSize = CCDirector.sharedDirector().winSize(); // ��Ļ���
	
	public LogoLayer()
	{
		// ---���������������
		CCSprite bgSprite = CCSprite.sprite("logo_layer/bg.png");
		bgSprite.setPosition(winSize.width / 2, winSize.height / 2); //���ñ��������������Ļ����
		this.addChild(bgSprite); // ��ӱ�������
		
		
//		BmobUser bmobUser = BmobUser.getCurrentUser();
//		if(bmobUser != null){
//			CCLabel userName = CCLabel.labelWithString(bmobUser.getUsername()+"����ӭ����", "hkbd.ttf", 30);
//			userName.setPosition(winSize.width / 2, winSize.height-20.0f);
//			this.addChild(userName);
//		} else {
//			// ��ʾ�û��Ƿ�Ҫ��¼
//			CCDirector.theApp.runOnUiThread(new Runnable() {
//				@Override
//				public void run() {
//					showDialog();
//				}
//			});
//		}
		// ---�򿪼����¼�
		setIsTouchEnabled(true);
	}
	
	
//	// ��ʾ�Ƿ�Ҫ��¼�Ի���
//	private void showDialog() {
//		AlertDialog.Builder builder = new AlertDialog.Builder(CCDirector.theApp);
//		builder.setIcon(R.drawable.icon);
//		builder.setTitle("�͹�Ҫ��¼��");
//		builder.setPositiveButton("Ҫ", new DialogInterface.OnClickListener()
//		{
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//				Intent intent = new Intent();
//				intent.setClass(CCDirector.theApp, LoginActivity.class);
//				intent.putExtra("username", "");
//				CCDirector.theApp.startActivity(intent);
//				
//				CCDirector.theApp.finish();
//			}
//		});
//
//
//		builder.setNegativeButton("��Ҫ", new DialogInterface.OnClickListener()
//		{
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//
//			}
//		});
//		// ��ʾ���öԻ���
//		builder.show();
//	}


	@Override
	public boolean ccTouchesBegan(MotionEvent event) 
	{
		// ---�л�����
		CCScene loadingScene = CCScene.node(); // �������س�������
		loadingScene.addChild(new LoadingLayer()); // ��Ӽ���ͼ��
		CCFadeTransition transition = CCFadeTransition.transition(1,
				loadingScene); // �л�Ч�� ���뵭��
		CCDirector.sharedDirector().replaceScene(transition); // ת������

		return super.ccTouchesBegan(event);
	}
	
	
}
