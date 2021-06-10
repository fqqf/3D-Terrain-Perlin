package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

import static com.mygdx.game.Wall.walls;

public class MyGdxGame2 extends Game
{

 Ray ray;

 ShapeRenderer shapeRenderer;

 @Override
 public void create()
 {

  shapeRenderer = new ShapeRenderer();

  //new Wall(230, 150, 230, 80);
  //new Wall(250, 110, 30, 90);
  //new Wall(300, 300, 500, 300);

  for (int i = 0; i < 5; i++)
  {
   new Wall(0,0,0,0);
  }


  int mousePosX = Gdx.input.getX();
  int mousePosY = Gdx.graphics.getHeight() - Gdx.input.getY();

  for (int i = 1; i <= 60; i++)
  {

   ray = new Ray(300, 300, i * 5f, i * 5f);
   ray.lookAt(i * 2 * 3, i);


  }

  //ray = new Ray(300,300,0,0);
  //ray2 = new Ray(300,300,counter*30,counter);

  //rays.add(ray1);


  DrawableObject.shapeRenderer = shapeRenderer;

 }

 @Override
 public void dispose()
 {

 }

 @Override
 public void render()
 {

  if (Gdx.input.isKeyJustPressed(Input.Keys.R))
  {
   for (Wall wall: walls)
   {
    wall.x1 = (float)Math.random() * 2000;
    wall.y1 = (float)Math.random() * 1000;
    wall.x2 = (float)Math.random() * 2000;
    wall.y2 = (float)Math.random() * 1000;
   }

  }

  Gdx.gl20.glClearColor(0, 0, 0, 0);
  Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

  shapeRenderer.begin(ShapeRenderer.ShapeType.Line);


  shapeRenderer.setColor(Color.WHITE);

  for (Ray currentRay : Ray.rays)
  {

   currentRay.setPos(Gdx.input.getX(), Gdx.graphics.getHeight() - (Gdx.input.getY()));

   currentRay.checkCollision(walls);

   currentRay.draw();

  }

  for (Wall currentWall : walls)
  {
   currentWall.draw();
  }
  shapeRenderer.end();

 }


}

