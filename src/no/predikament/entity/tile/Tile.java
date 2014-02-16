package no.predikament.entity.tile;

import no.predikament.Art;
import no.predikament.Bitmap;
import no.predikament.entity.PhysicsEntity;
import no.predikament.level.Level;

public class Tile extends PhysicsEntity 
{
	protected int type;
	
	public Tile(Level level, int type)
	{
		super(level);
		
		this.type = type;
		
		if (getType() == 0) setSolid(false);
		
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
	
	public String toString()
	{
		return String.format("Tile (type: %d) (%s)", getType(), getPosition());
	}
	
	public void render(Bitmap screen)
	{
		int draw_type = getType() - 1;
		
		if (draw_type >= 0) screen.draw(Art.instance.tiles[0][draw_type / 16], getPosition());
	}
}
