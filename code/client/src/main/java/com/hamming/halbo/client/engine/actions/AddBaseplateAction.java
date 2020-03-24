package com.hamming.halbo.client.engine.actions;

import com.hamming.halbo.client.engine.GLViewer;
import com.wijlen.ter.halbo.lwjgl.entities.Player;
import com.wijlen.ter.halbo.lwjgl.renderEngine.Loader;
import com.wijlen.ter.halbo.lwjgl.terrains.FlatTerrain;
import com.wijlen.ter.halbo.lwjgl.terrains.Terrain;
import com.wijlen.ter.halbo.lwjgl.textures.TerrainTexture;
import com.wijlen.ter.halbo.lwjgl.textures.TerrainTexturePack;


public class SetBaseplateAction implements Action {

    private GLViewer viewer;
    private String baseplateId;
    private Loader loader;
    private int size;

    public SetBaseplateAction(GLViewer viewer, Loader loader, String baseplateId, int size) {
        this.loader = loader;
        this.viewer = viewer;
        this.baseplateId = baseplateId;
        this.size = size;
    }

    @Override
    public void execute() {
        TerrainTexture texture = new TerrainTexture(loader.loadTexture("baseplate"));

        FlatTerrain terrain = new FlatTerrain(size, 0, 0, loader, texture);
        viewer.setTerrain(baseplateId, terrain);
    }

}
