package no.predikament.level;

import java.awt.Rectangle;
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
import no.predikament.entity.Entity;
import no.predikament.entity.PhysicsEntity;
import no.predikament.entity.tile.Tile;
import no.predikament.util.RectangleExtension;
import no.predikament.util.Vector2;

public class Level 
{
	@SuppressWarnings("unused")
	private final Game 		game;
	private List<Tile> 		tiles;
	private List<Entity> 	entities;
	private int width_in_tiles;
	private int height_in_tiles;
	private double gravity = (16 / 9.82) * 16 + 50;
	
	public Level(Game game)
	{
		this.game = game;
		
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
	
	public void addEntity(Entity e)
	{
		if (entities.contains(e) == false) entities.add(e);
	}
	
	public void removeEntity(Entity e)
	{
		if (entities.contains(e) == true) entities.remove(e);
	}

	public Tile getTile(int x, int y)
	{
		Tile t = null;
		
		if (x >= 0 && x < width_in_tiles && y >= 0 && y < height_in_tiles)
		{
			int tile_nr = width_in_tiles * y + x;
		
			t = tiles.get(tile_nr);
		}
		
		return t;
	}
	
	public void update(double delta) 
	{	
		Vector2 gravity_vec = new Vector2(0, gravity * delta);
		
		for (Tile t : tiles) t.update(delta);
		for (Entity e : entities)
		{
			PhysicsEntity pe = null;
			
			if (e instanceof PhysicsEntity) 
			{
				pe = (PhysicsEntity) e;
				
				pe.setVelocity(Vector2.add(pe.getVelocity(), gravity_vec));
			}
			
			e.update(delta);
			
			if (pe != null) handleCollisions(pe);
		}
	}
	
	public void handleCollisions(PhysicsEntity pe)
	{
		// Vertical collisions
		for (Tile t : tiles)
		{
			if (t.isSolid() && pe.getHitbox().intersects(t.getHitbox()))
			{
				float vertical_depth = RectangleExtension.getVerticalIntersectionDepth(pe.getHitbox(), t.getHitbox());
				
				if (vertical_depth != 0)
				{
					Vector2 depth = new Vector2(0, vertical_depth);
					
					pe.setPosition(Vector2.add(pe.getPosition(), depth));
					pe.setVelocity(new Vector2(pe.getVelocity().getX(), 0));
					Rectangle hb = pe.getHitbox();
					hb.translate((int) depth.getX(), (int) depth.getY());
					pe.setHitbox(hb);
				}
			}
		}
		
		// Horizontal collisions
		for (Tile t : tiles)
		{
			if (t.isSolid() && pe.getHitbox().intersects(t.getHitbox()))
			{
				float horizontal_depth = RectangleExtension.getHorizontalIntersectionDepth(pe.getHitbox(), t.getHitbox());
				
				if (horizontal_depth != 0)
				{
					Vector2 depth = new Vector2(horizontal_depth, 0);
					
					pe.setPosition(Vector2.add(pe.getPosition(), depth));
					pe.setVelocity(new Vector2(0, pe.getVelocity().getY()));
					Rectangle hb = pe.getHitbox();
					hb.translate((int) depth.getX(), (int) depth.getY());
					pe.setHitbox(hb);
				}
			}
		}
	}
	
	public final boolean isVisibleOnMap(PhysicsEntity e)
	{
		boolean visible = e.getHitbox().intersects(new Rectangle(	(int) -e.getHitbox().getWidth(), 
																	(int) -e.getHitbox().getHeight(), 
																	(int) (Game.WIDTH + e.getHitbox().getWidth()), 
																	(int) (Game.HEIGHT + e.getHitbox().getHeight())));
		
		return visible;
	}
	
	public void render(Bitmap screen) 
	{
		for (Tile t : tiles)
		{
			if (isVisibleOnMap(t)) t.render(screen);
		}
		
		for (Entity e : entities)
		{
			e.render(screen);
		}
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
			
			System.out.printf("Successfully loaded map from %s", map_path);
			
			Element layer_node = (Element) xmlDoc.getElementsByTagName("layer").item(0);
			String width_s = layer_node.getAttribute("width");
			String height_s = layer_node.getAttribute("height");
						
			try
			{
				width_in_tiles = Integer.parseInt(width_s);
				height_in_tiles = Integer.parseInt(height_s);
				
				System.out.printf(" (width: %d, height: %d)\n", width_in_tiles, height_in_tiles);
			}
			catch (NumberFormatException nfe)
			{
				System.out.println("Failed to parse map width/height.");
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
						
						tile = new Tile(this, tile_type);
					}
					catch (NumberFormatException nfe)
					{
						System.out.printf("Couldn't parse \"%s\" as integer, creating empty tile.\n", tile_type_s);
					}
					
					if (tile != null)
					{
						double tile_pos_x = currentNode % width_in_tiles;
						double tile_pos_y = currentNode / width_in_tiles;
						
						tile.setPosition(new Vector2(tile_pos_x * 16, tile_pos_y * 16));
						
						// System.out.println("New tile created: " + tile);
						tiles.add(tile);
					}
				}
			}
		}
	}
}