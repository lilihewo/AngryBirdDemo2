package com.lili.model;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import com.lili.sprite.Spring;

/**
 * ��ŭ��С��ͼ����߼���
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

	private boolean birdIsFire = false; // С���Ƿ���Է���ı�־λ

	
	/**
	 * ��ָ�ƶ�ʱ�ı侫��λ�õĺ���
	 * @param fingerPosition ��ָ��λ��
	 * @param sprite ����
	 * @param spring ����
	 * @param springLength ���ɵĳ���
	 * @param firePoint �����
	 */
	public void changeSpritePosition(CGPoint fingerPosition, CCSprite bird, Spring spring, float springLength, CGPoint firePoint)
	{
		// ���ɵķ�����봥����Ĵ�ֱ����
		float springAndfingerVerticalDistance = fingerPosition.y - firePoint.y;
		// ���ɵķ�����봥����Ĵ�ֱ�����ƽ��
		float springAndfingerVerticalDistanceSquare = (float) Math.pow(springAndfingerVerticalDistance, 2);
		// ���ɵķ�����봥�����ˮƽ����
		float springAndfingerHorizontalDistance = fingerPosition.x - firePoint.x;
		// ���ɵķ�����봥�����ˮƽ�����ƽ��
		float springAndfingerHorizontaDistanceSquare = (float) Math.pow(springAndfingerHorizontalDistance, 2);
		// ���ɵķ�����봥����ľ���
		float springAndfingerDistance = (float) Math.sqrt(springAndfingerVerticalDistanceSquare + springAndfingerHorizontaDistanceSquare);
		
		// ������ɵķ�����봥����ľ������12
		if (springAndfingerDistance > 12)
		{
			this.setBirdIsFire(true); // С����Է���
		}
		
		// ������ɵķ�����봥����ľ���С�ڵ��ɵ�������쳤��
		if (springAndfingerDistance < springLength)
		{
			bird.setPosition(fingerPosition); // �����λ��Ϊ��ָ��λ��
			spring.endPoint = fingerPosition; // ���ɵ��յ�Ϊ��ָ��λ��
		} 
		
		// ������ɵķ�����봥����ľ�����ڵ��ɵ�������쳤��
		else 
		{
			float sinX = springAndfingerVerticalDistance / springAndfingerDistance; // ��������ֵ
			// �����ĵ��뾫��Ĵ�ֱ����
			float slingShotAndSpriteVerticalDistance = springLength * sinX;
			float cosX = (float) Math.sqrt(1 - sinX*sinX); // ��������ֵ
			// �����ĵ��뾫���ˮƽ����
			float slingShotAndSpriteHorizontalDistance = springLength * cosX;
			
			// ����������ھ�����ұ�
			if (springAndfingerHorizontalDistance > 0)
			{
				// �ı�С���λ��
				bird.setPosition(firePoint.x + slingShotAndSpriteHorizontalDistance, firePoint.y + slingShotAndSpriteVerticalDistance);
				// �ı䵯�ɵ��յ�λ��
				spring.endPoint.set(firePoint.x + slingShotAndSpriteHorizontalDistance, firePoint.y + slingShotAndSpriteVerticalDistance);
			} 
			// ����������ھ�������
			else 
			{
				// �ı�С���λ��
				bird.setPosition(firePoint.x - slingShotAndSpriteHorizontalDistance, firePoint.y + slingShotAndSpriteVerticalDistance);
				// �ı䵯�����յ�λ��
				spring.endPoint.set(firePoint.x - slingShotAndSpriteHorizontalDistance, firePoint.y + slingShotAndSpriteVerticalDistance);
			}
		}
	}
	
	
	
}




