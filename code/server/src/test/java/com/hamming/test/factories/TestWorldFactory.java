package com.hamming.test.factories;

import com.hamming.halbo.datamodel.intern.User;
import com.hamming.halbo.datamodel.intern.World;
import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.factories.WorldFactory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestWorldFactory {

    @Test
    public void testStoreAndLoad() {
        deleteAllWorlds();
        World w1 = WorldFactory.getInstance().addWorld("USR1", "USR1", "World1");
        World w2 = WorldFactory.getInstance().addWorld("USR2", "USR2", "World2");
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
