package com.hamming.test.factories;

import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.model.User;
import com.hamming.halbo.model.World;
import com.hamming.halbo.factories.WorldFactory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestWorldFactory {

    @Test
    public void testStoreAndLoad(){
        deleteAllWorlds();
        User u1 = UserFactory.getInstance().addUser("Luuk Hamming", "lhhamming", "lhhamming", "luuk.hamming@gmail.com");
        User u2 = UserFactory.getInstance().addUser("Jan-Egbert Hamming", "jehamming", "jehamming", "janneman@hotmail.com");
        World w1 = WorldFactory.getInstance().createWorld(u1, "World1");
        World w2 = WorldFactory.getInstance().createWorld(u2, "World2");
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile("HALBO","TST");
            WorldFactory.getInstance().storeWorldsInFile(tmpFile);
            WorldFactory.getInstance().deleteWorld(w1);
            WorldFactory.getInstance().deleteWorld(w2);
            assert  WorldFactory.getInstance().getWorlds().size() == 0;
            WorldFactory.getInstance().loadWorldsFromFile(tmpFile);
            assert  WorldFactory.getInstance().getWorlds().size() == 2;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteAllWorlds() {
        WorldFactory.getInstance().getWorlds().removeAll(WorldFactory.getInstance().getWorlds());

    }
}
