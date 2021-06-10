package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public abstract class DrawableObject {

 public static ArrayList<DrawableObject> drawables;

 protected static ShapeRenderer shapeRenderer;

 abstract void draw();

 static {
  drawables = new ArrayList<>();
 }

 {
  drawables.add(this);
 }


}
