package com.hamming.halbo.client.engine;

import com.hamming.halbo.client.controllers.MoveController;
import com.hamming.halbo.client.engine.actions.Action;
import com.hamming.halbo.client.engine.actions.SetCameraAction;
import com.hamming.halbo.client.engine.actions.SetUserLocationAction;
import com.hamming.halbo.client.engine.entities.Camera;
import com.hamming.halbo.client.engine.entities.Light;
import com.hamming.halbo.client.engine.entities.Player;
import com.hamming.halbo.client.engine.renderengine.DisplayManager;
import com.hamming.halbo.client.engine.renderengine.Loader;
import com.hamming.halbo.client.engine.renderengine.Renderer;
import com.hamming.halbo.client.engine.shaders.StaticShader;
import com.hamming.halbo.client.interfaces.Viewer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;


public class GLViewer implements Viewer, Runnable {
    private Camera camera;
    private Deque<Action> actions;
    private Renderer renderer;
    private Light light;
    private StaticShader shader;
    private Loader loader;
    private List<Player> players;
    private MoveController moveController;

    public GLViewer(MoveController moveController) {
        actions = new LinkedList<Action>();
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

        while (!Display.isCloseRequested()) {
            handleActions();
            renderEverything();
            handleMove();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

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
        if ( forward || back || left || right) {
            moveController.moveRequest(forward, back, left, right, 0f, 0f);
        }

    }

    private void renderEverything() {
        renderer.prepare();
        shader.start();
        shader.loadLight(light);
        shader.loadViewMatrix(camera);
        renderPlayers();
        shader.stop();
    }

    private void renderPlayers() {
        for (Player player: players) {
            renderer.render(player,shader);
        }
    }

    private void handleActions() {
        while (!actions.isEmpty()) {
            Action cmd = actions.removeFirst();
            cmd.execute();
        }
    }

    @Override
    public void setLocation(float x, float y, float z, float pitch, float yaw) {
        Action action = new SetCameraAction(this, x, y, z, pitch, yaw);
        synchronized (this) {
            actions.add(action);
        }
    }

    @Override
    public void setLocation(String userId, String name, float x, float y, float z, float pitch, float yaw) {
        Action action = new SetUserLocationAction(this, userId, name, x, y, z, pitch, yaw);
        synchronized (this) {
            actions.add(action);
        }
    }

    @Override
    public void setBaseplate(String name, int width, int length) {

    }


    @Override
    public void resetView() {

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
