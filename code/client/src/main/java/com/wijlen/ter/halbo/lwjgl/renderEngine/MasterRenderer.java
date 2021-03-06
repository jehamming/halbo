package com.wijlen.ter.halbo.lwjgl.renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hamming.halbo.ldraw.LDRenderedModel;
import com.wijlen.ter.halbo.lwjgl.models.TexturedModel;

import com.wijlen.ter.halbo.lwjgl.terrains.FlatTerrain;
import com.wijlen.ter.halbo.lwjgl.terrains.HeightTerrain;
import com.wijlen.ter.halbo.lwjgl.terrains.Terrain;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import com.wijlen.ter.halbo.lwjgl.shaders.StaticShader;
import com.wijlen.ter.halbo.lwjgl.shaders.TerrainShader;
import com.wijlen.ter.halbo.lwjgl.skybox.SkyboxRenderer;
import com.wijlen.ter.halbo.lwjgl.entities.Camera;
import com.wijlen.ter.halbo.lwjgl.entities.Entity;
import com.wijlen.ter.halbo.lwjgl.entities.Light;

public class MasterRenderer {
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;

	private static final float RED = 0.5f;
	private static final float GREEN = 0.5f;
	private static final float BLUE = 0.5f;
	
	private Matrix4f projectionMatrix;
	
	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;
	
	private FlatTerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();

	private ConstructionRenderer constructionRenderer;
	
	
	private Map<TexturedModel,List<Entity>> entities = new HashMap<TexturedModel,List<Entity>>();
	private List<FlatTerrain> terrains = new ArrayList<FlatTerrain>();


	private List<LDRenderedModel> constructions = new ArrayList<LDRenderedModel>();

	private SkyboxRenderer skyboxRenderer;

	public MasterRenderer(Loader loader){
		enableCulling();
		createProjectionMatrix();
		renderer = new EntityRenderer(shader,projectionMatrix);
		terrainRenderer = new FlatTerrainRenderer(terrainShader,projectionMatrix);
		constructionRenderer = new ConstructionRenderer(shader,projectionMatrix);
		skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
	}

	public static void enableCulling(){
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public static void disableCulling(){
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void render(List<Light> lights,Camera camera){
		prepare();
		shader.start();
		shader.loadSkyColour(RED, GREEN, BLUE);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		terrainShader.start();
		terrainShader.loadSkyColour(RED, GREEN, BLUE);
		terrainShader.loadLights(lights);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		constructionRenderer.render(constructions);
		terrainShader.stop();
		skyboxRenderer.render(camera);
		terrains.clear();
		entities.clear();
	}
	
	public void processTerrain(FlatTerrain terrain){
		terrains.add(terrain);
	}

	public void processConstruction(LDRenderedModel construction){
		constructions.add(construction);
	}


	public void processEntity(Entity entity){
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch!=null){
			batch.add(entity);
		}else{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);		
		}
	}
	
	public void cleanUp(){
		shader.cleanUp();
		terrainShader.cleanUp();
	}
	
	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(RED, GREEN, BLUE, 1);
	}
	
	private void createProjectionMatrix() {
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}


}
