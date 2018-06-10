package com.lili.sprite;

/**
 * 精灵数据类
 * @author shiyang
 *
 */
public class SpriteData {
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	
	private String name; // 精灵的名字
	private String filePath; // 文件路径
	private float x; // X坐标
	private float y; // Y坐标
	private float scale; // 比例	
	private String shape; // 形状

}





