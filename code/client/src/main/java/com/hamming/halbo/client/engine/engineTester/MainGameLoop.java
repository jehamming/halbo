package com.hamming.halbo.client.engine.engineTester;

import com.hamming.halbo.client.engine.entities.Camera;
import com.hamming.halbo.client.engine.entities.Entity;
import com.hamming.halbo.client.engine.entities.Light;
import com.hamming.halbo.client.engine.models.RawModel;
import com.hamming.halbo.client.engine.models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import com.hamming.halbo.client.engine.renderEngine.DisplayManager;
import com.hamming.halbo.client.engine.renderEngine.Loader;
import com.hamming.halbo.client.engine.renderEngine.OBJLoader;
import com.hamming.halbo.client.engine.renderEngine.Renderer;
import com.hamming.halbo.client.engine.shaders.StaticShader;
import com.hamming.halbo.client.engine.textures.ModelTexture;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        RawModel model = OBJLoader.loadObjModel("dragon", loader);

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("purple")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);

        Entity entity = new Entity(staticModel, new Vector3f(0, -5, -25), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(0, -5, -20), new Vector3f(1, 1, 1));

        Camera camera = new Camera();

        while (!Display.isCloseRequested()) {
            entity.increaseRotation(0, 1, 0);
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }

}
