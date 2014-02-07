package no.predikament.entity;

import no.predikament.Art;
import no.predikament.Bitmap;
import no.predikament.Game;
import no.predikament.util.Vector2;

public class Character extends PhysicsEntity 
{
	private static final int HITBOX_WIDTH = 32;  //12;
	private static final int HITBOX_HEIGHT = 32; //22;
	private static final int HITBOX_OFFSET_X = 0;//10;
	private static final int HITBOX_OFFSET_Y = 0;//9;
	
	public Character(Game game)
	{
		super(game);
		
		hitbox.setSize(32, 32);
	}
	
	public Character(Game game, Vector2 position)
	{
		super(game, position, Vector2.zero(), new Vector2(HITBOX_WIDTH, HITBOX_HEIGHT));
	}
	
	public void render(Bitmap screen) 
	{
		super.render(screen); // Render the hitbox
		
		screen.draw(Art.instance.character[0][0], getPosition().getX(), getPosition().getY());
	}

	public void update(double delta) 
	{
		super.update(delta);

		// Translate hitbox by offset to match sprite bounds
		hitbox.translate(HITBOX_OFFSET_X, HITBOX_OFFSET_Y);
		
		double vel_x = (getVelocity().getX() * 0.99) * delta;
		double vel_y = (getVelocity().getY() * 0.99) * delta;
		
		setVelocity(new Vector2(vel_x, vel_y));	
	}
}