package com.lili.model;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import com.lili.sprite.Spring;

/**
 * 愤怒的小鸟图层的逻辑类
 * @author shiyang
 *
 */
public class BirdLayerLogic {
	
	public boolean isBirdIsFire() {
		return birdIsFire;
	}

	public void setBirdIsFire(boolean birdIsFire) {
		this.birdIsFire = birdIsFire;
	}

	private boolean birdIsFire = false; // 小鸟是否可以发射的标志位

	
	/**
	 * 手指移动时改变精灵位置的函数
	 * @param fingerPosition 手指的位置
	 * @param sprite 精灵
	 * @param spring 弹簧
	 * @param springLength 弹簧的长度
	 * @param firePoint 发射点
	 */
	public void changeSpritePosition(CGPoint fingerPosition, CCSprite bird, Spring spring, float springLength, CGPoint firePoint)
	{
		// 弹簧的发射点与触摸点的垂直距离
		float springAndfingerVerticalDistance = fingerPosition.y - firePoint.y;
		// 弹簧的发射点与触摸点的垂直距离的平方
		float springAndfingerVerticalDistanceSquare = (float) Math.pow(springAndfingerVerticalDistance, 2);
		// 弹簧的发射点与触摸点的水平距离
		float springAndfingerHorizontalDistance = fingerPosition.x - firePoint.x;
		// 弹簧的发射点与触摸点的水平距离的平方
		float springAndfingerHorizontaDistanceSquare = (float) Math.pow(springAndfingerHorizontalDistance, 2);
		// 弹簧的发射点与触摸点的距离
		float springAndfingerDistance = (float) Math.sqrt(springAndfingerVerticalDistanceSquare + springAndfingerHorizontaDistanceSquare);
		
		// 如果弹簧的发射点与触摸点的距离大于12
		if (springAndfingerDistance > 12)
		{
			this.setBirdIsFire(true); // 小鸟可以发射
		}
		
		// 如果弹簧的发射点与触摸点的距离小于弹簧的最大拉伸长度
		if (springAndfingerDistance < springLength)
		{
			bird.setPosition(fingerPosition); // 精灵的位置为手指的位置
			spring.endPoint = fingerPosition; // 弹簧的终点为手指的位置
		} 
		
		// 如果弹簧的发射点与触摸点的距离大于弹簧的最大拉伸长度
		else 
		{
			float sinX = springAndfingerVerticalDistance / springAndfingerDistance; // 计算正弦值
			// 弹弓的点与精灵的垂直距离
			float slingShotAndSpriteVerticalDistance = springLength * sinX;
			float cosX = (float) Math.sqrt(1 - sinX*sinX); // 计算余弦值
			// 弹弓的点与精灵的水平距离
			float slingShotAndSpriteHorizontalDistance = springLength * cosX;
			
			// 如果触摸点在精灵的右边
			if (springAndfingerHorizontalDistance > 0)
			{
				// 改变小鸟的位置
				bird.setPosition(firePoint.x + slingShotAndSpriteHorizontalDistance, firePoint.y + slingShotAndSpriteVerticalDistance);
				// 改变弹簧的终点位置
				spring.endPoint.set(firePoint.x + slingShotAndSpriteHorizontalDistance, firePoint.y + slingShotAndSpriteVerticalDistance);
			} 
			// 如果触摸点在精灵的左边
			else 
			{
				// 改变小鸟的位置
				bird.setPosition(firePoint.x - slingShotAndSpriteHorizontalDistance, firePoint.y + slingShotAndSpriteVerticalDistance);
				// 改变弹弓的终点位置
				spring.endPoint.set(firePoint.x - slingShotAndSpriteHorizontalDistance, firePoint.y + slingShotAndSpriteVerticalDistance);
			}
		}
	}
	
	
	
}




