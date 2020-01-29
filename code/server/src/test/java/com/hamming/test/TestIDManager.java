package com.hamming.test;

import com.hamming.halbo.IDManager;
import com.hamming.halbo.datamodel.HalboID;
import org.junit.Test;

public class TestIDManager {

    @Test
    public void testBasicIDs() {
        HalboID id1 = IDManager.getInstance().getNextID(HalboID.Prefix.BLK);

        assert id1.getId() == 1L;
    }
}
