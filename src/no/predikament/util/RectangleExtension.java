package no.predikament.util;

import java.awt.Rectangle;

public class RectangleExtension 
{
	public static float getHorizontalIntersectionDepth(Rectangle rect_a, Rectangle rect_b)
	{
		// Calculate half sizes
		float halfWidthA = (float) (rect_a.getWidth() / 2.0f);
		float halfWidthB = (float) (rect_b.getWidth() / 2.0f);
		
		// Calculate centers
		float centerA = (float) (rect_a.getMinX() + halfWidthA);
		float centerB = (float) (rect_b.getMinX() + halfWidthB);
		
		// Calculate current and minimum non-intersecting distances between centers
		float distanceX = centerA - centerB;
		float minDistanceX = halfWidthA + halfWidthB;
		
		// If we are not intersecting at all, return 0
		if (Math.abs(distanceX) >= minDistanceX) return 0f;
		
		// Calculate and return intersection depth
		return distanceX > 0 ? minDistanceX - distanceX : -minDistanceX - distanceX;
	}
	
	public static float getVerticalIntersectionDepth(Rectangle rect_a, Rectangle rect_b)
	{
		// Calculate half sizes
		float halfHeightA = (float) (rect_a.getHeight() / 2.0f);
		float halfHeightB = (float) (rect_b.getHeight() / 2.0f);
		
		// Calculate centers
		float centerA = (float) (rect_a.getMinY() + halfHeightA);
		float centerB = (float) (rect_b.getMinY() + halfHeightB);
		
		// Calculate current and minimum non-intersecting distances between centers
		float distanceY = centerA - centerB;
		float minDistanceY = halfHeightA + halfHeightB;
		
		// If we are not intersecting at all, return 0
		if (Math.abs(distanceY) >= minDistanceY) return 0f;
		
		// Calculate and return intersection depth
		return distanceY > 0 ? minDistanceY - distanceY : -minDistanceY - distanceY;
	}
	
	/// Gets intersection depth as Vector2 from two java.awt.Rectangles
	public static Vector2 getIntersectionDepth(Rectangle rect_a, Rectangle rect_b) 
	{
        // Calculate half sizes.
        float halfWidthA = (float) (rect_a.getWidth() / 2.0f);
        float halfHeightA = (float) (rect_a.getHeight() / 2.0f);
        float halfWidthB = (float) (rect_b.getWidth() / 2.0f);
        float halfHeightB = (float) (rect_b.getHeight() / 2.0f);

        // Calculate centers.
        Vector2 centerA = new Vector2(rect_a.getMinX() + halfWidthA, rect_a.getMinY() + halfHeightA);
        Vector2 centerB = new Vector2(rect_b.getMinX() + halfWidthB, rect_b.getMinY() + halfHeightB);

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
