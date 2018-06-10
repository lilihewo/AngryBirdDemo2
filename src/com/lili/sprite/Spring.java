package com.lili.sprite;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

/**
 * ���ɾ���
 * @author ʯ��
 *
 */
public class Spring extends CCSprite
{
	public CGPoint leftPoint; // ���
	public CGPoint rightPoint; // �ұ�
	public CGPoint endPoint; // �յ�
	
	public Spring(CGPoint leftPoint, CGPoint rightPoint,CGPoint endPoint)
	{
		this.leftPoint = leftPoint;
		this.rightPoint = rightPoint;
		this.endPoint = endPoint;
	}
	
	@Override
	public void draw(GL10 gl) 
	{
		// Ҫʵ�������Ļ���
	    gl.glLineWidth(2.0f); // �����߿�
	    gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f); // ������ɫ
	    gl.glEnable(GL10.GL_LINE_SMOOTH); //���߶η����
		
	    
	    gl.glDisable(GL10.GL_TEXTURE_2D);
	    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	    
	    // ---���
		float vertices[] = {leftPoint.x, leftPoint.y, endPoint.x, endPoint.y};
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4); //�����ڴ�
		vbb.order(ByteOrder.nativeOrder()); //�����ֽ�˳������ByteOrder.nativeOrder()�ǻ�ȡ�����ֽ�˳��
		FloatBuffer vertexBuffer = vbb.asFloatBuffer(); //ת��Ϊfloat��
		vertexBuffer.put(vertices); //�������
		vertexBuffer.position(0); //���û�������ʼλ��
	    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
	    gl.glDrawArrays(GL10.GL_LINES, 0, 2);
	    
	    // ---�ڶ�����
	    float vertices02[] = {rightPoint.x, rightPoint.y, endPoint.x, endPoint.y};
		ByteBuffer vbb02 = ByteBuffer.allocateDirect(vertices02.length * 4); //�����ڴ�
		vbb02.order(ByteOrder.nativeOrder()); //�����ֽ�˳������ByteOrder.nativeOrder()�ǻ�ȡ�����ֽ�˳��
		FloatBuffer vertexBuffer02 = vbb02.asFloatBuffer(); //ת��Ϊfloat��
		vertexBuffer02.put(vertices02); //�������
		vertexBuffer02.position(0); //���û�������ʼλ��
	    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer02);
	    gl.glDrawArrays(GL10.GL_LINES, 0, 2);


	    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    gl.glEnable(GL10.GL_TEXTURE_2D);

	    gl.glDisable(GL10.GL_LINE_SMOOTH);
	}
	
	
}



