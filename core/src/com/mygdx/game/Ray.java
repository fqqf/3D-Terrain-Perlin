package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Ray extends DrawableObject {

 public float x1,y1,x2,y2;
 private float x3, y3, x4, y4;

 private float t, u;

 private float px, py;

 private boolean drawFull = true;

 private Vector2 normalizer;

 public static ArrayList<Ray> rays;

 private static boolean tick;

 private static int i;

 private static float deviation, min_deviation;

 private static Wall chosenWall;

 private static int rayLength;

 static {
  rays = new ArrayList<>();
  rayLength = Gdx.graphics.getHeight()*2 + 100;
 }

 {
  normalizer = new Vector2();
  rays.add(this);
 }

 public Ray(float x1, float y1, float dirX, float dirY) {

  this.x1 = x1;
  this.y1 = y1;

  lookAt(dirX, dirY);

 }

 public void draw() {

  if (drawFull) {
   shapeRenderer.line(x1, y1, x2, y2);
  } else {
   shapeRenderer.line(x1, y1, px, py);
  }
 }

 public void lookAt(float x, float y) {

  normalizer.x = x - x1;
  normalizer.y = y - y1;
  normalizer = normalizer.nor();

  updLook();

 }

 public void setPos(float x, float y) {

  x1 = x;
  y1 = y;

  updLook();

 }


 private void updLook() {

   x2 = rayLength * normalizer.x + x1;
   y2 = rayLength * normalizer.y + y1;

 }



 public void checkCollision(ArrayList<Wall> walls) {

  i = 0;
  min_deviation = 0;
  deviation = 0;
  chosenWall = null;
  tick = false;

  for (Wall currentWall : walls) {

   currentWall.isTouched = false;

   countTU(currentWall);

   if ((t >= 0) && (u >= 0) && (u <= 1)) {

    countPXPY(currentWall);
    ++i;
    currentWall.isTouched = true;
    drawFull = false;
    tick = true;

   } else if (!tick)  drawFull = true;

  }

  if (i>1) {
   for (Wall wall : walls) {
    if (wall.isTouched) {

     countTU(wall);
     countPXPY(wall);
     countDeviation(x1, y1, px, py);

     if (min_deviation == 0) {
      min_deviation = deviation;
      chosenWall = wall;
     } else if (min_deviation >= deviation) {
      min_deviation = deviation;
      chosenWall = wall;
     }

    }
   }

   countTU(chosenWall);
   countPXPY(chosenWall);

  }

  System.out.print("\rpx: "+px+" py: "+py+" df: "+drawFull);

 }

 private void countTU(Wall currentWall) {
  this.x3 = currentWall.x1;
  this.y3 = currentWall.y1;
  this.x4 = currentWall.x2;
  this.y4 = currentWall.y2;

  this.t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));

  this.u = -(((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4)));
 }

 private void countPXPY(Wall currentWall) {
  this.px = x1+t*(x2-x1);
  this.py = y1+t*(y2-y1);
 }

 public void countDeviation(float x,float y,float px, float py) {
  deviation = (float) Math.sqrt((x-px)*(x-px)+(y-py)*(y-py));
 }

}

