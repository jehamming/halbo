package com.hamming.halbo;

import com.hamming.halbo.datamodel.intern.World;

public class WorldServer extends Server {

    private World world;

    public WorldServer( World world) {
        this.world = world;
    }


    public World getWorld() {
        return world;
    }
}
