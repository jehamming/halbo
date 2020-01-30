package com.hamming.halbo;

import com.hamming.halbo.datamodel.intern.World;

public class WorldServer implements Runnable {

    private boolean running = true;
    private World world;

    public WorldServer( World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }


    @Override
    public void run() {
        while (running) {

        }
    }

    public void stop() {
        running = false;
    }


}
