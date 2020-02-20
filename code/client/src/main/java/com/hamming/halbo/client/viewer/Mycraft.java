// Copyright 2012 Mitchell Kember. Subject to the MIT License.

package com.hamming.halbo.client.viewer;

import org.lwjgl.LWJGLException;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Mycraft is an open source java game that uses the LightWeight Java
 * Game Library (LWJGL). It is inspired by the popular game Minecraft.
 * It was written as a final summative project for the ICS2O course.
 * 
 * "Minecraft" is an official trademark of Mojang AB. This work is not
 * formally related to, endorsed by or affiliated with Minecraft or Mojang AB.
 * 
 * @author Mitchell Kember
 * @version 1.0 06/12/2011
 * @since 06/12/2011
 */
final class Mycraft {
    
    /**
     * Used to log errors to a log file.
     */
    static final Logger LOGGER = Logger.getLogger(Mycraft.class.getName());
    
    static {
        try {
            LOGGER.addHandler(new FileHandler("errors.log", true));
        } catch (IOException ioe) {
            LOGGER.log(Level.WARNING, ioe.toString(), ioe);
        }
    }
    
    /**
     * The main method.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ViewController controller = null;
        
        // Try creating the ViewController
        // Any LWJGLExceptions that occur during the initialization of LWJGL
        // (Display, Keyboard, Mouse) will propagate up here and be caught.
            System.out.println("Mycraft is starting up.");
            controller = new ViewController();
            Thread t = new Thread(controller);
            t.run(); // begin the main loop
    }
}
