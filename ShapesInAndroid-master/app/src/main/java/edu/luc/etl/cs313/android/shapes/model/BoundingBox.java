package edu.luc.etl.cs313.android.shapes.model;

import android.graphics.Color;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

	// TODO entirely your job (except onCircle)

	@Override
	public Location onCircle(final Circle c) {
		final int radius = c.getRadius();
		return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
	}

	@Override
	public Location onFill(final Fill f) {
		return f.getShape().accept(this);
	}

	@Override
	public Location onGroup(final Group g) {
		int minX = 0;
		int minY = 0;
		int maxX = 0;
		int maxY = 0;

		for (int i = 0; i < g.getShapes().size(); i++) {
			Shape shape = g.getShapes().get(i);
			Location loc = shape.accept(this);
			Rectangle r = (Rectangle) loc.getShape();
			if (i == 0) {
				minX = loc.getX();
				minY = loc.getY();
				maxX = loc.getX() + r.getWidth();
				maxY = loc.getY() + r.getHeight();
				continue;
			}
			minX = Math.min(minX, loc.getX());
			minY = Math.min(minY, loc.getY());
			maxX = Math.max(maxX, (loc.getX() + r.getWidth()));
			maxY = Math.max(maxY, (loc.getY() + r.getHeight()));
		}
		int boundingBoxWidth = maxX - minX;
		int boundingBoxHeight = maxY - minY;
		return new Location(minX, minY, new Rectangle(boundingBoxWidth, boundingBoxHeight));
	}

	@Override
	public Location onLocation(final Location l) {
		Location boundingBox = l.getShape().accept(this);
		return new Location(l.getX() + boundingBox.getX(), l.getY() + boundingBox.getY(),
				boundingBox.getShape());
	}

	@Override
	public Location onRectangle(final Rectangle r) {
		return new Location(0, 0, r);
	}

	@Override
	public Location onStroke(final Stroke c) {
		return c.getShape().accept(this);
	}

	@Override
	public Location onOutline(final Outline o) {
		return o.getShape().accept(this);
	}

	@Override
	public Location onPolygon(final Polygon s) {
		int minX = 0;
		int minY = 0;
		int maxX = 0;
		int maxY = 0;

		for (int i = 0; i < s.getPoints().size(); i++) {
			Point point = s.getPoints().get(i);
			if (i == 0) {
				minX = point.getX();
				minY = point.getY();
				maxX = point.getX();
				maxY = point.getY();
				continue;
			}
			minX = Math.min(minX, point.getX());
			minY = Math.min(minY, point.getY());
			maxX = Math.max(maxX, point.getX());
			maxY = Math.max(maxY, point.getY());
		}
		int boundingBoxWidth = maxX - minX;
		int boundingBoxHeight = maxY - minY;
		return new Location(minX, minY, new Rectangle(boundingBoxWidth, boundingBoxHeight));
	}
}
