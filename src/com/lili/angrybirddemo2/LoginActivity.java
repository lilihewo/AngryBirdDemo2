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

// ��¼����
public class LoginActivity extends Activity {
	
	private EditText accountEditText; // �˻�
	private EditText passwordEditText; // ����
	
	private MyHandler myHandler; // ��Ϣ����������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);

		// ������Ϣ����������
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

	// �ж��ַ����Ƿ񳬹�ָ������
	private boolean isTooLong(String text) {
		boolean isTooLong = false;
		
		if (text.length() > 20) {
			isTooLong = true;
		} 
		
		return isTooLong;
	}
	
	private String account; // �˻�
	private String password; // ����

	// ��¼
	public void login(View view) {
		account = accountEditText.getText().toString();
		password = passwordEditText.getText().toString();
		
		if (account.equals("")) {
			Toast.makeText(LoginActivity.this, "�û�������Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		} else {
			if (isTooLong(account)) {
				Toast.makeText(LoginActivity.this, "�û������ܳ���20���ַ�", Toast.LENGTH_SHORT).show();
				return;
			}
		}

		if (password.equals("")) {
			Toast.makeText(LoginActivity.this, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		} else {
			if (isTooLong(password)) {
				Toast.makeText(LoginActivity.this, "���벻�ܳ���20���ַ�", Toast.LENGTH_SHORT).show();
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
					// �˳���Activity��С
					overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
					// ����WelcomeActivity
					LoginActivity.this.finish();
		        }else{
		        	myDialog.dismiss();
		        	Message msg = myHandler.obtainMessage(); 
					msg.obj = "�˺Ų����ڻ��������";
		 			myHandler.sendMessage(msg);
		        }
		    }
		});
	}
	
	
	
	
	// ���û�ע�ᰴť����Ӧ����
	public void register(View view) {
		System.out.println(" WelcomeActivity register");
		
		// ����ע�����
		Intent intent = new Intent();
		intent.setClass(this, RegisterActivity.class);
		this.startActivity(intent);
		// �˳���Activity��С
		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
		// ����WelcomeActivity
		this.finish();
	}
	
	// �������밴ť����Ӧ����
	public void forget(View view) {
		// ��ת����������Ľ���
		Intent intent = new Intent();
		intent.setClass(this, PasswordResetActivity.class);
		this.startActivity(intent);
		// Activity�л�Ч��
		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
	}
	
	
	// ��Ϣ������
	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			String data = (String) msg.obj;
			Toast.makeText(LoginActivity.this, data, Toast.LENGTH_SHORT).show();
		}
	}

	
}




