package com.lili.engine;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * 物理引擎类
 * @author 冰淇淋的眼泪
 *
 */
public class PhysicalEngine {
	
	private World world; // 物理世界

	private final float RATE = 32; // 比率
	private float timeStep = 1.0f / 60.0f; //时间步
	private int velocityIterations = 10;
	private int positionIterations = 8;
	private boolean isSimulate = true;


	/**
	 * 创建物理世界的函数
	 */
	public void createPhysicalWorld()
	{
		Vec2 gravity = new Vec2(0.0f, -9.0f); // 设置重力向量
//		world = new World(gravity, false); // 创建物理世界
//		world = new World(gravity, true); // 创建物理世界
		world = new World(gravity);
	}
	

	/**
	 * 在物理世界中创建刚体
	 */
	public Body createBodyInWorld(CCSprite mySprite) {
		// 创建FixtureDef对象
		FixtureDef fixtureDef = new FixtureDef(); 
		fixtureDef.friction = 0.3f; // 设置FixtureDef的摩檫力
		fixtureDef.restitution = 0.1f; // 设置FixtureDef的回复力
		
		CircleShape circle = new CircleShape(); // 设置刚体的形状为圆形
		float circleR = mySprite.getBoundingBox().size.width / 2; // 得到精灵的圆形的半径
		circle.m_radius = circleR / RATE; // 设置刚体的半径
		// ---> 设置FixtureDef的型状
		fixtureDef.shape = circle;
		
		// ---> 设置刚体的密度
		fixtureDef.density = 5f;
		
		
		// 设置BodyDef
		BodyDef bodyDef = new BodyDef();  
		
		// >>>此处一定要设置，即使密度不为0，如果此处不将body.type设置为BodyType.DYNAMIC，物体也会静止
		bodyDef.type = BodyType.DYNAMIC;  

		// 设置body的位置
		float circleX = mySprite.getPosition().x;
		float circleY = mySprite.getPosition().y;
		bodyDef.position.set(circleX / RATE, circleY / RATE);
		
		// 创建body
		Body body = world.createBody(bodyDef); 
		// 为了不让程序崩溃
		if (body != null) {
			body.m_userData = mySprite; // 添加m_userData
			body.createFixture(fixtureDef); // 为body创建Fixture
		}
		
		return body;
	}
	
	
	/**
	 * 创建圆形刚体的函数
	 * @param mySprite 我的精灵对象
	 * @return 圆形刚体
	 */
	public Body createCircleBody(CCSprite mySprite) 
	{
		// 设置刚体的形状为圆形
		CircleShape circle = new CircleShape();
		// 得到精灵的圆形的半径
		float circleR = mySprite.getBoundingBox().size.width / 2;
		// 设置刚体的半径
		circle.m_radius = circleR / RATE;
		
		// --> 设置FixtureDef
		FixtureDef fixtureDef = new FixtureDef(); 
		fixtureDef.density = 5f;
		fixtureDef.friction = 0.3f; // 设置FixtureDef的摩檫力
		fixtureDef.restitution = 0.1f; // 设置FixtureDef的回复力
		fixtureDef.shape = circle; // 设置FixtureDef的型状

		// --> 设置BodyDef
		BodyDef bodyDef = new BodyDef();  
		bodyDef.type = BodyType.DYNAMIC;
		
		// 设置body的位置
		float circleX = mySprite.getPosition().x;
		float circleY = mySprite.getPosition().y;
		bodyDef.position.set(circleX / RATE, circleY / RATE);

		// 创建body
        Body body = world.createBody(bodyDef);  
		// 为了不让程序崩溃
		if (body != null) {
			body.m_userData = mySprite; // 添加m_userData
			body.createFixture(fixtureDef); // 为body创建Fixture
		}
  
		// 返回刚体
		return body;
	}
	

	/**
	 * 创建多边形刚体的函数
	 * @param mySprite 我的精灵对象
	 * @return 多边形刚体
	 */
	public Body createPolygonBody(CCSprite mySprite)  
    {  
		// 设置刚体的形状为多边形
        PolygonShape polygonShape = new PolygonShape(); 
        float mySpriteWidth = mySprite.getBoundingBox().size.width;
        float mySpriteHeight = mySprite.getBoundingBox().size.height;
        polygonShape.setAsBox(mySpriteWidth / 2 / RATE, mySpriteHeight / 2 / RATE);
          
        // --> 设置FixtureDef
        FixtureDef fixtureDef = new FixtureDef(); 
        fixtureDef.density = 5f;
        fixtureDef.friction = 0.3f; // 设置FixtureDef的摩檫力
        fixtureDef.restitution = 0.1f; // 设置FixtureDef的回复力
        fixtureDef.shape = polygonShape; // 材料的形状为多边形
  
        // 
        BodyDef bodyDef = new BodyDef(); 
        bodyDef.type = BodyType.DYNAMIC;
        
        // 设置刚体的位置
        float mySpriteX = mySprite.getPosition().x;
		float mySpriteY = mySprite.getPosition().y;
        bodyDef.position.set(mySpriteX / RATE, mySpriteY / RATE );
        
        // 创建刚体
        Body body = world.createBody(bodyDef);  
		// 为了不让程序崩溃
		if (body != null) {
			body.m_userData = mySprite; // 添加m_userData
			body.createFixture(fixtureDef); // 为body创建Fixture
		} 
          
        // 返回刚体对象
        return body;  
    }   
	
	

	

	// 创建屏幕边框刚体的函数
	public void createScreenBody () {
		float width = CCDirector.sharedDirector().winSize().width;
		float height = CCDirector.sharedDirector().winSize().height;
		float friction = 2.0f;
		
		// 创建天板刚体
		createGroundBody(width/2, height, width, 0.1f, friction);
		
		// 创建地板刚体
		createGroundBody(width/2, 0, width, 0.1f, friction);
	
		// 创建左边挡板刚体
		createGroundBody(0.0f, height/2, 0.1f, height, friction);
		
		// 创建右边的挡板对象
		createGroundBody(width, height/2, 0.1f, height, friction);
	}

	
	
	/**
	 * 创建地板刚体的函数
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param friction 刚体的摩檫力
	 */
	public void createGroundBody(float x, float y, float width,float height, float friction)  
    {  
		// 刚体的形状为多边形
        PolygonShape polygonShape = new PolygonShape();  
        polygonShape.setAsBox(width / 2 / RATE, height / 2 / RATE);
        
        FixtureDef fixture = new FixtureDef(); 
        fixture.density = 0; // 密度为0
        fixture.friction = friction; // 摩擦力
        fixture.restitution = 0.0f; // 回复力为0
        fixture.shape = polygonShape;
  
        // 创建BodyDef对象
        BodyDef bodyDef = new BodyDef(); 
        bodyDef.type = BodyType.STATIC;
        bodyDef.position.set(x / RATE, y / RATE ); // 设置位置
        
        // 创建Body对象
        Body body = world.createBody(bodyDef);  
		// 为了不让程序崩溃
		if (body != null) {
			body.createFixture(fixture); // 为body创建Fixture
		} 
    }  

	
	// 开始世界
	public void startWorld() {
		new SimulateThread().start();
	}
	
	// 停止世界
	public void stopWorld() {
		isSimulate = false;
	}

	
	// 模拟线程类
	class SimulateThread extends Thread {
		public void run() {
			while(isSimulate) {
				simulatePhysicalWorld();
				try 
				{
					Thread.sleep(20);
				} catch (Exception ex) {
					System.out.println(ex);
				}
				
				
			}
			
			System.out.println("物理引擎已停止。");
		}
		
	}
	
	/**
	 * 模拟物理世界的函数
	 */
	public void simulatePhysicalWorld()
	{
		// 物理世界进行模拟
		world.step(timeStep, velocityIterations, positionIterations);
		setSpriteAttributeByBody();
	}
	

	// 通过刚体设置精灵的属性
	private void setSpriteAttributeByBody() {
		// 遍历世界的刚体
		for (Body body = world.getBodyList(); body != null; body = body.getNext())
		{
			// 得到精灵对象
			CCSprite oneSprite = (CCSprite) body.getUserData();
			if (oneSprite != null) {
				// 设置位置
				oneSprite.setPosition(body.getPosition().x * RATE, body.getPosition().y * RATE);
				// 设置角度
				double temp = Math.toDegrees(body.getAngle());
				oneSprite.setRotation((float) temp * -1);
			}
		}
	}
	
	
}
















