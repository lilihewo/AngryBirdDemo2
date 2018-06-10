package com.lili.angrybirddemo2;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.sound.SoundEngine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import cn.bmob.v3.Bmob;

import com.lili.layer.StartLayer;

/**
 * 一、由于审核项目的工作人员不知道点击一下界面才会跳转，所以图层的跳转顺序改变一下
 * 由原来：
 * LogoLayer -> LoadingLayer -> StartLayer -> LevelLayer
 * 改为
 * StartLayer -> LevelLayer
 * 
 * 二、由于水平有限，没能弄明白如何保证100%成功创建刚体。
 * 如果没能成功创建刚体，程序因空指针而崩溃。
 */

/**
 * 愤怒的小鸟Demo
 * @author shiyang
 *
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ================请各位开发者朋友们注意======================
        // 这里的appkey是我的appkey，当你使用本源码时，换成你的appkey
        
        String appkey = "ebf0abaa831caef814bf09f371c654d4";
        Bmob.initialize(this, appkey);
        // ================请各位开发者朋友们注意======================
        
        // --> 创建CCGLSurfaceView对象
        CCGLSurfaceView view = new CCGLSurfaceView(this);
        setContentView(view); // 设置Activity显示的View
        // --> 获取导演对象
        CCDirector director = CCDirector.sharedDirector();
        director.setDeviceOrientation(CCDirector.
        		kCCDeviceOrientationLandscapeLeft); // 设置强制横屏显示
        director.setDisplayFPS(true); // 显示帧率
        director.attachInView(view); // 开始绘制线程
        
        director.setScreenSize(480.0f, 320.0f);
        
        CCScene scene = CCScene.node(); // 创建一个场景对象
        
//        LogoLayer logoLayer = new LogoLayer(); // 创建logo图层对象
        StartLayer startLayer = new StartLayer(); // 创建startLayer图层对象
        scene.addChild(startLayer); // 将logo图层添加到场景
        
        // 导演运行场景
        director.runWithScene(scene);  
    }

    /**
     * 暂停
     */
    @Override
    protected void onPause() 
    {
    	super.onPause();
    	
    	CCDirector.sharedDirector().onPause(); //游戏暂停
//    	SoundEngine.sharedEngine().pauseSound(); //暂停音乐
    }
    
    /**
     * 继续
     */
    @Override
    protected void onResume() 
    {
    	super.onResume();
    	
    	CCDirector.sharedDirector().onResume(); //游戏继续
//    	SoundEngine.sharedEngine().resumeSound(); //音乐继续
    }
    
    /**
     * 结束
     */
    @Override
    protected void onDestroy() 
    {
    	super.onDestroy();
    	
    	CCDirector.sharedDirector().end(); //游戏结束
 
//    	SoundEngine.sharedEngine().realesAllSounds(); // 音乐结束
    }
    
    
    
	// 用户按下返回键
	@Override
   	public boolean onKeyDown(int keyCode, KeyEvent event)
   	{
   		if (keyCode == KeyEvent.KEYCODE_BACK )
   		{
   			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
   			builder.setIcon(R.drawable.icon);
   			builder.setTitle("退出本游戏？");
   			builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
   			{
   				@Override
   				public void onClick(DialogInterface dialog, int which)
   				{
   					// 停止音乐
   					SoundEngine.sharedEngine().realesAllSounds();
   					// 销毁GameActivity
   					MainActivity.this.finish();
   				}
   			});


   			builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
   			{
   				@Override
   				public void onClick(DialogInterface dialog, int which)
   				{

   				}
   			});


   			//    显示出该对话框
   			builder.show();
   		}

   		return false;
   	}
    

    
}


