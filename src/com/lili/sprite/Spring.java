package com.lili.sprite;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

/**
 * 弹簧精灵
 * @author 石洋
 *
 */
public class Spring extends CCSprite
{
	public CGPoint leftPoint; // 左边
	public CGPoint rightPoint; // 右边
	public CGPoint endPoint; // 终点
	
	public Spring(CGPoint leftPoint, CGPoint rightPoint,CGPoint endPoint)
	{
		this.leftPoint = leftPoint;
		this.rightPoint = rightPoint;
		this.endPoint = endPoint;
	}
	
	@Override
	public void draw(GL10 gl) 
	{
		// 要实现真正的画线
	    gl.glLineWidth(2.0f); // 设置线宽
	    gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f); // 设置颜色
	    gl.glEnable(GL10.GL_LINE_SMOOTH); //把线段反锯齿
		
	    
	    gl.glDisable(GL10.GL_TEXTURE_2D);
	    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	    
	    // ---左边
		float vertices[] = {leftPoint.x, leftPoint.y, endPoint.x, endPoint.y};
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4); //申请内存
		vbb.order(ByteOrder.nativeOrder()); //设置字节顺序，其中ByteOrder.nativeOrder()是获取本机字节顺序
		FloatBuffer vertexBuffer = vbb.asFloatBuffer(); //转换为float型
		vertexBuffer.put(vertices); //添加数据
		vertexBuffer.position(0); //设置缓冲区起始位置
	    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
	    gl.glDrawArrays(GL10.GL_LINES, 0, 2);
	    
	    // ---第二根线
	    float vertices02[] = {rightPoint.x, rightPoint.y, endPoint.x, endPoint.y};
		ByteBuffer vbb02 = ByteBuffer.allocateDirect(vertices02.length * 4); //申请内存
		vbb02.order(ByteOrder.nativeOrder()); //设置字节顺序，其中ByteOrder.nativeOrder()是获取本机字节顺序
		FloatBuffer vertexBuffer02 = vbb02.asFloatBuffer(); //转换为float型
		vertexBuffer02.put(vertices02); //添加数据
		vertexBuffer02.position(0); //设置缓冲区起始位置
	    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer02);
	    gl.glDrawArrays(GL10.GL_LINES, 0, 2);


	    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    gl.glEnable(GL10.GL_TEXTURE_2D);

	    gl.glDisable(GL10.GL_LINE_SMOOTH);
	}
	
	
}



