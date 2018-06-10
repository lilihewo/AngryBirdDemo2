package com.lili.angrybirddemo2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 密码重置界面
 * @author shiyang
 *
 */
public class PasswordResetActivity extends Activity {
	
	private MyHandler myHandler; // 我的处理器
	private EditText emailEditText;
	private Button sendEmailButton;
	
	private boolean isEnabled = true; // 按钮是否启用，默认启用
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_reset_activity);
		
		myHandler = new MyHandler(); // 创建处理器对象
		emailEditText = (EditText) findViewById(R.id.emailEdiText);
		sendEmailButton = (Button) findViewById(R.id.sendEmail);
	}
	
	
	// 重置密码按钮的响应函数
	public void passwordReset(View view) {
		if (isEnabled) {
			// 邮箱地址
			String email = emailEditText.getText().toString();
			// 如果用户没有输入字符
			if (isEmpty(email)) {
				Message msg = myHandler.obtainMessage();
				msg.obj = "邮箱地址不能为空";
				myHandler.sendMessage(msg);
				return;
			}
			
			// 如果字符过长
			if (isTooLong(email)) {
				Message msg = myHandler.obtainMessage();
				msg.obj = "邮箱地址不能超过20个字符";
				myHandler.sendMessage(msg);
				return;
			}
			
			// 重置密码
			BmobUser.resetPasswordByEmail(email, new UpdateListener() {
			    @Override
			    public void done(BmobException e) {
			        if(e==null){
						isEnabled = false;
						sendEmailButton.setBackgroundResource(R.drawable.gray);
						CountThread countThread = new CountThread();
						countThread.start();

			        	Message msg = myHandler.obtainMessage(); 
						msg.obj = "已发送密码重置的电子邮件给您，注意查收";
			 			myHandler.sendMessage(msg);
			        }else{
			        	Message msg = myHandler.obtainMessage(); 
						msg.obj = "发送失败，请检查邮箱地址是否正确";
			 			myHandler.sendMessage(msg);
			        }
			    }
			});

		}
	}
	
	
	// 判断输入框中的文本是否为空
	private boolean isEmpty(String text) {
		boolean isEmpty = false;
		if (text.equals("")) {
			isEmpty = true;
		}
		
		return isEmpty;
	}
	
	// 判断字符串是否太长
	private boolean isTooLong(String text) {
		boolean isTooLong = false;
		if (text.length() >= 20) {
			isTooLong = true;
		}
		
		return isTooLong;
	}
	
//	// 判断字符串是否是一个邮箱地址
//	private boolean isAEmail(String text) {
//		
//		return false;
//	}
	
	// 我的处理器
	class MyHandler extends Handler {
		
		@Override
		public void handleMessage(Message msg) {
			System.out.println(msg.arg1);
			
			if (msg.arg1 > 0) {
				sendEmailButton.setText(msg.arg1+"秒后重新发送");
			} else {
				String data = (String) msg.obj;
				Toast.makeText(PasswordResetActivity.this, data, Toast.LENGTH_SHORT).show();
			}
			
			if (msg.arg2 == 10) {
				// 恢复原来的样式
				sendEmailButton.setBackgroundResource(R.drawable.my_button);
				sendEmailButton.setText("发送验证邮件");
			}
			
			
			
		}
		
	}
	
	
	private int totalSeconds = 60; // 用户等待60秒
	// 计数线程类
	class CountThread extends Thread {
		public void run() {
			while(totalSeconds > 1) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				totalSeconds--;
				Message msg = myHandler.obtainMessage();
				msg.arg1 = totalSeconds;
				myHandler.sendMessage(msg);
			}

			isEnabled = true; // 启动按钮
			Message msg = myHandler.obtainMessage();
			msg.arg1 = 1;
			msg.arg2 = 10;
			myHandler.sendMessage(msg);
		}
	}
	
	
	
	
}

























