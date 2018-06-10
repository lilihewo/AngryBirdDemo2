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
 * ����������
 * @author ����ܵ�����
 *
 */
public class PhysicalEngine {
	
	private World world; // ��������

	private final float RATE = 32; // ����
	private float timeStep = 1.0f / 60.0f; //ʱ�䲽
	private int velocityIterations = 10;
	private int positionIterations = 8;
	private boolean isSimulate = true;


	/**
	 * ������������ĺ���
	 */
	public void createPhysicalWorld()
	{
		Vec2 gravity = new Vec2(0.0f, -9.0f); // ������������
//		world = new World(gravity, false); // ������������
//		world = new World(gravity, true); // ������������
		world = new World(gravity);
	}
	

	/**
	 * �����������д�������
	 */
	public Body createBodyInWorld(CCSprite mySprite) {
		// ����FixtureDef����
		FixtureDef fixtureDef = new FixtureDef(); 
		fixtureDef.friction = 0.3f; // ����FixtureDef��Ħ����
		fixtureDef.restitution = 0.1f; // ����FixtureDef�Ļظ���
		
		CircleShape circle = new CircleShape(); // ���ø������״ΪԲ��
		float circleR = mySprite.getBoundingBox().size.width / 2; // �õ������Բ�εİ뾶
		circle.m_radius = circleR / RATE; // ���ø���İ뾶
		// ---> ����FixtureDef����״
		fixtureDef.shape = circle;
		
		// ---> ���ø�����ܶ�
		fixtureDef.density = 5f;
		
		
		// ����BodyDef
		BodyDef bodyDef = new BodyDef();  
		
		// >>>�˴�һ��Ҫ���ã���ʹ�ܶȲ�Ϊ0������˴�����body.type����ΪBodyType.DYNAMIC������Ҳ�ᾲֹ
		bodyDef.type = BodyType.DYNAMIC;  

		// ����body��λ��
		float circleX = mySprite.getPosition().x;
		float circleY = mySprite.getPosition().y;
		bodyDef.position.set(circleX / RATE, circleY / RATE);
		
		// ����body
		Body body = world.createBody(bodyDef); 
		// Ϊ�˲��ó������
		if (body != null) {
			body.m_userData = mySprite; // ���m_userData
			body.createFixture(fixtureDef); // Ϊbody����Fixture
		}
		
		return body;
	}
	
	
	/**
	 * ����Բ�θ���ĺ���
	 * @param mySprite �ҵľ������
	 * @return Բ�θ���
	 */
	public Body createCircleBody(CCSprite mySprite) 
	{
		// ���ø������״ΪԲ��
		CircleShape circle = new CircleShape();
		// �õ������Բ�εİ뾶
		float circleR = mySprite.getBoundingBox().size.width / 2;
		// ���ø���İ뾶
		circle.m_radius = circleR / RATE;
		
		// --> ����FixtureDef
		FixtureDef fixtureDef = new FixtureDef(); 
		fixtureDef.density = 5f;
		fixtureDef.friction = 0.3f; // ����FixtureDef��Ħ����
		fixtureDef.restitution = 0.1f; // ����FixtureDef�Ļظ���
		fixtureDef.shape = circle; // ����FixtureDef����״

		// --> ����BodyDef
		BodyDef bodyDef = new BodyDef();  
		bodyDef.type = BodyType.DYNAMIC;
		
		// ����body��λ��
		float circleX = mySprite.getPosition().x;
		float circleY = mySprite.getPosition().y;
		bodyDef.position.set(circleX / RATE, circleY / RATE);

		// ����body
        Body body = world.createBody(bodyDef);  
		// Ϊ�˲��ó������
		if (body != null) {
			body.m_userData = mySprite; // ���m_userData
			body.createFixture(fixtureDef); // Ϊbody����Fixture
		}
  
		// ���ظ���
		return body;
	}
	

	/**
	 * ��������θ���ĺ���
	 * @param mySprite �ҵľ������
	 * @return ����θ���
	 */
	public Body createPolygonBody(CCSprite mySprite)  
    {  
		// ���ø������״Ϊ�����
        PolygonShape polygonShape = new PolygonShape(); 
        float mySpriteWidth = mySprite.getBoundingBox().size.width;
        float mySpriteHeight = mySprite.getBoundingBox().size.height;
        polygonShape.setAsBox(mySpriteWidth / 2 / RATE, mySpriteHeight / 2 / RATE);
          
        // --> ����FixtureDef
        FixtureDef fixtureDef = new FixtureDef(); 
        fixtureDef.density = 5f;
        fixtureDef.friction = 0.3f; // ����FixtureDef��Ħ����
        fixtureDef.restitution = 0.1f; // ����FixtureDef�Ļظ���
        fixtureDef.shape = polygonShape; // ���ϵ���״Ϊ�����
  
        // 
        BodyDef bodyDef = new BodyDef(); 
        bodyDef.type = BodyType.DYNAMIC;
        
        // ���ø����λ��
        float mySpriteX = mySprite.getPosition().x;
		float mySpriteY = mySprite.getPosition().y;
        bodyDef.position.set(mySpriteX / RATE, mySpriteY / RATE );
        
        // ��������
        Body body = world.createBody(bodyDef);  
		// Ϊ�˲��ó������
		if (body != null) {
			body.m_userData = mySprite; // ���m_userData
			body.createFixture(fixtureDef); // Ϊbody����Fixture
		} 
          
        // ���ظ������
        return body;  
    }   
	
	

	

	// ������Ļ�߿����ĺ���
	public void createScreenBody () {
		float width = CCDirector.sharedDirector().winSize().width;
		float height = CCDirector.sharedDirector().winSize().height;
		float friction = 2.0f;
		
		// ����������
		createGroundBody(width/2, height, width, 0.1f, friction);
		
		// �����ذ����
		createGroundBody(width/2, 0, width, 0.1f, friction);
	
		// ������ߵ������
		createGroundBody(0.0f, height/2, 0.1f, height, friction);
		
		// �����ұߵĵ������
		createGroundBody(width, height/2, 0.1f, height, friction);
	}

	
	
	/**
	 * �����ذ����ĺ���
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param friction �����Ħ����
	 */
	public void createGroundBody(float x, float y, float width,float height, float friction)  
    {  
		// �������״Ϊ�����
        PolygonShape polygonShape = new PolygonShape();  
        polygonShape.setAsBox(width / 2 / RATE, height / 2 / RATE);
        
        FixtureDef fixture = new FixtureDef(); 
        fixture.density = 0; // �ܶ�Ϊ0
        fixture.friction = friction; // Ħ����
        fixture.restitution = 0.0f; // �ظ���Ϊ0
        fixture.shape = polygonShape;
  
        // ����BodyDef����
        BodyDef bodyDef = new BodyDef(); 
        bodyDef.type = BodyType.STATIC;
        bodyDef.position.set(x / RATE, y / RATE ); // ����λ��
        
        // ����Body����
        Body body = world.createBody(bodyDef);  
		// Ϊ�˲��ó������
		if (body != null) {
			body.createFixture(fixture); // Ϊbody����Fixture
		} 
    }  

	
	// ��ʼ����
	public void startWorld() {
		new SimulateThread().start();
	}
	
	// ֹͣ����
	public void stopWorld() {
		isSimulate = false;
	}

	
	// ģ���߳���
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
			
			System.out.println("����������ֹͣ��");
		}
		
	}
	
	/**
	 * ģ����������ĺ���
	 */
	public void simulatePhysicalWorld()
	{
		// �����������ģ��
		world.step(timeStep, velocityIterations, positionIterations);
		setSpriteAttributeByBody();
	}
	

	// ͨ���������þ��������
	private void setSpriteAttributeByBody() {
		// ��������ĸ���
		for (Body body = world.getBodyList(); body != null; body = body.getNext())
		{
			// �õ��������
			CCSprite oneSprite = (CCSprite) body.getUserData();
			if (oneSprite != null) {
				// ����λ��
				oneSprite.setPosition(body.getPosition().x * RATE, body.getPosition().y * RATE);
				// ���ýǶ�
				double temp = Math.toDegrees(body.getAngle());
				oneSprite.setRotation((float) temp * -1);
			}
		}
	}
	
	
}
















