package no.predikament.entity;

import no.predikament.Bitmap;
import no.predikament.Game;
import no.predikament.util.Vector2;

public class Entity
{
	protected final Game game;
	protected Vector2 position;
	private boolean removed;
	
	public Entity(Game game)
	{
		this.game = game;
		
		removed = false;
		
		position = Vector2.zero();
	}
	
	public Entity(Game game, Vector2 position)
	{
		this(game);
		
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
