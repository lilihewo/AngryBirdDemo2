package com.lili.angrybirddemo2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

// 登录界面
public class LoginActivity extends Activity {
	
	private EditText accountEditText; // 账户
	private EditText passwordEditText; // 密码
	
	private MyHandler myHandler; // 消息处理器对象
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);

		// 创建消息处理器对象
		myHandler = new MyHandler();
		
		accountEditText = (EditText) findViewById(R.id.account);
		passwordEditText = (EditText) findViewById(R.id.password);
		
		Intent intent = getIntent();
		String username = intent.getExtras().getString("username");
//		String pwd = intent.getExtras().getString("password");
		if (!username.equals("")) {
			accountEditText.setText(username);
		}
	}

	// 判断字符串是否超过指定长度
	private boolean isTooLong(String text) {
		boolean isTooLong = false;
		
		if (text.length() > 20) {
			isTooLong = true;
		} 
		
		return isTooLong;
	}
	
	private String account; // 账户
	private String password; // 密码

	// 登录
	public void login(View view) {
		account = accountEditText.getText().toString();
		password = passwordEditText.getText().toString();
		
		if (account.equals("")) {
			Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
			return;
		} else {
			if (isTooLong(account)) {
				Toast.makeText(LoginActivity.this, "用户名不能超过20个字符", Toast.LENGTH_SHORT).show();
				return;
			}
		}

		if (password.equals("")) {
			Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
			return;
		} else {
			if (isTooLong(password)) {
				Toast.makeText(LoginActivity.this, "密码不能超过20个字符", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		
		final ProgressDialog myDialog = new ProgressDialog(this);
		myDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		myDialog.show();
		
		BmobUser bu2 = new BmobUser();
		bu2.setUsername(account);
		bu2.setPassword(password);
		bu2.login(new SaveListener<BmobUser>() {
		    @Override
		    public void done(BmobUser bmobUser, BmobException e) {
		        if(e==null){
		        	myDialog.dismiss();
		        	
		        	Intent intent = new Intent();
					intent.putExtra("username", account);
					intent.setClass(LoginActivity.this, MainActivity.class);
					LoginActivity.this.startActivity(intent);
					// 退出的Activity缩小
					overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
					// 销毁WelcomeActivity
					LoginActivity.this.finish();
		        }else{
		        	myDialog.dismiss();
		        	Message msg = myHandler.obtainMessage(); 
					msg.obj = "账号不存在或密码错误";
		 			myHandler.sendMessage(msg);
		        }
		    }
		});
	}
	
	
	
	
	// 新用户注册按钮的响应函数
	public void register(View view) {
		System.out.println(" WelcomeActivity register");
		
		// 跳到注册界面
		Intent intent = new Intent();
		intent.setClass(this, RegisterActivity.class);
		this.startActivity(intent);
		// 退出的Activity缩小
		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
		// 销毁WelcomeActivity
		this.finish();
	}
	
	// 忘记密码按钮的响应函数
	public void forget(View view) {
		// 跳转到重置密码的界面
		Intent intent = new Intent();
		intent.setClass(this, PasswordResetActivity.class);
		this.startActivity(intent);
		// Activity切换效果
		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
	}
	
	
	// 消息处理器
	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			String data = (String) msg.obj;
			Toast.makeText(LoginActivity.this, data, Toast.LENGTH_SHORT).show();
		}
	}

	
}




