package com.hamming.halbo.client.engine;

import com.hamming.halbo.client.controllers.MoveController;
import com.hamming.halbo.client.engine.actions.*;
import com.hamming.halbo.client.engine.entities.Camera;
import com.hamming.halbo.client.engine.entities.Entity;
import com.hamming.halbo.client.engine.entities.Light;
import com.hamming.halbo.client.engine.entities.Player;
import com.hamming.halbo.client.engine.models.RawModel;
import com.hamming.halbo.client.engine.models.TexturedModel;
import com.hamming.halbo.client.engine.renderengine.DisplayManager;
import com.hamming.halbo.client.engine.renderengine.Loader;
import com.hamming.halbo.client.engine.renderengine.OBJLoader;
import com.hamming.halbo.client.engine.renderengine.Renderer;
import com.hamming.halbo.client.engine.shaders.StaticShader;
import com.hamming.halbo.client.engine.textures.ModelTexture;
import com.hamming.halbo.client.interfaces.Viewer;
import javafx.scene.input.MouseButton;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.*;


public class GLViewer implements Viewer, Runnable {
    private Camera camera;
    private List<Action> actions;
    private Renderer renderer;
    private Light light;
    private StaticShader shader;
    private Loader loader;
    private List<Player> players;
    private MoveController moveController;
    private enum MouseButton {
        LEFT,
        RIGHT
    }

    public GLViewer(MoveController moveController) {
        actions = Collections.synchronizedList(new LinkedList<Action>());
        players = new ArrayList<Player>();
        this.moveController = moveController;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        DisplayManager.createDisplay("GLViewer");
        loader = new Loader();
        shader = new StaticShader();
        renderer = new Renderer(shader);
        light = new Light(new Vector3f(0, -5, -20), new Vector3f(1, 1, 1));
        camera = new Camera();

        // Dragon mag weg als players goed gerendered worden
        RawModel model = OBJLoader.loadObjModel("dragon", loader);
        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("purple")));
        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        Entity dragon = new Entity();
        dragon.setModel(staticModel);
        dragon.setPosition(new Vector3f(0, -5, -25));
        dragon.setScale(1);

        while (!Display.isCloseRequested()) {
            checkMouseGrab();
            handleActions();
            shader.start();
            renderEverything();
            renderer.render(dragon, shader);
            shader.stop();
            handleMove();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }

    private void checkMouseGrab() {
        // Check for Mouse
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) ) {
            Mouse.setGrabbed(false);
        }
        if (Mouse.isButtonDown(MouseButton.LEFT.ordinal())) {
            Mouse.setGrabbed(true);
        }
    }

    private void handleMove() {
        boolean forward = false, back = false, left = false, right = false;
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            forward = true;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            back = true;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            right = true;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            left = true;
        }
        if ( forward || back || left || right || Mouse.getDY() > 0 || Mouse.getDX() > 0) {
            moveController.moveRequest(forward, back, left, right, Mouse.getDY(), Mouse.getDX());
        }

    }

    private void renderEverything() {
        renderer.prepare();
        shader.loadLight(light);
        shader.loadViewMatrix(camera);
        renderPlayers();
    }

    private void renderPlayers() {
        for (Player player: players) {
            if (player.getPosition() != null ) {
                renderer.render(player, shader);
            }
        }
    }

    private void handleActions() {
        synchronized (actions) {
            Iterator<Action> iter = actions.iterator();
            while (iter.hasNext()) {
                Action cmd = iter.next();
                cmd.execute();
            }
        }
    }

    @Override
    public void setLocation(float x, float y, float z, float pitch, float yaw) {
        Action action = new SetCameraAction(this, x, y, z, pitch, yaw);
        synchronized (actions) {
            actions.add(action);
        }
    }

    @Override
    public void setLocation(String userId, String name, float x, float y, float z, float pitch, float yaw) {
        Action action = new SetUserLocationAction(this, userId, name, x, y, z, pitch, yaw);
        synchronized (actions) {
            actions.add(action);
        }
    }

    @Override
    public void setBaseplate(String name, int width, int length) {

    }


    @Override
    public void resetView() {

    }

    @Override
    public void addPlayer(String userId, String name) {
        Action action = new AddPlayerAction(this, userId, name);
        synchronized (actions) {
            actions.add(action);
        }
    }

    @Override
    public void removePlayer(String userId) {
        Action action = new RemovePlayerAction(this, userId);
        synchronized (actions) {
            actions.add(action);
        }
    }


    public Camera getCamera() {
        return camera;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Loader getLoader() {
        return loader;
    }
}
