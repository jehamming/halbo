package com.hamming.test.model;

import com.hamming.halbo.datamodel.intern.HalboID;
import org.junit.Test;

public class TestHalboID {

    @Test
    public void testValueOf() {
        HalboID id1 = HalboID.valueOf(HalboID.Prefix.BLK, 22L);
        assert id1.getPrefix().equals(HalboID.Prefix.BLK);
        assert id1.getId() == 22L;

        HalboID id2 = HalboID.valueOf("USR33");
        assert id2.getPrefix().equals(HalboID.Prefix.USR);
        assert id2.getId() == 33L;

    }
}
