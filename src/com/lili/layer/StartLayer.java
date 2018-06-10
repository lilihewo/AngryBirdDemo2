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
 * 游戏开始图层
 * @author shiyang
 *
 */
public class StartLayer extends CCLayer {
	
	public StartLayer()
	{
		CGSize winSize = CCDirector.sharedDirector().winSize(); // 屏幕宽高
		CGPoint center = CGPoint.ccp(winSize.width / 2, winSize.height / 2);
		// 背景精灵
		CCSprite backgroundSprite = CCSprite.sprite("start_layer/startbg.png");
		backgroundSprite.setPosition(center);
		this.addChild(backgroundSprite); // 添加精灵
		
		// 开始按钮
		CCMenu startGameMenu = new MenuHelper().getMenu("start_layer/start.png", 
				"start_layer/start.png", 
				center, "startGame", this);
		this.addChild(startGameMenu);
		
		BmobUser bmobUser = BmobUser.getCurrentUser();
		if(bmobUser != null){
			CCLabel userName = CCLabel.makeLabel(bmobUser.getUsername()+"，欢迎您！", "hkbd.ttf", 30);
			userName.setPosition(winSize.width / 2, winSize.height-20.0f);
			this.addChild(userName);
		} else {
			// 开启一个定时器，0.1秒后显示对话框
			this.schedule("show", 0.1f);
		}
		
	}
	
	public void show(float f) {
		// 销毁定时器
		this.unschedule("show");
		
		// 提示用户是否要登录
		CCDirector.theApp.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				showDialog();
			}
		});
	}
	
	
	// 开始游戏菜单按钮的回调函数
	public void startGame(Object obj) {
		// ---转换到场景
		CCScene scene = CCScene.node(); // 创建选关场景
		scene.addChild(new LevelLayer()); // 添加选关图层
		CCFadeTransition transition = CCFadeTransition.transition(1, scene);
		CCDirector.sharedDirector().replaceScene(transition);
	}
	
	// 显示是否要登录对话框
	private void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(CCDirector.theApp);
		builder.setIcon(R.drawable.icon);
		builder.setTitle("客官要登录吗？");
		builder.setPositiveButton("要", new DialogInterface.OnClickListener()
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


		builder.setNegativeButton("不要", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{

			}
		});
		// 显示出该对话框
		builder.show();
	}

	
}


