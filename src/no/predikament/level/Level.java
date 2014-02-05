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
	@SuppressWarnings("unused")
	private final Game 		game;
	@SuppressWarnings("unused")
	private final Character character;
	@SuppressWarnings("unused")
	private final Camera 	camera;
	private List<Tile> 		tiles;
	private List<Entity> 	entities;
	
	@SuppressWarnings("unused")
	private int width_in_tiles;
	@SuppressWarnings("unused")
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
		else if (tiles.isEmpty() == false) tiles.clear();
		
		URL urlFile = Level.class.getResource("/maps/test.tmx");
		Document xmlDoc = null;
		
		if (urlFile != null)
		{
			File xmlFile = new File(urlFile.getFile());
		
			try
			{	
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				xmlDoc = dBuilder.parse(xmlFile);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		if (xmlDoc != null)
		{
			xmlDoc.getDocumentElement().normalize();
			
			Element layer_node = (Element) xmlDoc.getElementsByTagName("layer").item(0);
			String width_s = layer_node.getAttribute("width");
			String height_s = layer_node.getAttribute("height");
			
			try
			{
				width_in_tiles = Integer.parseInt(width_s);
				height_in_tiles = Integer.parseInt(height_s);
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
						double tile_pos_x = currentNode / width_in_tiles;
						double tile_pos_y = currentNode % height_in_tiles;
						
						tile_pos_x *= 16;
						tile_pos_y *= 16;
						
						tile.setPosition(new Vector2(tile_pos_x, tile_pos_y));
						
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
		/*Tile t = null;
		
		int tnr = TOTAL_TILES_HEIGHT * x + y;
		
		if (tnr >= 0 && tnr < tiles.size()) t = tiles.get(tnr);
		
		return t;*/
		return null; // FIX THIS ASAP
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