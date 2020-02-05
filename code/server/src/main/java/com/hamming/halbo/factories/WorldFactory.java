package com.hamming.halbo.factories;

import com.hamming.halbo.IDManager;
import com.hamming.halbo.datamodel.intern.HalboID;
import com.hamming.halbo.datamodel.intern.World;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WorldFactory extends AbstractFactory {

    private List<World> worlds;

    private static WorldFactory instance;

    private WorldFactory() {
        initialize();
    };

    private void initialize() {
        worlds = new ArrayList<World>();
    }

    public static WorldFactory getInstance() {
        if ( instance == null ) {
            instance = new WorldFactory();
        }
        return instance;
    }


    public World addWorld(String creatorID, String ownerID, String name) {
        HalboID newID = IDManager.getInstance().getNextID(HalboID.Prefix.WLD);
        World w = new World(newID.toString());
        w.setCreatorID(creatorID);
        w.setName(name);
        w.setOwnerID(ownerID);
        worlds.add(w);
        return w;
    }

    public World deleteWorld(World w) {
        worlds.remove(w);
        return w;
    }

    public List<World> getWorlds() {
        return worlds;
    }

    public boolean loadWorldsFromFile(String filename) {
        File file = new File(filename);
        return loadWorldsFromFile(file);
    }

    public boolean loadWorldsFromFile(File file) {
        boolean retval = true;
        List<World> loadWorlds = (ArrayList<World>) loadFromFile(file);
        if ( loadWorlds != null ) {
            worlds = loadWorlds;
        }
        return retval;
    }

    public boolean storeWorldsInFile(String filename) {
        return storeInFile(worlds, filename);
    }

    public boolean storeWorldsInFile(File file) {
        return storeInFile(worlds, file);
    }


    public World getWorldByName(String worldName) {
        World foundWorld = null;
        for (World w : worlds){
            if(w.getName().equalsIgnoreCase(worldName)){
                //It has found the world
                foundWorld = w;
            }
        }
        return foundWorld;
    }

    public String getWorldsAsString() {
        StringBuilder sb = new StringBuilder();
        for (World w : worlds){
            sb.append(w + "\n");
        }
        return sb.toString();
    }
}
