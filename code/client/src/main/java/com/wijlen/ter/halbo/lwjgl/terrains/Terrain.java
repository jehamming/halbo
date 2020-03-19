package com.wijlen.ter.halbo.lwjgl.terrains;

import com.wijlen.ter.halbo.lwjgl.models.RawModel;
import com.wijlen.ter.halbo.lwjgl.textures.TerrainTexture;
import com.wijlen.ter.halbo.lwjgl.textures.TerrainTexturePack;

public interface Terrain {

    public RawModel getModel();

    float getHeightOfTerrain(float x, float z);

    TerrainTexturePack getTexturePack();

    TerrainTexture getBlendMap();

    float getX();

    float getZ();
}
