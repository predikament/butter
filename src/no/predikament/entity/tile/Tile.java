package no.predikament.entity.tile;

import no.predikament.entity.PhysicsEntity;
import no.predikament.level.Level;

public class Tile extends PhysicsEntity 
{
	protected int type;
	
	public Tile(Level level, int type)
	{
		super(level);
		
		this.type = type;
		
		hitbox.setSize(16, 16);
	}
		
	public void setType(int type)
	{
		this.type = type;
	}
	
	public final int getType()
	{
		return type;
	}
	
	public final boolean isSolid()
	{
		return getType() > 0;
	}
	
	public String toString()
	{
		return String.format("Tile (type: %d) (%s)", getType(), getPosition());
	}
}
