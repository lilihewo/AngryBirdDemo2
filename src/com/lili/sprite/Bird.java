package com.lili.sprite;

import org.cocos2d.nodes.CCSprite;

/**
 * С����
 * @author shiyang
 *
 */
public class Bird extends CCSprite {

	private int HP; // Ѫ��
	
	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public Bird(String filePath) {
		super(filePath);
	}

}





