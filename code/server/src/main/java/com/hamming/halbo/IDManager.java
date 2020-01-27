package com.hamming.halbo;

import com.hamming.halbo.datamodel.HalboID;

import java.util.HashMap;
import java.util.Map;

public class IDManager {

    private Map<HalboID.Prefix, Long> lastAdded;
    private static IDManager instance;

    private IDManager() {
       lastAdded = new HashMap<HalboID.Prefix, Long>();
    }

    public static IDManager getInstance() {
        if ( instance == null ) {
            instance = new IDManager();
        }
        return instance;
    }

    public HalboID getNextID( HalboID.Prefix prefix) {
        Long nextId = lastAdded.get(prefix);
        if (nextId == null) { // Never created an ID before.
            nextId = 0L;
        }
        nextId++;
        HalboID newId = new HalboID(prefix, nextId);
        lastAdded.put(prefix, nextId); // Store last added ID for this prefix
        return newId;
    }


}
