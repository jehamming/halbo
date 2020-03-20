package com.hamming.halbo.client.engine;

import com.hamming.halbo.client.controllers.MoveController;
import com.hamming.halbo.client.engine.actions.*;
import com.wijlen.ter.halbo.lwjgl.entities.Camera;
import com.wijlen.ter.halbo.lwjgl.entities.Light;
import com.wijlen.ter.halbo.lwjgl.entities.Player;
import com.wijlen.ter.halbo.lwjgl.models.RawModel;
import com.wijlen.ter.halbo.lwjgl.models.TexturedModel;
import com.wijlen.ter.halbo.lwjgl.renderEngine.DisplayManager;
import com.wijlen.ter.halbo.lwjgl.renderEngine.Loader;
import com.wijlen.ter.halbo.lwjgl.renderEngine.MasterRenderer;
import com.wijlen.ter.halbo.lwjgl.renderEngine.OBJLoader;
import com.wijlen.ter.halbo.lwjgl.terrains.FlatTerrain;
import com.wijlen.ter.halbo.lwjgl.terrains.Terrain;
import com.wijlen.ter.halbo.lwjgl.textures.ModelTexture;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class GLViewer implements Runnable {
    private Camera camera;
    private List<Action> actions;
    private List<Player> players;
    private MoveController moveController;
    private boolean initialized;
    private TexturedModel basicPlayerTexture;
    private String followedUserId;
    private FlatTerrain terrain;
    private Loader loader;
    private String currentBaseplateId;
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
        initialized = false;
        currentBaseplateId = null;
    }

    @Override
    public void run() {
        DisplayManager.createDisplay("GLViewer");
        loader = new Loader();


        MasterRenderer renderer = new MasterRenderer(loader);

        Light light = new Light(new Vector3f(0, 1000, -7000), new Vector3f(0.4f, 0.4f, 0.4f));
        List<Light> lights = new ArrayList<>();
        lights.add(light);
        lights.add(new Light(new Vector3f(185,10,-293), new Vector3f(2,0,0), new Vector3f(1, 0.01f,0.002f)));
        lights.add(new Light(new Vector3f(370,17,-300), new Vector3f(0,2,2), new Vector3f(1, 0.01f,0.002f)));
        lights.add(new Light(new Vector3f(293,7,-305), new Vector3f(2,2,0), new Vector3f(1, 0.01f,0.002f)));


        RawModel basicPlayerModel = OBJLoader.loadObjModel("person", loader);
        basicPlayerTexture = new TexturedModel(basicPlayerModel, new ModelTexture(
                loader.loadTexture("purple")));

        camera = new Camera();



        initialized = true;
        while (!Display.isCloseRequested()) {
            checkMouseGrab();
            handleActions();
            camera.move();
            players.forEach(player -> renderer.processEntity(player));
            if ( terrain != null ) {
                renderer.processTerrain(terrain);
            }
            renderer.render(lights, camera);
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
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

    private void handleActions() {
        synchronized (actions) {
            actions.forEach(action -> action.execute());
            actions.removeAll(actions);
        }
    }

    public void setLocation(String userId, float x, float y, float z, float pitch, float yaw) {
        Action action = new SetUserLocationAction(this, userId, x, y, z, pitch, yaw);
        synchronized (actions) {
            actions.add(action);
        }
    }

    public void followPlayer(String userId) {
        followedUserId = userId;
    }


    public void setBaseplate(String baseplateId, String name, int width, int length) {
        if (currentBaseplateId == null || currentBaseplateId.equals(baseplateId)) {
            Action action = new SetBaseplateAction(this, loader, baseplateId);
            synchronized (actions) {
                actions.add(action);
            }
        }
    }

    public String getCurrentBaseplateId() {
        return currentBaseplateId;
    }

    public void resetView() {
        terrain = null;
        currentBaseplateId = null;
        players = new ArrayList<Player>();
    }

    public void addPlayer(String userId, String name) {
        Action action = new AddPlayerAction(this, userId, name);
        synchronized (actions) {
            actions.add(action);
        }
    }

    public void removePlayer(String userId) {
        Action action = new RemovePlayerAction(this, userId);
        synchronized (actions) {
            actions.add(action);
        }
    }

    public boolean getForward() {
        return  initialized && Keyboard.isKeyDown(Keyboard.KEY_W);
    }

    public boolean getBack() {
        return initialized && Keyboard.isKeyDown(Keyboard.KEY_S);
    }

    public boolean getLeft() {
        return initialized && Keyboard.isKeyDown(Keyboard.KEY_A);
    }

    public boolean getRight() {
        return initialized && Keyboard.isKeyDown(Keyboard.KEY_D);
    }


    public List<Player> getPlayers() {
        return players;
    }

    public TexturedModel getBasicPlayerTexture() {
        return basicPlayerTexture;
    }

    public String getFollowedUserId() {
        return followedUserId;
    }

    public void followPlayer(Player p) {
        camera.setPlayer(p);
    }

    public void setTerrain(String baseplateId, FlatTerrain terrain) {
        this.terrain = terrain;
        this.currentBaseplateId = baseplateId;
    }



}
