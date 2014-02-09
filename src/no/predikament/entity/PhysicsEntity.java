package no.predikament.entity;

import java.awt.Rectangle;

import no.predikament.Game;
import no.predikament.util.Vector2;

public class PhysicsEntity extends Entity {

	protected Vector2 velocity;
	protected Rectangle hitbox;
	
	public PhysicsEntity(Game game) 
	{
		this(game, Vector2.zero());
	}

	public PhysicsEntity(Game game, Vector2 position) 
	{
		this(game, position, Vector2.zero());
	}
	
	public PhysicsEntity(Game game, Vector2 position, Vector2 velocity)
	{
		this(game, position, velocity, new Vector2(1, 1));
	}
	
	public PhysicsEntity(Game game, Vector2 position, Vector2 velocity, Vector2 size)
	{
		super(game, position);
		
		setVelocity(velocity);
		setHitbox(new Rectangle((int) position.getX(), (int) position.getY(), (int) size.getX(), (int) size.getY()));
	}
	
	public void setPosition(Vector2 position)
	{
		super.setPosition(position);
		
		getHitbox().setLocation((int) position.getX(), (int) position.getY());
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
		double posx = position.getX() + (velocity.getX() * delta);
		double posy = position.getY() + (velocity.getY() * delta);
		
		setPosition(new Vector2(posx, posy));
		
		hitbox.setLocation((int) getPosition().getX(), (int) getPosition().getY());
	}
}