package com.mygdx.game;

import java.util.ArrayList;

public class Wall extends DrawableObject {

 public float x1,y1,x2,y2;

 public boolean isTouched;



 public static ArrayList<Wall> walls;

 static {
  walls = new ArrayList<>();
 }

 {

 }

 public Wall(float x1, float y1, float x2, float y2) {
  this.x1 = x1;
  this.y1 = y1;
  this.x2 = x2;
  this.y2 = y2;
  walls.add(this);
 }

 public void draw() { shapeRenderer.line(x1,y1,x2,y2); }

}
