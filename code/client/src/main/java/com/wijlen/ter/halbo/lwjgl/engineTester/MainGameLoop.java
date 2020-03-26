package com.wijlen.ter.halbo.lwjgl.engineTester;

import com.wijlen.ter.halbo.lwjgl.entities.Camera;
import com.wijlen.ter.halbo.lwjgl.entities.Light;
import com.wijlen.ter.halbo.lwjgl.entities.Player;
import com.wijlen.ter.halbo.lwjgl.guis.GuiRenderer;
import com.wijlen.ter.halbo.lwjgl.models.RawModel;
import com.wijlen.ter.halbo.lwjgl.models.TexturedModel;
import com.wijlen.ter.halbo.lwjgl.renderEngine.DisplayManager;
import com.wijlen.ter.halbo.lwjgl.renderEngine.Loader;
import com.wijlen.ter.halbo.lwjgl.renderEngine.MasterRenderer;
import com.wijlen.ter.halbo.lwjgl.renderEngine.OBJLoader;
import com.wijlen.ter.halbo.lwjgl.terrains.FlatTerrain;
import com.wijlen.ter.halbo.lwjgl.textures.ModelTexture;
import com.wijlen.ter.halbo.lwjgl.textures.TerrainTexture;
import com.wijlen.ter.halbo.lwjgl.textures.TerrainTexturePack;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class MainGameLoop {

    public static void main(String[] args) {
        DisplayManager.createDisplay("TTW Game!");
        Loader loader = new Loader();

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("baseplate"));

        FlatTerrain terrain = new FlatTerrain(48, 0, -1, loader, backgroundTexture);

        Light light = new Light(new Vector3f(0, 1000, -7000), new Vector3f(0.4f, 0.4f, 0.4f));
        List<Light> lights = new ArrayList<>();
        lights.add(light);
        lights.add(new Light(new Vector3f(185,10,-293), new Vector3f(2,0,0), new Vector3f(1, 0.01f,0.002f)));
        lights.add(new Light(new Vector3f(370,17,-300), new Vector3f(0,2,2), new Vector3f(1, 0.01f,0.002f)));
        lights.add(new Light(new Vector3f(293,7,-305), new Vector3f(2,2,0), new Vector3f(1, 0.01f,0.002f)));

        MasterRenderer renderer = new MasterRenderer(loader);

        RawModel dragonModel = OBJLoader.loadObjModel("person", loader);
        TexturedModel playerModel = new TexturedModel(dragonModel, new ModelTexture(loader.loadTexture("purple")));

        Player player = new Player("PLAYER", playerModel, new Vector3f(0, 1, -0), 0, 0, 0, 0.5f);

        Camera camera = new Camera();
        camera.setPlayer(player);

        GuiRenderer guiRenderer = new GuiRenderer(loader);

        while (!Display.isCloseRequested()) {
            camera.move();
            player.move(terrain);
            renderer.processEntity(player);
            renderer.processTerrain(terrain);
            renderer.render(lights, camera);
            DisplayManager.updateDisplay();
        }

        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }

}
