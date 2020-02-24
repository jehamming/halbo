package com.hamming.halbo.client.interfaces;

import com.hamming.halbo.model.dto.WorldDto;

public interface IWorldWindow {

    public void addWorld(WorldDto dto);

    public WorldDto getSelectedWorld();

    public void empty();
}
