package no.predikament.level;

import java.util.ArrayList;
import java.util.List;

import no.predikament.Bitmap;
import no.predikament.Game;
import no.predikament.entity.Camera;
import no.predikament.entity.Character;
import no.predikament.entity.Entity;
import no.predikament.entity.tile.Tile;
import no.predikament.util.Vector2;

public class Level 
{
	@SuppressWarnings("unused")
	private final Game 		game;
	@SuppressWarnings("unused")
	private final Character character;
	@SuppressWarnings("unused")
	private final Camera 	camera;
	private List<Tile> 		tiles;
	private List<Entity> 	entities;
	
	private int width_in_tiles;
	private int height_in_tiles;
	
	public Level(Game game, Character character, Camera camera)
	{
		this.game = game;
		this.camera = camera;
		this.character = character;
		
		entities 		= new ArrayList<Entity>();
		tiles 			= new ArrayList<Tile>();
		width_in_tiles 	= 0;
		height_in_tiles = 0;
	}
	
	public void init()
	{
		tiles.clear();
		entities.clear();
				
		loadTMXFileIntoArrayList("/maps/test.tmx", tiles);
	}
	
	private void loadTMXFileIntoArrayList(String string, List<Tile> tiles) 
	{
		if (tiles == null) tiles = new ArrayList<Tile>();
		else tiles.clear();
		
		
	}

	public Tile getTile(int x, int y)
	{
		Tile t = null;
		
		int tnr = TOTAL_TILES_HEIGHT * x + y;
		
		if (tnr >= 0 && tnr < tiles.size()) t = tiles.get(tnr);
		
		return t;
	}
	
	public void render(Bitmap screen) 
	{
		for (Tile t : tiles)
		{
			t.render(screen);
		}
	}
	
	public void update(double delta) 
	{
		
	}
}