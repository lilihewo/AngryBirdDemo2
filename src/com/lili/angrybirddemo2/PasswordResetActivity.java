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
 * �������ý���
 * @author shiyang
 *
 */
public class PasswordResetActivity extends Activity {
	
	private MyHandler myHandler; // �ҵĴ�����
	private EditText emailEditText;
	private Button sendEmailButton;
	
	private boolean isEnabled = true; // ��ť�Ƿ����ã�Ĭ������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_reset_activity);
		
		myHandler = new MyHandler(); // ��������������
		emailEditText = (EditText) findViewById(R.id.emailEdiText);
		sendEmailButton = (Button) findViewById(R.id.sendEmail);
	}
	
	
	// �������밴ť����Ӧ����
	public void passwordReset(View view) {
		if (isEnabled) {
			// �����ַ
			String email = emailEditText.getText().toString();
			// ����û�û�������ַ�
			if (isEmpty(email)) {
				Message msg = myHandler.obtainMessage();
				msg.obj = "�����ַ����Ϊ��";
				myHandler.sendMessage(msg);
				return;
			}
			
			// ����ַ�����
			if (isTooLong(email)) {
				Message msg = myHandler.obtainMessage();
				msg.obj = "�����ַ���ܳ���20���ַ�";
				myHandler.sendMessage(msg);
				return;
			}
			
			// ��������
			BmobUser.resetPasswordByEmail(email, new UpdateListener() {
			    @Override
			    public void done(BmobException e) {
			        if(e==null){
						isEnabled = false;
						sendEmailButton.setBackgroundResource(R.drawable.gray);
						CountThread countThread = new CountThread();
						countThread.start();

			        	Message msg = myHandler.obtainMessage(); 
						msg.obj = "�ѷ����������õĵ����ʼ�������ע�����";
			 			myHandler.sendMessage(msg);
			        }else{
			        	Message msg = myHandler.obtainMessage(); 
						msg.obj = "����ʧ�ܣ����������ַ�Ƿ���ȷ";
			 			myHandler.sendMessage(msg);
			        }
			    }
			});

		}
	}
	
	
	// �ж�������е��ı��Ƿ�Ϊ��
	private boolean isEmpty(String text) {
		boolean isEmpty = false;
		if (text.equals("")) {
			isEmpty = true;
		}
		
		return isEmpty;
	}
	
	// �ж��ַ����Ƿ�̫��
	private boolean isTooLong(String text) {
		boolean isTooLong = false;
		if (text.length() >= 20) {
			isTooLong = true;
		}
		
		return isTooLong;
	}
	
//	// �ж��ַ����Ƿ���һ�������ַ
//	private boolean isAEmail(String text) {
//		
//		return false;
//	}
	
	// �ҵĴ�����
	class MyHandler extends Handler {
		
		@Override
		public void handleMessage(Message msg) {
			System.out.println(msg.arg1);
			
			if (msg.arg1 > 0) {
				sendEmailButton.setText(msg.arg1+"������·���");
			} else {
				String data = (String) msg.obj;
				Toast.makeText(PasswordResetActivity.this, data, Toast.LENGTH_SHORT).show();
			}
			
			if (msg.arg2 == 10) {
				// �ָ�ԭ������ʽ
				sendEmailButton.setBackgroundResource(R.drawable.my_button);
				sendEmailButton.setText("������֤�ʼ�");
			}
			
			
			
		}
		
	}
	
	
	private int totalSeconds = 60; // �û��ȴ�60��
	// �����߳���
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

			isEnabled = true; // ������ť
			Message msg = myHandler.obtainMessage();
			msg.arg1 = 1;
			msg.arg2 = 10;
			myHandler.sendMessage(msg);
		}
	}
	
	
	
	
}

























