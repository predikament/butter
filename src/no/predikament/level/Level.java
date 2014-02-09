package no.predikament.level;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import no.predikament.Bitmap;
import no.predikament.Game;
import no.predikament.entity.Camera;
import no.predikament.entity.Character;
import no.predikament.entity.Entity;
import no.predikament.entity.tile.Tile;
import no.predikament.util.Vector2;

public class Level 
{
	private final Game 		game;
	@SuppressWarnings("unused")
	private final Character character;
	@SuppressWarnings("unused")
	private final Camera 	camera;
	private List<Tile> 		tiles;
	private List<Entity> 	entities;
	
	private int width_in_tiles;
	private int height_in_tiles;
	private double gravity = -9.82;
	
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
	
	private Document getXMLDocument(String path)
	{
		Document document = null;
		URL urlFile = Level.class.getResource(path);
		
		if (urlFile != null)
		{
			File xmlFile = new File(urlFile.getFile());
		
			try
			{	
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				document = dBuilder.parse(xmlFile);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return document;
	}
	
	private void loadTMXFileIntoArrayList(String map_path, List<Tile> tiles) 
	{
		if (tiles == null) tiles = new ArrayList<Tile>();
		else if (tiles.isEmpty() == false) tiles.clear();
		
		Document xmlDoc = getXMLDocument(map_path);
		
		if (xmlDoc != null)
		{
			xmlDoc.getDocumentElement().normalize();
			
			System.out.printf("Successfully loaded map from \"%s\"", map_path);
			
			Element layer_node = (Element) xmlDoc.getElementsByTagName("layer").item(0);
			String width_s = layer_node.getAttribute("width");
			String height_s = layer_node.getAttribute("height");
			
			try
			{
				width_in_tiles = Integer.parseInt(width_s);
				height_in_tiles = Integer.parseInt(height_s);
				
				System.out.printf("(width: %d, height: %d)\n", width_in_tiles, height_in_tiles);
			}
			catch (NumberFormatException nfe)
			{
				System.out.println("Failed to parse map width and/or height.");
			}
			
			if (width_in_tiles > 0 && height_in_tiles > 0)
			{
				NodeList nodeList = xmlDoc.getElementsByTagName("tile");
				
				for (int currentNode = 0; currentNode < nodeList.getLength(); ++currentNode)
				{
					Node node = nodeList.item(currentNode);
					Element nodeElement = (Element) node;
					String tile_type_s = nodeElement.getAttribute("gid");
					int tile_type = 0;
					Tile tile = null;
					
					try
					{
						tile_type = Integer.parseInt(tile_type_s);
						
						tile = new Tile(game, tile_type);
					}
					catch (NumberFormatException nfe)
					{
						System.out.printf("Couldn't parse \"%s\" to integer. Creating empty tile.\n", tile_type_s);
					}
					
					if (tile != null)
					{
						double tile_pos_x = currentNode % width_in_tiles;
						double tile_pos_y = currentNode / width_in_tiles;
						
						tile.setPosition(new Vector2(tile_pos_x * 16, tile_pos_y * 16));
						
						// System.out.println("New tile created: " + tile);
						tiles.add(tile);
					}
					
					// System.out.println("Name: " + nodeElement.getNodeName() + "\"ID\" Value: " + nodeElement.getAttribute("gid"));
				}
			}
		}
	}

	public Tile getTile(int x, int y)
	{
		Tile t = null;
		int tile_nr = width_in_tiles * y + x;
		
		try
		{
			if (tiles.isEmpty() == false && tile_nr <= tiles.size()) return tiles.get(tile_nr);
		}
		catch (IndexOutOfBoundsException ioobe)
		{
			System.out.println("getTile() tried to go out of bounds - returning null.");
		}
		
		return t;
	}
	
	public void render(Bitmap screen) 
	{
		for (Tile t : tiles)
		{
			/*if (t.getPosition().getX() > 0 && 
				t.getPosition().getY() > 0 &&
				t.getPosition().getX() + t.getHitbox().getWidth() <= Game.WIDTH &&
				t.getPosition().getY() + t.getHitbox().getHeight() <= Game.HEIGHT)
			{
				t.render(screen);
			}*/
			t.render(screen);
		}
	}
	
	public void update(double delta) 
	{
		List<Tile> removable = new ArrayList<Tile>();
		
		for (Tile t : tiles)
		{
			if (t.isRemoved()) removable.add(t);
			else t.update(delta);
		}
		
		tiles.removeAll(removable);
	}
}