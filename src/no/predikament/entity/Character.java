package no.predikament.entity;

import no.predikament.Art;
import no.predikament.Bitmap;
import no.predikament.level.Level;
import no.predikament.util.Vector2;

public class Character extends PhysicsEntity 
{
	private static final boolean DRAW_HITBOX = true;
	
	private static final int HITBOX_WIDTH = 32;
	private static final int HITBOX_HEIGHT = 32;
	private static final int ACCELERATION_X = 25;
	private static final int MAX_SPEED_X = 50;
	private static final int MAX_SPEED_Y = 50;

	
	public Character(Level level)
	{
		this(level, Vector2.zero());
	}
	
	public Character(Level level, Vector2 position)
	{
		super(level, position, Vector2.zero(), new Vector2(HITBOX_WIDTH, HITBOX_HEIGHT));
	}
	
	public void render(Bitmap screen) 
	{
		screen.draw(Art.instance.character[0][0], getPosition().getX(), getPosition().getY());
		
		if (DRAW_HITBOX) screen.drawRectangle(getHitbox(), 0xFF00FF00);
	}
	
	public void setVelocity(Vector2 velocity)
	{
		if (Math.abs(velocity.getX()) > MAX_SPEED_X) velocity = new Vector2(velocity.getX() > 0 ? MAX_SPEED_X : -MAX_SPEED_X, velocity.getY());
		if (Math.abs(velocity.getY()) > MAX_SPEED_Y) velocity = new Vector2(velocity.getX(), velocity.getY() > 0 ? MAX_SPEED_Y : -MAX_SPEED_Y);
		
		super.setVelocity(velocity);
	}
	
	/*public void setHitbox(Rectangle hitbox)
	{
		hitbox.translate(HITBOX_OFFSET_X, HITBOX_OFFSET_Y);
		
		super.setHitbox(hitbox);
	}*/
	
	public void moveLeft()
	{
		setVelocity(Vector2.add(getVelocity(), new Vector2(-ACCELERATION_X, 0)));
	}
	
	public void moveRight()
	{
		setVelocity(Vector2.add(getVelocity(), new Vector2(ACCELERATION_X, 0)));
	}
	
	public void jump()
	{
		
	}
}