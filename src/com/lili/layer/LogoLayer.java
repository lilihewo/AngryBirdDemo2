package com.lili.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGSize;

import android.view.MotionEvent;


/**
 * logo图层类
 * @author shiyang
 *
 */
public class LogoLayer extends CCLayer {
	
	private CGSize winSize = CCDirector.sharedDirector().winSize(); // 屏幕宽高
	
	public LogoLayer()
	{
		// ---创建背景精灵对象
		CCSprite bgSprite = CCSprite.sprite("logo_layer/bg.png");
		bgSprite.setPosition(winSize.width / 2, winSize.height / 2); //设置背景精灵对象在屏幕居中
		this.addChild(bgSprite); // 添加背景精灵
		
		
//		BmobUser bmobUser = BmobUser.getCurrentUser();
//		if(bmobUser != null){
//			CCLabel userName = CCLabel.labelWithString(bmobUser.getUsername()+"，欢迎您！", "hkbd.ttf", 30);
//			userName.setPosition(winSize.width / 2, winSize.height-20.0f);
//			this.addChild(userName);
//		} else {
//			// 提示用户是否要登录
//			CCDirector.theApp.runOnUiThread(new Runnable() {
//				@Override
//				public void run() {
//					showDialog();
//				}
//			});
//		}
		// ---打开监听事件
		setIsTouchEnabled(true);
	}
	
	
//	// 显示是否要登录对话框
//	private void showDialog() {
//		AlertDialog.Builder builder = new AlertDialog.Builder(CCDirector.theApp);
//		builder.setIcon(R.drawable.icon);
//		builder.setTitle("客官要登录吗？");
//		builder.setPositiveButton("要", new DialogInterface.OnClickListener()
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
//		builder.setNegativeButton("不要", new DialogInterface.OnClickListener()
//		{
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//
//			}
//		});
//		// 显示出该对话框
//		builder.show();
//	}


	@Override
	public boolean ccTouchesBegan(MotionEvent event) 
	{
		// ---切换场景
		CCScene loadingScene = CCScene.node(); // 创建加载场景对象
		loadingScene.addChild(new LoadingLayer()); // 添加加载图层
		CCFadeTransition transition = CCFadeTransition.transition(1,
				loadingScene); // 切换效果 淡入淡出
		CCDirector.sharedDirector().replaceScene(transition); // 转换场景

		return super.ccTouchesBegan(event);
	}
	
	
}
