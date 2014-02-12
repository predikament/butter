package no.predikament.entity;

import no.predikament.Bitmap;
import no.predikament.util.Vector2;

public abstract class Entity
{
	private boolean removed;	
	protected Vector2 position;
	
	public Entity()
	{
		this(Vector2.zero());
	}
	
	public Entity(Vector2 position)
	{	
		removed = false;
		
		setPosition(position);
	}
	
	public void remove() 
	{
		if (isRemoved() == false)
		{
			removed = true;
			
			System.out.println(this + " was removed.");
		}
	}
	
	public final boolean isRemoved() 
	{
		return removed;
	}
	
	public void setPosition(Vector2 position)
	{
		this.position = position;
	}
	
	public final Vector2 getPosition()
	{
		return position;
	}
	
	public abstract void render(Bitmap screen);
	public abstract void update(double delta);
}