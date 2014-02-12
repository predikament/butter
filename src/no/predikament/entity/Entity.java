package no.predikament.entity;

import no.predikament.Bitmap;
import no.predikament.level.Level;
import no.predikament.util.Vector2;

public class Entity
{
	protected final Level level;
	protected Vector2 position;
	private boolean removed;
	
	public Entity(Level level)
	{
		this.level = level;
		
		removed = false;
		
		position = Vector2.zero();
	}
	
	public Entity(Level level, Vector2 position)
	{
		this(level);
		
		this.position = position;
	}
	
	public void render(Bitmap screen)
	{
		
	}
	
	public void update(double delta)
	{
		
	}
	
	public void remove() 
	{
		removed = true;
		
		System.out.println(this + " was removed.");
	}
	
	public boolean isRemoved() 
	{
		return removed;
	}
	
	public final Vector2 getPosition()
	{
		return position;
	}
	
	public void setPosition(Vector2 position)
	{
		this.position = position;
	}
}
