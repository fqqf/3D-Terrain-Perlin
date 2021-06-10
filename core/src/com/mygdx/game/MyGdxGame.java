package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import java.util.Arrays;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;

public class MyGdxGame extends ApplicationAdapter
{

 PerspectiveCamera camera;

 CameraInputController cameraInputController;

 ShapeRenderer shapeRenderer;

 Texture texture;

 int quantity = 100;
 float scl = 1f;
 float i;

 int stack = 0;

 float camPosY;
 float camPosX;

 boolean renderVar;

 float deviation = 5.5f;

 float blue_color;

 float perlin_noise_value = 10;

 int sq_counter = 0;
 float sq_z_value;
 int modelsCounter = 0;
 int globalModelsCounter = 0;
 ModelInstance[] modelsInstances;

 float array[][];

 float halfOfAPolygon = quantity * scl / 2;

 boolean done, done2;

 ModelBatch modelBatch;

 Model model, mine;

 ModelBuilder modelBuilder;

 ModelInstance modelInstance, mineInstance;

 float nearRandX = 0;
 float nearRandY = 0;

 float r = halfOfAPolygon;
 float fi = 4;
 float step = 0.05f;

 float perlin_x, perlin_y, perlin_z, gx, gy;

 float curX, curY, curP1, curP2, curP3, curP4;

 Environment environment;

 ColorAttribute colorAttribute;
 PointLight pointLight;

 float sunPosX = halfOfAPolygon;
 float sunPosY = halfOfAPolygon;

 Vector3 bomb_velocity = new Vector3(0, 0, 0);

 final Vector3 BOMB_START_VELOCITY = new Vector3(0, 1f * scl, 1.5f * scl);
 final Vector3 GRAVITY_FORCE = new Vector3(0, -0.005f * scl, -0.1f * scl);

 boolean calculatePhysics;

 Matrix4 matrix4;

 private void shoot()
 {
  bomb_velocity.add(BOMB_START_VELOCITY);
  calculatePhysics = true;
  mineInstance.transform.translate(camera.position.x, camera.position.y + 15 * scl, camera.position.z);
 }

 private void calculateBombPos()
 {
  bomb_velocity.add(GRAVITY_FORCE);
  mineInstance.transform.translate(bomb_velocity);
 }

 @Override
 public void create()
 {
  camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  camera.far = 200f * scl;
  camera.near = 0.1f;
  camera.position.set(50.0030327966f * scl, 50.3854037f * scl, 100.9227406f * scl);
  camera.lookAt(50.0030327966f * scl, 50.3854037f * scl, -0.9227406f * scl);

  texture = new Texture("123.png");

  cameraInputController = new CameraInputController(camera);
  cameraInputController.scrollFactor = -2;
  cameraInputController.forwardButton = 5;


  Gdx.input.setInputProcessor(cameraInputController);

  shapeRenderer = new ShapeRenderer();

  array = new float[quantity][quantity];
  modelsInstances = new ModelInstance[quantity * quantity];

  i = 2;

  modelBatch = new ModelBatch();

  modelBuilder = new ModelBuilder();

  model = modelBuilder.createRect(0, 0, 0, 1 * scl, 0, 0, 1 * scl, 1 * scl, 0, 0, 1 * scl, 0, 0, 0, 0, new Material(ColorAttribute.createDiffuse(Color.GREEN)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

  mine = modelBuilder.createSphere(0.5f * scl, 0.5f * scl, 0.5f * scl, 5, 5, new Material(ColorAttribute.createDiffuse(Color.DARK_GRAY)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

  //   model1 = modelBuilder.createRect(0,0,0,1,0,0,1,1,0,0,1,0,0,0,0,new Material(ColorAttribute.createDiffuse(Color.RED)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
  modelInstance = new ModelInstance(model);
  colorAttribute = new ColorAttribute(ColorAttribute.AmbientLight, 0.9f, 0.9f, 0.9f, 1f);
  pointLight = new PointLight().set(Color.WHITE, 50 * scl, 100 * scl, 50, 5000);



  environment = new Environment();
  environment.set(colorAttribute);
  environment.add(pointLight);

  mineInstance = new ModelInstance(mine);


  gx = (float) Math.random() * 1000;
  gy = (float) Math.random() * 1000;

  perlin_y = gy;

  while (!generateZaxis())  deviation++;

  //	modelInstance1 = new ModelInstance(model1);

  //	modelInstance.transform.setTranslation(0,0,0);
  //	modelInstance.transform.rotate(0,1,0,5);
 }

 @Override
 public void render()
 {

  // расчет перемещения объектов

  calculateSunPos();

  if (calculatePhysics)
  {
   calculateBombPos();
  }

  // управление

  if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
  {
   shoot();
  }
  if (Gdx.input.isKeyPressed(Input.Keys.UP))
  {
   pointLight.position.z += 1.5;
   renderVar = true;
  }
  if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
  {
   pointLight.position.z -= 1.5;
   renderVar = true;
  }
  if (Gdx.input.isKeyPressed(Input.Keys.Q))
  {
   pointLight.position.y += 1.5;
   renderVar = true;
  }
  if (Gdx.input.isKeyPressed(Input.Keys.E))
  {
   pointLight.position.y -= 1.5;
   renderVar = true;
  }

  if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
  {
   colorAttribute.color.r += 0.004f;
   colorAttribute.color.g += 0.004f;
   colorAttribute.color.b += 0.004f;
  }

  if (Gdx.input.isKeyPressed(Input.Keys.I))
  {
   pointLight.intensity += 3.5f;
  }
  if (Gdx.input.isKeyPressed(Input.Keys.K))
  {
   pointLight.intensity -= 3.5f;
  }

  if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
  {
   colorAttribute.color.r -= 0.004f;
   colorAttribute.color.g -= 0.004f;
   colorAttribute.color.b -= 0.004f;
  }

  camera.update();
  cameraInputController.update();

  // рендер

  Gdx.gl20.glClearColor(0, 0, 0, 0);
  Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

  draw();
 }


 private void calculateSunPos()
 {

  fi = fi + step;
  //System.out.println(fi);
  sunPosX = (float) (r * Math.cos(fi)) + halfOfAPolygon;
  sunPosY = (float) (r * Math.sin(fi)) + halfOfAPolygon;
  pointLight.position.x = sunPosX;
  pointLight.position.y = sunPosY;

 }

 private boolean generateZaxis()
 {

  for (int y = 0; y < quantity; y++)
  {


   perlin_y += 0.15f;
   perlin_x = gx;


   //   System.out.println((float)ImprovedNoise.noise(perlin_x,perlin_y,perlin_z));

   for (int x = 0; x < quantity; x++)
   {

    perlin_x += 0.12f;

    array[y][x] = deviation + (float) ImprovedNoise.noise(perlin_x, perlin_y, perlin_z) * perlin_noise_value * (sinDeg(y*2)*3+cosDeg(x*8));

    if (array[y][x] < 0) return false;
   }

  }

  return true;
 }

 private void draw()
 {

  shapeRenderer.setProjectionMatrix(camera.combined);

  shapeRenderer.setColor(1, 1, 1, 1);

  shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

  modelBatch.begin(camera);

  for (int y = 0; y < quantity; y++)
  {


   if (done)
   {
    perlin_y += 0.125f;
    perlin_x = 0;
   }

   //   System.out.println((float)ImprovedNoise.noise(perlin_x,perlin_y,perlin_z));

   for (int x = 0; x < quantity; x++)
   {

    if (modelsCounter < quantity * quantity)
    {

     try
     {
      nearRandX = array[y][x - 1];
     } catch (Exception e)
     {
     }

     try
     {
      nearRandY = array[y - 1][x - 1];
     } catch (Exception e)
     {
     }


     if (y != 0)
     {
      if (x != 0)
      {

       if (curP1 < 0.9f)
       {
        blue_color = 0.35f * curP1;
       } else blue_color = (0.5f / curP1) / 2;

       if (blue_color > 0.4)
        System.out.println("[y:" + y + " x:" + x + "] = " + curP1 + " " + blue_color);

       curX = x * scl;
       curY = y * scl;
       curP1 = array[y][x] * scl;
       curP2 = nearRandX * scl;
       curP3 = array[y - 1][x - 1] * scl;
       curP4 = array[y - 1][x] * scl;

       //if (x==42) blue_color=2;
       //if (y==52) blue_color=2;

       modelsInstances[modelsCounter] = new ModelInstance(
        modelBuilder.createRect(

         curX + scl, curY, curP1,
         curX, curY, curP2,
         curX, curY - scl, curP3,
         curX + scl, curY - scl, curP4,

         1, 1, 1,

         new Material(ColorAttribute.createDiffuse(new Color(/*(curP1 / perlin_noise_value + 0.1f) / 3.5f*/0, ((curP1 / perlin_noise_value + 0.1f) / 3.5f) / scl, blue_color / scl, 0))),
         VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));

        if (modelsCounter > 25) {
         matrix4 = modelsInstances[modelsCounter].transform;
         done2 = true;
       }


      } else
      {
       modelsInstances[modelsCounter] = new ModelInstance(modelBuilder.createRect(curX + scl, curY, curP1, curX, curY, curP2, curX, curY - scl, curP2, curX + scl, curY - scl, curP1, 0, 0, 0, new Material(ColorAttribute.createDiffuse(Color.BLACK)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
      }
     } else
     {
      modelsInstances[modelsCounter] = new ModelInstance(modelBuilder.createRect(curX + scl, curY, curP1, curX, curY, curP2, curX, curY - scl, curP2, curX + scl, curY - scl, curP1, 0, 0, 0, new Material(ColorAttribute.createDiffuse(Color.BLACK)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));

     }

     ++modelsCounter;


    }

    modelBatch.render(mineInstance, environment);

    //System.out.println(camera.position.x);
    //System.out.println(camera.position.y);

    modelInstance = modelsInstances[globalModelsCounter];

    if (!(globalModelsCounter == quantity * quantity - 1))
    {
     ++globalModelsCounter;
    } else
    {
     globalModelsCounter = 0;
    }

    //globalModelsCounter = 0;

    modelBatch.render(modelInstance, environment);

   }


  }

  if (!done)
  {
   done = true;
  }

  modelBatch.end();
  shapeRenderer.end();

 }

 private void generateBlocks()
 {


 }

 @Override
 public void dispose()
 {

  model.dispose();

 }
}


                      /*else {

                          System.out.println(array[y][x]/perlin_noise_value*-1);

                          modelsInstances[modelsCounter] = new ModelInstance(modelBuilder.createRect(curX + scl, curY, array[y][x], curX, curY, nearRandX, curX, curY - scl, array[y - 1][x - 1], curX + scl, curY - scl, array[y - 1][x], 0, 0, 0,
                                                           new Material(ColorAttribute.createDiffuse(new Color(0, 0.1f, /*(0.5f-(array[y][x]/perlin_noise_value*-1))+0.1f0, 0))), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
                      } */
//  modelBatch.render(modelInstance1);


//КАТЕТЫ (ГОРИЗОНТАЛЬ)
// shapeRenderer.line(x,y,nearRandX,x+1,y,array[y][x]);

//shapeRenderer.line(x,y-1,0,x+1,y-1,0);

//КАТЕТЫ (ВЕРТИКАЛЬ)
//  shapeRenderer.line(x,y,nearRandX,x,y-1,nearRandY);

//shapeRenderer.line(x+1,y,0,x+1,y-1,0);

//ГИПОТЕНУЗА


//shapeRenderer.line(x,y-1,nearRandY,x+1,y,array[y][x]);
