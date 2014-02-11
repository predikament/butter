package no.predikament.util;

import java.awt.Point;
import java.awt.Rectangle;

/*
 * Immutable class representing a 2D vector 
 */

public class Vector2 
{
	private final double x, y;
	
	public Vector2()
	{
		this(0, 0);
	}
	
	public Vector2(Vector2 u)
	{
		this(u.x, u.y);
	}
	
	public Vector2(Point point)
	{
		this(point.getX(), point.getY());
	}
	
	public Vector2(final double x, final double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public final double getX()
	{
		return x;
	}
	
	public final double getY()
	{
		return y;
	}
	
	public String toString()
	{
		return String.format("X = %f, Y = %f", x, y); 
	}
	
	public static Vector2 add(Vector2 u, Vector2 v)
	{
		return new Vector2(u.x + v.x, u.y + v.y);
	}
	
	public void add(final Vector2 u)
	{
		add(this, u);
	}
	
	public static Vector2 multiply(Vector2 u, double scalar)
	{
		return new Vector2(u.x * scalar, u.y * scalar);
	}
	
	public static double distanceBetween(final Vector2 u, final Vector2 v)
	{
		double x = u.getX() - v.getX();
		double y = u.getY() - v.getY();
		
		return Math.sqrt(x * x + y * y);
	}
	
	public double distanceTo(final Vector2 u)
	{
		return distanceBetween(this, u);
	}
	
	public static double angleBetween(final Vector2 u, final Vector2 v)
	{
		return Math.acos(u.normalized().dot(v.normalized()));
	}
	
	public double angleTo(final Vector2 u)
	{
		return angleBetween(this, u);
	}
	
	
	public static double cross(final Vector2 u, final Vector2 v)
	{
		return (u.x * v.y) - (u.y * v.x);
	}
	
	public double cross(final Vector2 u) 
	{
		return cross(this, u);
	}
	
	public static double dot(final Vector2 u, final Vector2 v)
	{
		return (u.x * v.x) + (u.y * v.y);
	}
	
	public double dot(final Vector2 u)
	{
		return dot(this, u);
	}
	
	public static double length(final Vector2 u)
	{
		return Math.sqrt(u.x * u.x + u.y * u.y);
	}
	
	public double length()
	{
		return length(this);
	}
	
	public static Vector2 normalized(final Vector2 u)
	{
		double length = length(u);
		
		if (length != 0) return new Vector2(u.x / length, u.y / length);
		
		return u;
	}
	
	public Vector2 normalized()
	{
		return normalized(this);
	}
	
	public static Vector2 zero()
	{
		return new Vector2(0, 0);
	}

	public static Vector2 radianToVector(double rad)
	{
		return new Vector2(Math.cos(rad), Math.sin(rad));
	}
	
	public static Vector2 isoTo2D(Vector2 v)
	{
		double x = (2 * v.getY() + v.getX()) / 2;
		double y = (2 * v.getY() - v.getX()) / 2;
		
		Vector2 result = new Vector2(x, y);
		
		return result;
	}
	
	public static Vector2 twoDToIso(Vector2 v)
	{
		double x = v.getX() - v.getY();
		double y = (v.getX() + v.getY()) / 2;
		
		Vector2 result = new Vector2(x, y);
		
		return result;
	}
	
	/// Gets intersection depth from two java.awt.Rectangles
	public static Vector2 getIntersectionDepth(Rectangle rectA, Rectangle rectB) 
	{
        // Calculate half sizes.
        float halfWidthA = (float) (rectA.getWidth() / 2.0f);
        float halfHeightA = (float) (rectA.getHeight() / 2.0f);
        float halfWidthB = (float) (rectB.getWidth() / 2.0f);
        float halfHeightB = (float) (rectB.getHeight() / 2.0f);

        // Calculate centers.
        Vector2 centerA = new Vector2(rectA.getMinX() + halfWidthA, rectA.getMinY() + halfHeightA);
        Vector2 centerB = new Vector2(rectB.getMinX() + halfWidthB, rectB.getMinY() + halfHeightB);

        // Calculate current and minimum-non-intersecting distances between centers.
        float distanceX = (float) (centerA.getX() - centerB.getX());
        float distanceY = (float) (centerA.getY() - centerB.getY());
        float minDistanceX = halfWidthA + halfWidthB;
        float minDistanceY = halfHeightA + halfHeightB;

        // If we are not intersecting at all, return (0, 0).
        if (Math.abs(distanceX) >= minDistanceX || Math.abs(distanceY) >= minDistanceY)
            return Vector2.zero();

        // Calculate and return intersection depths.
        float depthX = distanceX > 0 ? minDistanceX - distanceX : -minDistanceX - distanceX;
        float depthY = distanceY > 0 ? minDistanceY - distanceY : -minDistanceY - distanceY;
        return new Vector2(depthX, depthY);
    }
}
