package com.lili.layer;

import java.util.ArrayList;

import org.cocos2d.actions.interval.CCJumpTo;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import android.view.MotionEvent;

import com.lili.engine.PhysicalEngine;
import com.lili.model.BirdLayerLogic;
import com.lili.sprite.Bird;
import com.lili.sprite.Spring;
import com.lili.sprite.SpriteData;

/**
 * ��ŭ��С��ͼ��
 * @author shiyang
 *
 */
public class BirdLayer extends CCLayer {
	private BirdLayerLogic birdLayerLogic;
	
	private int birdNumber = 3; // С��ĸ���
	private ArrayList<CCSprite> birdSpriteList; // С�񼯺�
	private CGPoint firePoint; // ����ĵ�
	
	private Spring spring; // ���ɶ���
	private PhysicalEngine physicalEngine; // �����������
	
	// ���캯��
	public BirdLayer(ArrayList list) {
		initLayer(); // ��ʼ��ͼ��
		
		// ͼ���߼�����
		birdLayerLogic = new BirdLayerLogic();
		
		birdJump(0); // С����Ծ
		
		// ������������
		physicalEngine = new PhysicalEngine();
		physicalEngine.createPhysicalWorld();

		// ������Ļ���ܵĸ���
		physicalEngine.createScreenBody();
		
		loadSprite(list); // ���ر��ܺ�ľ��

		physicalEngine.startWorld(); // ��ʼģ��
		
		this.setIsTouchEnabled(true); // �򿪵���¼�
		
		// ����һ����ʱ�����ж�С���Ƿ�������
		this.schedule("isGameEnd", 1.0f);
	}

	/**
	 * ��ʼ��ͼ��
	 */
	private void initLayer() {
		// ����
		CCSprite bg = CCSprite.sprite("bird_layer/fight_bg.png");
		bg.setAnchorPoint(0, 0);
		this.addChild(bg);
		
		createSlingshot(); // ������������
		firePoint = createSpring(); // �������ɾ���
		birdSpriteList = createBirdSprite(birdNumber); // ����С����
	}
	
	/**
	 * ���ر��ܺ�ľ��
	 */
	private void loadSprite(ArrayList list) {
	    // ��������
		for (int i = 0; i < list.size(); i++) {
			// �õ��������ݶ���
			SpriteData spriteData = (SpriteData) list.get(i);
			
			// �����������
			CCSprite sprite = CCSprite.sprite(spriteData.getFilePath());
			// ����λ��
			sprite.setPosition(spriteData.getX(), spriteData.getY());
			// ���ô�С
			sprite.setScale(spriteData.getScale());
			// ��ӵ�ͼ��
			this.addChild(sprite);
			
			// ����Ƕ����
			if (spriteData.getShape().equals("polygon"))
			{
				physicalEngine.createPolygonBody(sprite); // ��������θ���
			} 
			
			// �����Բ��
			else if (spriteData.getShape().equals("circle"))
			{
				physicalEngine.createCircleBody(sprite); // ����Բ�θ���
			}
		}
	}

	
	/**
	 * ������������ĺ���
	 */
	private void createSlingshot() {
		// �����󵯹��������
		CCSprite leftShotSprite = CCSprite.sprite("bird_layer/left.png");
		leftShotSprite.setPosition(68.00f, 83.00f); // ����λ��
		this.addChild(leftShotSprite,1); // ��Ӿ���

		// �����ҵ�������
		CCSprite rightShotSprite = CCSprite.sprite("bird_layer/right.png");
		rightShotSprite.setPosition(80.00f, 83.00f); // ����λ��
		this.addChild(rightShotSprite); // ��Ӿ���
	}

	
	/**
	 * �������ɾ���ĺ���
	 * @return ���ɵ����ĵ㣨Ҳ���Ƿ����firePoint��
	 */
	private CGPoint createSpring() {
		CGPoint[] points = getFirePoints();
		// �������ɾ������
		spring = new Spring(points[0], points[1], points[2]);
		spring.setPosition(0, 0); // ������ǻ��ߵĻ�׼��
		this.addChild(spring); // ��ӵ��ɾ������
		
		return points[2];
	}
	

	/**
	 *  ����С����ĺ���
	 * @param birdSize �ж���ֻС��
	 * @return С�񼯺�
	 */
	private ArrayList<CCSprite> createBirdSprite(int birdSize)
	{
		// �������϶���
		ArrayList<CCSprite> birdSpriteList = new ArrayList<CCSprite>();
		
		// ����С�������
		for (int i = 0; i < birdSize; i++)
		{
			Bird birdSprite = new Bird("bird_layer/bird1.png");
			birdSprite.setPosition(16.00f + i * 30, 65.50f);
//			birdSprite.setScale(0.5f);
			birdSprite.setScale(0.8f);
			this.addChild(birdSprite); // ��ӵ�ͼ��
			
			birdSpriteList.add(birdSprite); // ��С������ӵ�С�񼯺�
		}
		
		return birdSpriteList;
	}
	

	/**
	 * �õ����ɵ����У���������
	 * @return ������
	 */
	private CGPoint[] getFirePoints() {
		CGPoint[] points = new CGPoint[3];
		// ���
		CGPoint leftPoint = new CGPoint();
		leftPoint.x = 69.00f;
		leftPoint.y = 99.50f;
		points[0] = leftPoint;
		
		// �ұ�
		CGPoint rightPoint = new CGPoint();
		rightPoint.x = 79.00f;
		rightPoint.y = 99.50f;
		points[1] = rightPoint;
		
		// �����
		CGPoint firePoint = new CGPoint();
		firePoint.x = leftPoint.x + (rightPoint.x - leftPoint.x) / 2;
		firePoint.y = leftPoint.y;
		points[2] = firePoint;
		
		return points;
	}
	
	
	/**
	 * С������������
	 * @param index �ڼ���С�����������
	 */
	private void birdJump(int index)
	{
		// С����Ծ
		CCSprite birdSprite = birdSpriteList.get(index);
		CCJumpTo jumpTo = CCJumpTo.action(1, firePoint, 
				firePoint.y - birdSprite.getPosition().y, 1);
		birdSprite.runAction(jumpTo);
	}

	
	
	// �û�ѡ�����Ϸ����
	private Bird selectedGameObject = null;
	private CGPoint beginPoint;
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		beginPoint = convertTouchToNodeSpace(event);
		
		for (int i = 0; i < birdSpriteList.size(); i++) {
			Bird bird = (Bird) birdSpriteList.get(i);
			if (CGRect.containsPoint(bird.getBoundingBox(),beginPoint)) {
				selectedGameObject = bird;
				break;
			}
		}

		return super.ccTouchesBegan(event);
	}
	
	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		// ��ָ��λ��
		CGPoint fingerPosition = convertTouchToNodeSpace(event);
		// ���ɵ�������쳤��
		float springLength = 40.0f;

		// ���ѡ����С��
		if(selectedGameObject != null)
		{
			// ������ָ�ƶ�ʱ�ı侫��λ�õĺ���
			birdLayerLogic.changeSpritePosition(fingerPosition, selectedGameObject, spring, 
					springLength, firePoint);
		}

		return super.ccTouchesMoved(event);
	}
	
	
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		
		// ��������С�����Ķ�ʱ��
		this.schedule("createBirdBody", 0.01f);
		
		return super.ccTouchesEnded(event);
	}
	
	public void createBirdBody(float f) {
		// ���ٴ���С�����Ķ�ʱ��
		this.unschedule("createBirdBody");
		
		// ���ѡ����С��
		if (selectedGameObject != null)
		{
			// �õ��ɸ�λ
			spring.endPoint = firePoint;
			if (birdLayerLogic.isBirdIsFire())
			{
				// �����������д�������
				Body birdBody = physicalEngine.createBodyInWorld(selectedGameObject);
				if (birdBody != null) {
					float x =(firePoint.x - selectedGameObject.getPosition().x) * 2.0f;
					float y =(firePoint.y - selectedGameObject.getPosition().y) * 2.0f;
					Vec2 force = new Vec2(x, y); // ���ĵ�Vec2

					// С���ܵ�һ����
//					birdBody.applyLinearImpulse(force, birdBody.getPosition());
					birdBody.applyLinearImpulse(force, birdBody.getPosition(), true);

					birdLayerLogic.setBirdIsFire(false);
					birdSpriteList.remove(selectedGameObject);
					if (birdSpriteList.size() > 0) {
						birdJump(0);
					}
					
					selectedGameObject = null;
				} else { // �����������ʧ��
					// ���Լ�������
					this.schedule("createBirdBody", 0.01f);
					System.out.println("���Լ�������");
				}
			} else {
				// С��λ
				selectedGameObject.setPosition(firePoint);
				selectedGameObject = null;
			}	
		}

	}
	
	

	
	// ��Ϸ����
	public void isGameEnd(float f) {
		// ���С��������
		if (birdSpriteList.size() == 0) {
			// ֹͣ�ж���Ϸ�Ƿ�����Ķ�ʱ��
			this.unschedule("isGameEnd");

			// �������һ��ͼ��Ķ�ʱ��
			this.schedule("addOneLayer", 3.0f);
		}
	}
	
	// ���һ��ͼ��Ķ�ʱ��
	public void addOneLayer(float f) {
		// ȡ����ʱ��
		this.unschedule("addOneLayer");
		addOneLayer(this, new LuckyLayer());
	}
	
	
	
	/**
	 * ���һ��ͼ��
	 * @param self �Լ�
	 * @param oneLayer ��Ҫ��ӵ�һ��ͼ��
	 */
	private void addOneLayer(CCLayer self, CCLayer oneLayer) {
		// self.onExit(); // �ȹر��Լ��Ĵ���

		oneLayer.setScale(0.0f); // ��СΪ0
		oneLayer.setAnchorPoint(0.5f, 0.5f); // ����ê��
		CCScaleTo scaleTo = CCScaleTo.action(0.2f, 0.6f); // �Ŵ�Ч��
		oneLayer.runAction(scaleTo); // ���зŴ�Ч��
		self.getParent().addChild(oneLayer); // ��ӵ�������
	}
}




