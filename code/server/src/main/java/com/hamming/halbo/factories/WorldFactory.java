package com.hamming.halbo.factories;

import com.hamming.halbo.IDManager;
import com.hamming.halbo.model.HalboID;
import com.hamming.halbo.model.User;
import com.hamming.halbo.model.World;

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


    public World createWorld(User creator, String name) {
        HalboID newID = IDManager.getInstance().getNextID(HalboID.Prefix.WLD);
        World w = new World(newID);
        w.setCreator(creator);
        w.setOwner(creator);
        w.setName(name);
        worlds.add(w);
        return w;
    }

    public World deleteWorld(World w) {
        worlds.remove(w);
        return w;
    }

    public World findWorldById( String strId ) {
        World retval = null;
        HalboID id = HalboID.valueOf(strId);
        for( World w : worlds) {
            if ( w.getId().equals(id)) {
                retval = w;
                break;
            }
        }
        return retval;
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
