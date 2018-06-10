package com.lili.sprite;

import org.cocos2d.nodes.CCSprite;

/**
 * Ð¡Äñ¾«Áé
 * @author shiyang
 *
 */
public class Bird extends CCSprite {

	private int HP; // ÑªÁ¿
	
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





