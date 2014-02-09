package no.predikament.entity.tile;

import no.predikament.Art;
import no.predikament.Bitmap;
import no.predikament.Game;
import no.predikament.entity.PhysicsEntity;

public class Tile extends PhysicsEntity 
{
	protected int type;
	
	public Tile(Game game, int type)
	{
		super(game);
		
		this.type = type;
		
		hitbox.setSize(16, 16);
	}
	
	public void render(Bitmap screen)
	{	
		int draw_type = getType() - 1;
		
		if (draw_type >= 0) 
		{
			screen.draw(Art.instance.tiles[draw_type % 16][draw_type / 16], getPosition().getX(), getPosition().getY());
		}
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
