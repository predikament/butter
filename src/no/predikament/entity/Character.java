package no.predikament.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import no.predikament.Art;
import no.predikament.Bitmap;
import no.predikament.entity.tile.Tile;
import no.predikament.level.Level;
import no.predikament.util.Stopwatch;
import no.predikament.util.Vector2;

public class Character extends PhysicsEntity 
{
	private static final boolean DRAW_HITBOX = false;
	
	private static final int HITBOX_WIDTH = 16;
	private static final int HITBOX_HEIGHT = 24;
	private static final int HITBOX_OFFSET_X = 8;
	private static final int HITBOX_OFFSET_Y = 8;
	private static final int ACCELERATION_X = 25;
	private static final int MAX_SPEED_X = 100;
	private static final int MAX_SPEED_Y = 250;
	private static final int JUMP_TIME = 500;

	private Stopwatch jumpTimer;
	private boolean onGround;
	
	public Character(Level level)
	{
		this(level, Vector2.zero());
	}
	
	public Character(Level level, Vector2 position)
	{
		super(level, position, Vector2.zero(), new Vector2(HITBOX_WIDTH, HITBOX_HEIGHT));
		
		jumpTimer = new Stopwatch(true);
		setOnGround(false);
	}
	
	public void render(Bitmap screen) 
	{
		screen.draw(Art.instance.character[0][0], getPosition().getX(), getPosition().getY());
		
		if (DRAW_HITBOX) screen.drawRectangle(getHitbox(), 0xFF00FF00);
	}
	
	public void update(double delta)
	{
		super.update(delta);
		
		// Collision stuff		
		List<Tile> collidableTiles = new ArrayList<Tile>();
		
		for (int x = -1; x <= 1; ++x)
		{
			for (int y = -1; y <= 1; ++y)
			{
				int t_x = (int) ((getHitbox().getCenterX() / 16) + x);
				int t_y = (int) ((getHitbox().getCenterY() / 16) + y);
				
				Tile t = level.getTile(t_x, t_y);
				
				if (t != null && t.isSolid()) collidableTiles.add(t);
			}
		}
		
		Vector2 previous_position = getPosition();
		Iterator<Tile> tile_iterator = collidableTiles.iterator();
		int attempt = 0;
		
		while (tile_iterator.hasNext() && attempt < collidableTiles.size() * 2)
		{
			++attempt;
			
			Tile t = tile_iterator.next();
			
			if (getHitbox().intersects(t.getHitbox()))
			{
				Vector2 intersection_depth = Vector2.getIntersectionDepth(getHitbox(), t.getHitbox());
				
				if (intersection_depth != Vector2.zero())
				{
					// If we have a collision we reset to the first tile and re-check them all
					tile_iterator = collidableTiles.iterator();
					
					double absDepthX = Math.abs(intersection_depth.getX());
					double absDepthY = Math.abs(intersection_depth.getY());
					
					if (absDepthY < absDepthX)
					{
						setPosition(Vector2.add(getPosition(), new Vector2(0, intersection_depth.getY())));
					}
					else
					{
						setPosition(Vector2.add(getPosition(), new Vector2(intersection_depth.getX(), 0)));
					}
				}
			}
		}
		
		if (previous_position.getX() != getPosition().getX()) setVelocity(new Vector2(0, getVelocity().getY()));
		else if (previous_position.getY() != getPosition().getY()) setVelocity(new Vector2(getVelocity().getX(), 0));
	}
	
	public void setPosition(Vector2 position)
	{
		super.setPosition(position);
		
		// Translate hitbox by offset to match sprite bounds
		hitbox.translate(HITBOX_OFFSET_X, HITBOX_OFFSET_Y);
	}
	
	public void setVelocity(Vector2 velocity)
	{
		if (Math.abs(velocity.getX()) > MAX_SPEED_X) velocity = new Vector2(velocity.getX() > 0 ? MAX_SPEED_X : -MAX_SPEED_X, velocity.getY());
		if (Math.abs(velocity.getY()) > MAX_SPEED_Y) velocity = new Vector2(velocity.getX(), velocity.getY() > 0 ? MAX_SPEED_Y : -MAX_SPEED_Y);
		
		if (velocity.getY() == 0) setOnGround(true);
		else setOnGround(false);
		
		super.setVelocity(velocity);
	}
	
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
		if (isOnGround() && jumpTimer.getElapsedTime() >= JUMP_TIME)
		{
			jumpTimer.reset();
			
			setOnGround(false);
			
			setVelocity(Vector2.add(getVelocity(), new Vector2(0, -MAX_SPEED_Y)));
		}
	}
	
	public final boolean isOnGround()
	{
		return onGround;
	}
	
	private void setOnGround(boolean onGround) 
	{
		this.onGround = onGround;
	}
}