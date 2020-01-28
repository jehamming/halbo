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
        Long lastId = lastAdded.get(prefix);
        if (lastId == null) { // Never created an ID before.
            lastId = 0L;
        }
        Long nextId = lastId + 1;
        HalboID newId = new HalboID(prefix, nextId);
        lastAdded.put(prefix, nextId); // Store last added ID for this prefix
        return newId;
    }

    public void setLastAddedID(HalboID.Prefix prefix, Long id) {
        lastAdded.put(prefix, id); // Store last added ID for this prefix
    }


}
