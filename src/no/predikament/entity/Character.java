package no.predikament.entity;

import no.predikament.Art;
import no.predikament.Bitmap;
import no.predikament.Game;
import no.predikament.util.Vector2;

public class Character extends PhysicsEntity 
{
	private static final boolean DRAW_HITBOX = false;
	
	private static final int HITBOX_WIDTH = 7;
	private static final int HITBOX_HEIGHT = 23;
	private static final int HITBOX_OFFSET_X = 13;
	private static final int HITBOX_OFFSET_Y = 10;
	private static final int ACCELERATION_X = 2;
	private static final int MAX_SPEED_X = 50;
	private static final int MAX_SPEED_Y = 100;
	private static final int JUMP_VECTOR = -300;

	private boolean onGround;
	
	public Character(Game game)
	{
		super(game);
		
		hitbox.setSize(32, 32);
		
		setOnGround(false);
	}
	
	public Character(Game game, Vector2 position)
	{
		super(game, position, Vector2.zero(), new Vector2(HITBOX_WIDTH, HITBOX_HEIGHT));
	}
	
	public void render(Bitmap screen) 
	{
		screen.draw(Art.instance.character[0][0], getPosition().getX(), getPosition().getY());
		
		if (DRAW_HITBOX) screen.drawRectangle(getHitbox(), 0xFF00FF00);
	}

	public void update(double delta) 
	{
		super.update(delta);

		// Translate hitbox by offset to match sprite bounds
		hitbox.translate(HITBOX_OFFSET_X, HITBOX_OFFSET_Y);
		
		// Cap velocity
		if (Math.abs(getVelocity().getX()) > MAX_SPEED_X) 
			setVelocity(new Vector2(getVelocity().getX() < 0 ? -MAX_SPEED_X : MAX_SPEED_X, getVelocity().getY()));
		
		if (Math.abs(getVelocity().getY()) > MAX_SPEED_Y) 
			setVelocity(new Vector2(getVelocity().getX(), getVelocity().getY() < 0 ? -MAX_SPEED_Y : MAX_SPEED_Y));
	}
	
	public void moveLeft()
	{
		setVelocity(Vector2.add(getVelocity(), new Vector2(-ACCELERATION_X, 0)));
	}
	
	public void moveRight()
	{
		setVelocity(Vector2.add(getVelocity(), new Vector2(ACCELERATION_X, 0)));
	}
	
	public void jump()
	{
		if (getVelocity().getY() == 0) setVelocity(Vector2.add(getVelocity(), new Vector2(0, JUMP_VECTOR)));
	}

	public final boolean isOnGround()
	{
		return onGround;
	}
	
	public void setOnGround(boolean onGround) 
	{
		this.onGround = onGround;
	}
}