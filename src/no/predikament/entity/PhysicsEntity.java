package no.predikament.entity;

import java.awt.Rectangle;
import no.predikament.level.Level;
import no.predikament.util.Vector2;

public class PhysicsEntity extends Entity 
{
	protected Vector2 velocity;
	protected Rectangle hitbox;
	
	public PhysicsEntity(Level level) 
	{
		this(level, Vector2.zero());
	}

	public PhysicsEntity(Level level, Vector2 position) 
	{
		this(level, position, Vector2.zero());
	}
	
	public PhysicsEntity(Level level, Vector2 position, Vector2 velocity)
	{
		this(level, position, velocity, new Vector2(1, 1));
	}
	
	public PhysicsEntity(Level level, Vector2 position, Vector2 velocity, Vector2 size)
	{
		super(level, position);
		
		setVelocity(velocity);
		
		setHitbox(new Rectangle((int) position.getX(), (int) position.getY(), (int) size.getX(), (int) size.getY()));
	}
	
	public void setPosition(Vector2 position)
	{
		super.setPosition(position);
		
		getHitbox().setLocation((int) getPosition().getX(), (int) getPosition().getY());
	}
	
	public void setVelocity(Vector2 velocity)
	{
		this.velocity = velocity;
	}
	
	public final Vector2 getVelocity()
	{
		return velocity;
	}
	
	public void setHitbox(Rectangle hitbox) 
	{
		this.hitbox = hitbox;
	}
	
	public final Rectangle getHitbox() 
	{
		return hitbox;
	}
	
	public void update(double delta)
	{
		double pos_x = position.getX() + (velocity.getX() * delta);
		double pos_y = position.getY() + (velocity.getY() * delta);
		
		setPosition(new Vector2(pos_x, pos_y));
	}
}