package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

public class MyGdxGame3 extends Game
{

 PerspectiveCamera camera;
 SpriteBatch batch;
 Texture terrain;
 CameraInputController cameraInputController;

 @Override
 public void create()
 {
  camera = new PerspectiveCamera(67, 600, 600);
  camera.position.set(104.179726f, -45.423542f, 51.18856f);
  camera.lookAt(50, 50, 0);
  camera.near = 0.1f;
  camera.far = 500f;
  batch = new SpriteBatch();

  terrain = new Texture("terrain.png");

  cameraInputController = new CameraInputController(camera);

  Gdx.input.setInputProcessor(cameraInputController);
 }

 @Override
 public void render()
 {
  Gdx.gl20.glClearColor(Color.CYAN.r, Color.CYAN.g, Color.CYAN.b, 1);
  Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

  batch.setProjectionMatrix(camera.combined);

  batch.begin();
  batch.draw(terrain, 0, 0, 100, 100);
  batch.end();

  camera.update();
  cameraInputController.update();

 }

 @Override
 public void resize(int width, int height)
 {
  super.resize(width, height);
 }

}
