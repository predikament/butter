package no.predikament.entity;


import java.awt.Rectangle;
import java.util.ArrayList;

import no.predikament.Bitmap;
import no.predikament.level.Level;
import no.predikament.util.RectangleExtension;
import no.predikament.util.Vector2;

public class PhysicsEntity extends Entity 
{
	protected Level level;
	protected Rectangle hitbox;
	protected boolean solid;
	protected Vector2 velocity;
	private enum Direction
	{
		HORIZONTAL,
		VERTICAL,
		END
	};
	
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
		double add_x = velocity.getX() * delta;
		double add_y = velocity.getY() * delta;
		
		Rectangle new_hitbox = getHitbox();
		
		setPosition(Vector2.add(getPosition(), new Vector2(0, add_y)));
		new_hitbox.setLocation(getPosition().asPoint());
		setHitbox(new_hitbox);
		
		handleCollisions(Direction.VERTICAL);
		
		
		setPosition(Vector2.add(getPosition(), new Vector2(add_x, 0)));
		new_hitbox.setLocation(getPosition().asPoint());
		setHitbox(new_hitbox);
		
		handleCollisions(Direction.HORIZONTAL);
	}
	
	public void handleCollisions(Direction direction)
	{
		ArrayList<PhysicsEntity> tiles = new ArrayList<PhysicsEntity>();
		
		// Find colliding tiles
		for (int x = -1; x <= 1; ++x)
		{
			for (int y = -1; y <= 1; ++y)
			{
				PhysicsEntity p = level.getTile((int) (hitbox.getCenterX() / 16) + x, (int) (hitbox.getCenterY() / 16) + y);
				
				if (p != null && p != this && p.isSolid() && this.hitbox.intersects(p.hitbox))
				{
					tiles.add(p);
				}
			}
		}
		
		// if (tiles.size() > 0) System.out.println("Colliding tiles found: " + tiles.size());
		
		Vector2 new_position = getPosition();
		Vector2 new_velocity = getVelocity();
		Rectangle new_hitbox = getHitbox();
		
		// Handle collisions with collidable tiles for now
		for (PhysicsEntity p : tiles)
		{
			// Vertical collisions
			if (direction == Direction.VERTICAL)
			{
				float vertical_depth = RectangleExtension.getVerticalIntersectionDepth(new_hitbox, p.hitbox);
				
				if (vertical_depth != 0)
				{
					new_position = new Vector2(new_position.getX(), new_position.getY() + vertical_depth);
					new_velocity = new Vector2(new_velocity.getX(), 0);
					new_hitbox.setLocation(new_position.asPoint());
				}
			}
			else if (direction == Direction.HORIZONTAL)
			{
				// Horizontal collisions
				float horizontal_depth = RectangleExtension.getHorizontalIntersectionDepth(new_hitbox, p.hitbox);
				
				if (horizontal_depth != 0)
				{		
					new_position = new Vector2(new_position.getX() + horizontal_depth, new_position.getY());
					new_velocity = new Vector2(0, new_velocity.getY());
					new_hitbox.setLocation(new_position.asPoint());
				}
			}

			
			setPosition(new_position);
			setVelocity(new_velocity);
			setHitbox(new_hitbox);
		}
	}
	
	public void render(Bitmap screen)
	{
		
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