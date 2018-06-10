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
 * 愤怒的小鸟图层
 * @author shiyang
 *
 */
public class BirdLayer extends CCLayer {
	private BirdLayerLogic birdLayerLogic;
	
	private int birdNumber = 3; // 小鸟的个数
	private ArrayList<CCSprite> birdSpriteList; // 小鸟集合
	private CGPoint firePoint; // 发射的点
	
	private Spring spring; // 弹簧对象
	private PhysicalEngine physicalEngine; // 物理引擎对象
	
	// 构造函数
	public BirdLayer(ArrayList list) {
		initLayer(); // 初始化图层
		
		// 图层逻辑对象
		birdLayerLogic = new BirdLayerLogic();
		
		birdJump(0); // 小鸟跳跃
		
		// 创建物理世界
		physicalEngine = new PhysicalEngine();
		physicalEngine.createPhysicalWorld();

		// 创建屏幕四周的刚体
		physicalEngine.createScreenBody();
		
		loadSprite(list); // 加载冰架和木架

		physicalEngine.startWorld(); // 开始模拟
		
		this.setIsTouchEnabled(true); // 打开点击事件
		
		// 启动一个定时器来判断小鸟是否发射完了
		this.schedule("isGameEnd", 1.0f);
	}

	/**
	 * 初始化图层
	 */
	private void initLayer() {
		// 背景
		CCSprite bg = CCSprite.sprite("bird_layer/fight_bg.png");
		bg.setAnchorPoint(0, 0);
		this.addChild(bg);
		
		createSlingshot(); // 创建弹弓精灵
		firePoint = createSpring(); // 创建弹簧精灵
		birdSpriteList = createBirdSprite(birdNumber); // 创建小鸟精灵
	}
	
	/**
	 * 加载冰架和木架
	 */
	private void loadSprite(ArrayList list) {
	    // 遍历数组
		for (int i = 0; i < list.size(); i++) {
			// 得到精灵数据对象
			SpriteData spriteData = (SpriteData) list.get(i);
			
			// 创建精灵对象
			CCSprite sprite = CCSprite.sprite(spriteData.getFilePath());
			// 设置位置
			sprite.setPosition(spriteData.getX(), spriteData.getY());
			// 设置大小
			sprite.setScale(spriteData.getScale());
			// 添加到图层
			this.addChild(sprite);
			
			// 如果是多边形
			if (spriteData.getShape().equals("polygon"))
			{
				physicalEngine.createPolygonBody(sprite); // 创建多边形刚体
			} 
			
			// 如果是圆形
			else if (spriteData.getShape().equals("circle"))
			{
				physicalEngine.createCircleBody(sprite); // 创建圆形刚体
			}
		}
	}

	
	/**
	 * 创建弹弓精灵的函数
	 */
	private void createSlingshot() {
		// 创建左弹弓精灵对象
		CCSprite leftShotSprite = CCSprite.sprite("bird_layer/left.png");
		leftShotSprite.setPosition(68.00f, 83.00f); // 设置位置
		this.addChild(leftShotSprite,1); // 添加精灵

		// 创建右弹弓对象
		CCSprite rightShotSprite = CCSprite.sprite("bird_layer/right.png");
		rightShotSprite.setPosition(80.00f, 83.00f); // 设置位置
		this.addChild(rightShotSprite); // 添加精灵
	}

	
	/**
	 * 创建弹簧精灵的函数
	 * @return 弹簧的中心点（也就是发射点firePoint）
	 */
	private CGPoint createSpring() {
		CGPoint[] points = getFirePoints();
		// 创建弹簧精灵对象
		spring = new Spring(points[0], points[1], points[2]);
		spring.setPosition(0, 0); // 这个点是画线的基准点
		this.addChild(spring); // 添加弹簧精灵对象
		
		return points[2];
	}
	

	/**
	 *  创建小鸟精灵的函数
	 * @param birdSize 有多少只小鸟
	 * @return 小鸟集合
	 */
	private ArrayList<CCSprite> createBirdSprite(int birdSize)
	{
		// 创建集合对象
		ArrayList<CCSprite> birdSpriteList = new ArrayList<CCSprite>();
		
		// 创建小鸟精灵对象
		for (int i = 0; i < birdSize; i++)
		{
			Bird birdSprite = new Bird("bird_layer/bird1.png");
			birdSprite.setPosition(16.00f + i * 30, 65.50f);
//			birdSprite.setScale(0.5f);
			birdSprite.setScale(0.8f);
			this.addChild(birdSprite); // 添加到图层
			
			birdSpriteList.add(birdSprite); // 将小鸟精灵添加到小鸟集合
		}
		
		return birdSpriteList;
	}
	

	/**
	 * 得到弹簧的左，中，右三个点
	 * @return 点数组
	 */
	private CGPoint[] getFirePoints() {
		CGPoint[] points = new CGPoint[3];
		// 左边
		CGPoint leftPoint = new CGPoint();
		leftPoint.x = 69.00f;
		leftPoint.y = 99.50f;
		points[0] = leftPoint;
		
		// 右边
		CGPoint rightPoint = new CGPoint();
		rightPoint.x = 79.00f;
		rightPoint.y = 99.50f;
		points[1] = rightPoint;
		
		// 发射点
		CGPoint firePoint = new CGPoint();
		firePoint.x = leftPoint.x + (rightPoint.x - leftPoint.x) / 2;
		firePoint.y = leftPoint.y;
		points[2] = firePoint;
		
		return points;
	}
	
	
	/**
	 * 小鸟跳到弹弓上
	 * @param index 第几个小鸟调到弹弓上
	 */
	private void birdJump(int index)
	{
		// 小鸟跳跃
		CCSprite birdSprite = birdSpriteList.get(index);
		CCJumpTo jumpTo = CCJumpTo.action(1, firePoint, 
				firePoint.y - birdSprite.getPosition().y, 1);
		birdSprite.runAction(jumpTo);
	}

	
	
	// 用户选择的游戏物体
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
		// 手指的位置
		CGPoint fingerPosition = convertTouchToNodeSpace(event);
		// 弹簧的最大拉伸长度
		float springLength = 40.0f;

		// 如果选中了小鸟
		if(selectedGameObject != null)
		{
			// 调用手指移动时改变精灵位置的函数
			birdLayerLogic.changeSpritePosition(fingerPosition, selectedGameObject, spring, 
					springLength, firePoint);
		}

		return super.ccTouchesMoved(event);
	}
	
	
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		
		// 启动创建小鸟刚体的定时器
		this.schedule("createBirdBody", 0.01f);
		
		return super.ccTouchesEnded(event);
	}
	
	public void createBirdBody(float f) {
		// 销毁创建小鸟刚体的定时器
		this.unschedule("createBirdBody");
		
		// 如果选中了小鸟
		if (selectedGameObject != null)
		{
			// 让弹簧复位
			spring.endPoint = firePoint;
			if (birdLayerLogic.isBirdIsFire())
			{
				// 在物理世界中创建刚体
				Body birdBody = physicalEngine.createBodyInWorld(selectedGameObject);
				if (birdBody != null) {
					float x =(firePoint.x - selectedGameObject.getPosition().x) * 2.0f;
					float y =(firePoint.y - selectedGameObject.getPosition().y) * 2.0f;
					Vec2 force = new Vec2(x, y); // 力的的Vec2

					// 小鸟受到一个力
//					birdBody.applyLinearImpulse(force, birdBody.getPosition());
					birdBody.applyLinearImpulse(force, birdBody.getPosition(), true);

					birdLayerLogic.setBirdIsFire(false);
					birdSpriteList.remove(selectedGameObject);
					if (birdSpriteList.size() > 0) {
						birdJump(0);
					}
					
					selectedGameObject = null;
				} else { // 如果创建刚体失败
					// 则尝试继续创建
					this.schedule("createBirdBody", 0.01f);
					System.out.println("尝试继续创建");
				}
			} else {
				// 小鸟复位
				selectedGameObject.setPosition(firePoint);
				selectedGameObject = null;
			}	
		}

	}
	
	

	
	// 游戏结束
	public void isGameEnd(float f) {
		// 如果小鸟发射完了
		if (birdSpriteList.size() == 0) {
			// 停止判断游戏是否结束的定时器
			this.unschedule("isGameEnd");

			// 创建添加一个图层的定时器
			this.schedule("addOneLayer", 3.0f);
		}
	}
	
	// 添加一个图层的定时器
	public void addOneLayer(float f) {
		// 取消定时器
		this.unschedule("addOneLayer");
		addOneLayer(this, new LuckyLayer());
	}
	
	
	
	/**
	 * 添加一个图层
	 * @param self 自己
	 * @param oneLayer 将要添加的一个图层
	 */
	private void addOneLayer(CCLayer self, CCLayer oneLayer) {
		// self.onExit(); // 先关闭自己的触摸

		oneLayer.setScale(0.0f); // 大小为0
		oneLayer.setAnchorPoint(0.5f, 0.5f); // 设置锚点
		CCScaleTo scaleTo = CCScaleTo.action(0.2f, 0.6f); // 放大效果
		oneLayer.runAction(scaleTo); // 运行放大效果
		self.getParent().addChild(oneLayer); // 添加到场景中
	}
}




