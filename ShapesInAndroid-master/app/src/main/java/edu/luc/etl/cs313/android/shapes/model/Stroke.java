package edu.luc.etl.cs313.android.shapes.model;

import android.graphics.Color;

/**
 * A decorator for specifying the stroke (foreground) color for drawing the
 * shape.
 */
public class Stroke implements Shape {

	// TODO entirely your job

	protected Shape shape;
	protected int color;

	public Stroke(final int color, final Shape shape) {
		this.shape = shape;
		this.color = color;
	}

	public int getColor() {
		return color;
	}

	public Shape getShape() {
		return shape;
	}

	@Override
	public <Result> Result accept(Visitor<Result> v) {

		return v.onStroke(this);
	}
}
