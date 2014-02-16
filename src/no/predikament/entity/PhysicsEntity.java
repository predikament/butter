package no.predikament.entity;


import java.awt.Rectangle;
import no.predikament.level.Level;
import no.predikament.util.Vector2;

public abstract class PhysicsEntity extends Entity 
{
	protected Level level;
	protected Rectangle hitbox;
	protected boolean solid;
	protected Vector2 velocity;
	
	
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
		super(position);
		
		this.level = level;
		
		solid = true;
		
		setHitbox(new Rectangle((int) size.getX(), (int) size.getY()));
		setVelocity(velocity);
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
		double new_x = getPosition().getX() + velocity.getX() * delta;
		double new_y = getPosition().getY() + velocity.getY() * delta;
		
		Vector2 new_position = new Vector2(new_x, new_y);
		Rectangle new_hitbox = getHitbox();
		new_hitbox.setLocation(new_position.asPoint());
		
		setPosition(new_position);
		setHitbox(new_hitbox);
	}
	
	public void setSolid(boolean solid)
	{
		this.solid = solid;
	}
	
	public final boolean isSolid()
	{
		return solid;
	}
}