// Copyright 2012 Mitchell Kember. Subject to the MIT License.

package com.hamming.halbo.client.viewer;

import com.hamming.halbo.client.HALBOTestToollWindow;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.BaseplateDto;
import com.hamming.halbo.model.dto.UserLocationDto;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import static org.lwjgl.input.Keyboard.KEY_ESCAPE;
import static org.lwjgl.input.Keyboard.isKeyDown;

/**
 * A tiny enum for identifying mouse buttons.
 *
 * @author Mitchell Kember
 * @since 10/12/2011
 */
enum MouseButton {
    LEFT,
    RIGHT
}

/**
 * ViewController is the controller in the Model-View-Controller (MVC) design
 * architecture for this application. ViewController handles user input and mediates
 * between the ViewState and ViewRenderer classes. It also manages the run loop
 * of Mycraft.
 *
 * @author Mitchell Kember
 * @see ViewState
 * @see ViewRenderer
 * @since 07/12/2011
 */
public final class ViewController implements Runnable {

    /**
     * The maximum amount of time to simulate over a single frame, in milliseconds.
     */
    private static final float MAX_DELTA_TIME = 50.f;

    /**
     * The renderer for this ViewController's state.
     */
    private ViewRenderer renderer;

    /**
     * The heart of the game, the ViewState.
     */
    private ViewState state;

    /**
     * Used for detecting space bar presses.
     */
    boolean wasSpaceBarDown = false;

    /**
     * Used for detecting left mouse clicks.
     */
    private boolean wasLeftMouseButtonDown = false;

    /**
     * Used for detecting right mouse clicks.
     */
    private boolean wasRightMouseButtonDown = false;

    /**
     * Used for calculating delta time between frames.
     */
    private double previousTime = 0.0;

    private boolean running = false;

    private HALBOTestToollWindow client;

    private UserLocationDto location;

    private ProtocolHandler protocolHandler;

    public ViewController( HALBOTestToollWindow client) {
        this.client = client;
        protocolHandler = new ProtocolHandler();
    }

    /**
     * Initializes the ViewController, which manages its own ViewState and
     * ViewRenderer, as well as user input (LWJGL Keyboard and Mouse).
     *
     * @throws LWJGLException if there was an error loading any part of LWJGL
     */
    private void init() throws LWJGLException {
        location = null;
        Keyboard.create();
        // This will make the mouse invisible. It will be "grabbed" by the
        // window, making it invisible and unable to leave the window.
        //TODO - Enable? Mouse.setGrabbed(true);
        Mouse.create();
    }

    /**
     * Clean up LWJGL components.
     */
    void destroy() {
        // Methods already check if created before destroying.
        Mouse.destroy();
        Keyboard.destroy();
        Display.destroy();
        running = false;
        location = null;
    }

    /**
     * Gets the time in milliseconds since this method was last called. If it
     * is greater than MAX_DELTA_TIME, that will be returned instead.
     *
     * @return the time since this method was last called in milliseconds
     * @see #MAX_DELTA_TIME
     */
    private float getDeltaTime() {
        // Get hires time in milliseconds
        double newTime = (Sys.getTime() * 1000.0) / Sys.getTimerResolution();
        // Calculate the delta
        float delta = (float) (newTime - previousTime);
        // New becomes old for next call
        previousTime = newTime;

        // Return the delta time unless it's bigger than MAX_DELTA_TIME
        return (delta < MAX_DELTA_TIME) ? delta : MAX_DELTA_TIME;
    }

    /**
     * Determines whether the mouse {@code button} has been clicked or not.
     *
     * @param button which mouse button to check
     * @return true if it is down and it wasn't last time this method was called
     */
    private boolean wasMouseClicked(MouseButton button) {
        boolean buttonDown = Mouse.isButtonDown(button.ordinal());
        boolean clicked = false;

        // Determine if the mouse button wasn't down before but is now
        if (button == MouseButton.LEFT) {
            clicked = !wasLeftMouseButtonDown && buttonDown;
            wasLeftMouseButtonDown = buttonDown;
        } else if (button == MouseButton.RIGHT) {
            clicked = !wasRightMouseButtonDown && buttonDown;
            wasRightMouseButtonDown = buttonDown;
        }

        return clicked;
    }

    /**
     * Determines whether the space bar key was pressed.
     *
     * @return true if the space bar was not pressed last time this was called but is now
     */
    private boolean wasSpaceBarPressed() {
        boolean spaceBarDown = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
        boolean wasPressed = !wasSpaceBarDown && spaceBarDown;
        wasSpaceBarDown = spaceBarDown;

        return wasPressed;
    }

    /**
     * The run loop. The application will stay inside this method until the window
     * is closed or the Escape key is pressed.
     */
    @Override
    public void run() {
        try {
            renderer = new ViewRenderer();
            state = new ViewState(renderer);

            //TODO Remove
            // state.setChunk(new Chunk(100,100));


            init();
            running = true;
        } catch (LWJGLException e) {
            e.printStackTrace();
            running = false;
        }
        while (running) {
            if (!Display.isCloseRequested()) {
                if (Display.isVisible()) {
                    // Check movement
                    doMove(
                            Keyboard.isKeyDown(Keyboard.KEY_W),
                            Keyboard.isKeyDown(Keyboard.KEY_S),
                            Keyboard.isKeyDown(Keyboard.KEY_A),
                            Keyboard.isKeyDown(Keyboard.KEY_D)
                    );
                    // Update the state with the required input
                    state.update(new ViewStateInputData(
                                    location,
                                    wasSpaceBarPressed(),
                                    Mouse.getDX(), Mouse.getDY(), Mouse.getDWheel() / -120,
                                    wasMouseClicked(MouseButton.LEFT),
                                    wasMouseClicked(MouseButton.RIGHT)),
                            // Using the delta time for a framerate-independent simulation
                            // should be the correct way to do things but it produces strange
                            // results on the school computers, so instead simulate one
                            // sixtieth of a second every frame.
                            /*getDeltaTime()*/ 1000.f / 60.f);

                    // Check MouseDown
                    if (Mouse.isButtonDown(MouseButton.LEFT.ordinal())) {
                        showMouse(false);
                    }

                    // Check if Escape is pressed
                    if (isKeyDown(KEY_ESCAPE)) {
                        showMouse(true);
                    }

                    // Render it
                    renderer.render(state);
                } else {
                    // Only render if it needs rendering
                    if (Display.isDirty()) {
                        renderer.render(state);
                    }
                    try {
                        // If the window isn't visible, sleep a bit so that we're
                        // not wasting resources by checking nonstop.
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    public void doMove(boolean forward, boolean back, boolean left, boolean right) {
        if ( client.isConnected() && client.getUserLocation() != null ) {
            if ( forward || back || left || right ) {
                String cmd = protocolHandler.getMoveCommand(forward, back, left, right);
                client.send(cmd);
            }
        }
    }

    private void showMouse(boolean b) {
        //TODO Enable?
        if (b) {
            Mouse.setGrabbed(false);
        } else {
            Mouse.setGrabbed(true);
        }
    }

    public void setLocation(UserLocationDto loc) {
        this.location = loc;
    }

    public UserLocationDto getLocation() {
        return location;
    }

    public void teleportTo(BaseplateDto baseplate, UserLocationDto location) {
        state.setChunk( new Chunk(baseplate.getWidth(), baseplate.getLength()));
        setLocation(location);
    }
}
