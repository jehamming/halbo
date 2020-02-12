package com.hamming.halbo.factories;

import com.hamming.halbo.IDManager;
import com.hamming.halbo.model.Baseplate;
import com.hamming.halbo.model.City;
import com.hamming.halbo.model.HalboID;
import com.hamming.halbo.model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BaseplateFactory extends AbstractFactory {
    private static BaseplateFactory instance;

    private BaseplateFactory() {
        initialize();
    };
    private List<Baseplate> baseplateList;

    private void initialize() {
        baseplateList = new ArrayList<Baseplate>();
    }

    public static BaseplateFactory getInstance() {
        if ( instance == null ) {
            instance = new BaseplateFactory();
        }
        return instance;
    }

    public List<Baseplate> getBaseplateList() {
        return baseplateList;
    }

    public Baseplate findBaseplateByID(String strId) {
        Baseplate found = null;
        HalboID id = HalboID.valueOf(strId);
        for (Baseplate b: baseplateList) {
            if ( b.getId().equals(id) ) {
                found = b;
                break;
            }
        }
        return found;
    }

    public Baseplate createBaseplate(String name, User creator) {
        HalboID id = IDManager.getInstance().getNextID(HalboID.Prefix.BPL);
        Baseplate baseplate = new Baseplate(id);
        baseplate.setName(name);
        baseplate.setCreator(creator);
        baseplate.setOwner(creator);
        baseplateList.add(baseplate);
        return baseplate;
    }


    public boolean loadBaseplatesFromFile(String filename) {
        File file = new File(filename);
        return loadBaseplatesFromFile(file);
    }

    public boolean loadBaseplatesFromFile(File file) {
        boolean retval = true;
        List<Baseplate> loadBaseplates = (ArrayList<Baseplate>) loadFromFile(file);
        if (loadBaseplates != null) {
            baseplateList = loadBaseplates;
            Long highestID = getHighestID(baseplateList);
            IDManager.getInstance().setLastAddedID(HalboID.Prefix.BPL, highestID);
        }
        return retval;
    }


    public boolean storeBaseplatesInFile(String filename) {
        return storeInFile(baseplateList, filename);
    }

    public boolean storeBaseplatesInFile(File file) {
        return storeInFile(baseplateList, file);
    }


}
