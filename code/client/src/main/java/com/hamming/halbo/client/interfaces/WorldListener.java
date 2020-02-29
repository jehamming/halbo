package com.hamming.halbo.client.interfaces;

import com.hamming.halbo.model.dto.WorldDto;

public interface WorldListener {

    public void worldAdded(WorldDto world);
    public void worldDeleted(WorldDto world);

}
