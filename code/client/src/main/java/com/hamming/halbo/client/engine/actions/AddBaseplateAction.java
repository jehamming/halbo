package com.hamming.halbo.client.engine.actions;

import com.hamming.halbo.client.engine.GLViewer;
import com.wijlen.ter.halbo.lwjgl.entities.Player;
import com.wijlen.ter.halbo.lwjgl.renderEngine.Loader;
import com.wijlen.ter.halbo.lwjgl.terrains.FlatTerrain;
import com.wijlen.ter.halbo.lwjgl.terrains.Terrain;
import com.wijlen.ter.halbo.lwjgl.textures.TerrainTexture;
import com.wijlen.ter.halbo.lwjgl.textures.TerrainTexturePack;


public class AddBaseplateAction implements Action {

    private GLViewer viewer;
    private String baseplateId;
    private Loader loader;
    private int size;
    private int row, col;

    public AddBaseplateAction(GLViewer viewer, Loader loader, String baseplateId, int size, int row, int col) {
        this.loader = loader;
        this.viewer = viewer;
        this.baseplateId = baseplateId;
        this.size = size;
        this.row = row;
        this.col = col;
    }

    @Override
    public void execute() {
        int gridX = row * size;
        int gridZ = - (col * size);
        TerrainTexture texture = new TerrainTexture(loader.loadTexture("baseplate"));
        FlatTerrain terrain = new FlatTerrain(size, gridX, gridZ, loader, texture);
        viewer.addTerrain(terrain);
    }

}
