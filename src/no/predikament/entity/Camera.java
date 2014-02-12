package no.predikament.entity;

import no.predikament.level.Level;
import no.predikament.util.Vector2;

public class Camera extends PhysicsEntity 
{
	public Camera(Level level)
	{
		this(level, Vector2.zero(), Vector2.zero());
	}
	
	public Camera(Level level, Vector2 position)
	{
		this(level, position, Vector2.zero());
	}
	
	public Camera(Level level, Vector2 position, Vector2 size)
	{
		super(level, position, Vector2.zero(), size);
	}
}
