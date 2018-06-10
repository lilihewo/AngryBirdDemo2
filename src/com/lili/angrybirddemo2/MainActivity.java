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
 * һ�����������Ŀ�Ĺ�����Ա��֪�����һ�½���Ż���ת������ͼ�����ת˳��ı�һ��
 * ��ԭ����
 * LogoLayer -> LoadingLayer -> StartLayer -> LevelLayer
 * ��Ϊ
 * StartLayer -> LevelLayer
 * 
 * ��������ˮƽ���ޣ�û��Ū������α�֤100%�ɹ��������塣
 * ���û�ܳɹ��������壬�������ָ���������
 */

/**
 * ��ŭ��С��Demo
 * @author shiyang
 *
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ================���λ������������ע��======================
        // �����appkey���ҵ�appkey������ʹ�ñ�Դ��ʱ���������appkey
        
        String appkey = "ebf0abaa831caef814bf09f371c654d4";
        Bmob.initialize(this, appkey);
        // ================���λ������������ע��======================
        
        // --> ����CCGLSurfaceView����
        CCGLSurfaceView view = new CCGLSurfaceView(this);
        setContentView(view); // ����Activity��ʾ��View
        // --> ��ȡ���ݶ���
        CCDirector director = CCDirector.sharedDirector();
        director.setDeviceOrientation(CCDirector.
        		kCCDeviceOrientationLandscapeLeft); // ����ǿ�ƺ�����ʾ
        director.setDisplayFPS(true); // ��ʾ֡��
        director.attachInView(view); // ��ʼ�����߳�
        
        director.setScreenSize(480.0f, 320.0f);
        
        CCScene scene = CCScene.node(); // ����һ����������
        
//        LogoLayer logoLayer = new LogoLayer(); // ����logoͼ�����
        StartLayer startLayer = new StartLayer(); // ����startLayerͼ�����
        scene.addChild(startLayer); // ��logoͼ����ӵ�����
        
        // �������г���
        director.runWithScene(scene);  
    }

    /**
     * ��ͣ
     */
    @Override
    protected void onPause() 
    {
    	super.onPause();
    	
    	CCDirector.sharedDirector().onPause(); //��Ϸ��ͣ
//    	SoundEngine.sharedEngine().pauseSound(); //��ͣ����
    }
    
    /**
     * ����
     */
    @Override
    protected void onResume() 
    {
    	super.onResume();
    	
    	CCDirector.sharedDirector().onResume(); //��Ϸ����
//    	SoundEngine.sharedEngine().resumeSound(); //���ּ���
    }
    
    /**
     * ����
     */
    @Override
    protected void onDestroy() 
    {
    	super.onDestroy();
    	
    	CCDirector.sharedDirector().end(); //��Ϸ����
 
//    	SoundEngine.sharedEngine().realesAllSounds(); // ���ֽ���
    }
    
    
    
	// �û����·��ؼ�
	@Override
   	public boolean onKeyDown(int keyCode, KeyEvent event)
   	{
   		if (keyCode == KeyEvent.KEYCODE_BACK )
   		{
   			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
   			builder.setIcon(R.drawable.icon);
   			builder.setTitle("�˳�����Ϸ��");
   			builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
   			{
   				@Override
   				public void onClick(DialogInterface dialog, int which)
   				{
   					// ֹͣ����
   					SoundEngine.sharedEngine().realesAllSounds();
   					// ����GameActivity
   					MainActivity.this.finish();
   				}
   			});


   			builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener()
   			{
   				@Override
   				public void onClick(DialogInterface dialog, int which)
   				{

   				}
   			});


   			//    ��ʾ���öԻ���
   			builder.show();
   		}

   		return false;
   	}
    

    
}


