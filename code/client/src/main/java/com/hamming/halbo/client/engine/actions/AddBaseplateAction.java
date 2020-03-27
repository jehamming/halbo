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
    private String baseplateId, name;
    private Loader loader;
    private int baseplateSize;
    private int gridSize;
    private int x, z;

    public AddBaseplateAction(GLViewer viewer, Loader loader, String baseplateId, String name,  int baseplateSize, int gridSize, int x, int z) {
        this.loader = loader;
        this.viewer = viewer;
        this.baseplateId = baseplateId;
        this.baseplateSize = baseplateSize;
        this.gridSize = gridSize;
        this.x = x;
        this.z = z;
        this.name = name;
    }

    @Override
    public void execute() {
        int gridZ = z * baseplateSize;
        int gridX = (gridSize - x) * baseplateSize;
        TerrainTexture texture = new TerrainTexture(loader.loadTexture("baseplate2"));
        FlatTerrain terrain = new FlatTerrain(baseplateSize, gridX, gridZ, loader, texture);
        System.out.println(getClass().getName() + ": AddTerrain '"+ baseplateId+"'("+name+") at :" +terrain.getX() +","  +terrain.getZ());
        viewer.addTerrain(terrain);
    }

}
