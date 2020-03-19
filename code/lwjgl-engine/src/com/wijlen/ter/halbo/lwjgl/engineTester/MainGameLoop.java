package com.wijlen.ter.halbo.lwjgl.engineTester;

import com.wijlen.ter.halbo.lwjgl.entities.Camera;
import com.wijlen.ter.halbo.lwjgl.entities.Entity;
import com.wijlen.ter.halbo.lwjgl.entities.Light;
import com.wijlen.ter.halbo.lwjgl.entities.Player;
import com.wijlen.ter.halbo.lwjgl.guis.GuiRenderer;
import com.wijlen.ter.halbo.lwjgl.guis.GuiTexture;
import com.wijlen.ter.halbo.lwjgl.models.RawModel;
import com.wijlen.ter.halbo.lwjgl.models.TexturedModel;
import com.wijlen.ter.halbo.lwjgl.objConverter.ModelData;
import com.wijlen.ter.halbo.lwjgl.objConverter.OBJFileLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import com.wijlen.ter.halbo.lwjgl.renderEngine.DisplayManager;
import com.wijlen.ter.halbo.lwjgl.renderEngine.Loader;
import com.wijlen.ter.halbo.lwjgl.renderEngine.MasterRenderer;
import com.wijlen.ter.halbo.lwjgl.renderEngine.OBJLoader;
import com.wijlen.ter.halbo.lwjgl.terrains.Terrain;
import com.wijlen.ter.halbo.lwjgl.textures.ModelTexture;
import com.wijlen.ter.halbo.lwjgl.textures.TerrainTexture;
import com.wijlen.ter.halbo.lwjgl.textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        // Terrain Texture
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        // Models
        ModelData treeModelData = OBJFileLoader.loadOBJ("tree");

        RawModel model = loader.loadToVAO(treeModelData.getVertices(), treeModelData.getTextureCoords(),
                treeModelData.getNormals(), treeModelData.getIndices());

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));

        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
                new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
        fernTextureAtlas.setNumberOfRows(2);
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader),
                fernTextureAtlas);
        fern.getTexture().setHasTransparency(true);

        ModelData lampModelData = OBJFileLoader.loadOBJ("lamp");
        RawModel lampModel = loader.loadToVAO(lampModelData.getVertices(),lampModelData.getTextureCoords(),
                lampModelData.getNormals(), lampModelData.getIndices());
        TexturedModel lamp = new TexturedModel(lampModel, new ModelTexture(loader.loadTexture("lamp")));

        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");

        List<Entity> entities = new ArrayList<>();
        Random random = new Random(676_452);
        for (int i = 0; i < 400; i++) {
            if (i % 2 == 0) {
                float x = random.nextFloat() * 800 - 400;
                float z = random.nextFloat() * -600;
                float y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z),
                        0, random.nextFloat() * 360, 0, 0.9f));
            }
            if (i % 5 == 0) {
                float x = random.nextFloat() * 800 - 400;
                float z = random.nextFloat() * -600;
                float y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(staticModel, new Vector3f(x, y, z),
                        0, 0, 0, random.nextFloat() * 1 + 4));
            }
            entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 800 - 400, 0,
                    random.nextFloat() * -600), 0, 0, 0, 1));

        }

        Light light = new Light(new Vector3f(0, 1000, -7000), new Vector3f(0.4f, 0.4f, 0.4f));
        List<Light> lights = new ArrayList<>();
        lights.add(light);
        lights.add(new Light(new Vector3f(185,10,-293), new Vector3f(2,0,0), new Vector3f(1, 0.01f,0.002f)));
        lights.add(new Light(new Vector3f(370,17,-300), new Vector3f(0,2,2), new Vector3f(1, 0.01f,0.002f)));
        lights.add(new Light(new Vector3f(293,7,-305), new Vector3f(2,2,0), new Vector3f(1, 0.01f,0.002f)));

        entities.add(new Entity(lamp, new Vector3f(185, -4.7f, -293),0,0,0,1));
        entities.add(new Entity(lamp, new Vector3f(370, 4.2f, -300),0,0,0,1));
        entities.add(new Entity(lamp, new Vector3f(293, -6.8f, -305),0,0,0,1));

        MasterRenderer renderer = new MasterRenderer(loader);

        RawModel dragonModel = OBJLoader.loadObjModel("dragon", loader);
        TexturedModel dragon = new TexturedModel(dragonModel, new ModelTexture(
                loader.loadTexture("purple")));

        Player player = new Player(dragon, new Vector3f(100, 0, -50), 0, 0, 0, 0.5f);

        Camera camera = new Camera(player);

        List<GuiTexture> guis = new ArrayList<>();
//        GuiTexture gui = new GuiTexture(loader.loadTexture("socuwan"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
//        com.wijlen.ter.halbo.lwjgl.guis.add(gui);

        GuiRenderer guiRenderer = new GuiRenderer(loader);

        while (!Display.isCloseRequested()) {
            camera.move();
            player.move(terrain);
            renderer.processEntity(player);
            renderer.processTerrain(terrain);
            for (Entity entity : entities) {
                renderer.processEntity(entity);
            }
            renderer.render(lights, camera);
            guiRenderer.render(guis);
            DisplayManager.updateDisplay();
        }

        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }

}
