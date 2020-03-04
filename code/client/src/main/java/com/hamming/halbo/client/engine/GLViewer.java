package com.hamming.halbo.client.engine;

import com.hamming.halbo.client.engine.entities.Camera;
import com.hamming.halbo.client.engine.entities.Entity;
import com.hamming.halbo.client.engine.entities.Light;
import com.hamming.halbo.client.engine.models.RawModel;
import com.hamming.halbo.client.engine.models.TexturedModel;
import com.hamming.halbo.client.interfaces.Viewer;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import com.hamming.halbo.client.engine.renderengine.DisplayManager;
import com.hamming.halbo.client.engine.renderengine.Loader;
import com.hamming.halbo.client.engine.renderengine.OBJLoader;
import com.hamming.halbo.client.engine.renderengine.Renderer;
import com.hamming.halbo.client.engine.shaders.StaticShader;
import com.hamming.halbo.client.engine.textures.ModelTexture;

public class GLViewer implements Viewer, Runnable {

    public GLViewer() {
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        DisplayManager.createDisplay("GLViewer");
        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

    //    RawModel model = OBJLoader.loadObjModel("dragon", loader);
    //    TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("purple")));
	//	ModelTexture texture = staticModel.getTexture();
	//	texture.setShineDamper(10);
	//	texture.setReflectivity(1);

    //    Entity entity = new Entity(staticModel, new Vector3f(0, -5, -25), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(0, -5, -20), new Vector3f(1, 1, 1));

        Camera camera = new Camera();

        while (!Display.isCloseRequested()) {
      //      entity.increaseRotation(0, 1, 0);
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(camera);
     //       renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }

    @Override
    public void setLocation(double x, double y, double z, double viewDirection) {

    }

    @Override
    public void setLocation(String userId, String name, double x, double y, double z, double viewDirection) {

    }

    @Override
    public void setBaseplate(String name, int width, int length) {

    }

    @Override
    public void resetView() {

    }


}
