package com.wijlen.ter.halbo.lwjgl.terrains;

import com.wijlen.ter.halbo.lwjgl.models.RawModel;
import com.wijlen.ter.halbo.lwjgl.renderEngine.Loader;
import com.wijlen.ter.halbo.lwjgl.textures.TerrainTexture;
import com.wijlen.ter.halbo.lwjgl.textures.TerrainTexturePack;
import com.wijlen.ter.halbo.lwjgl.toolbox.Maths;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FlatTerrain {

    private int size = 200;
    private float x;
    private float z;
    private RawModel model;
    private TerrainTexture texture;

    public FlatTerrain(int size, int gridX, int gridZ, Loader loader, TerrainTexture texture) {
        this.texture  = texture;
        this.size = size;
        this.x = gridX - this.size;
        this.z = gridZ;
        this.model = generateTerrain(loader);
    }


    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public RawModel getModel() {
        return model;
    }

    public TerrainTexture getTexture() {
        return texture;
    }

    private RawModel generateTerrain(Loader loader) {
        int count = size * size;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (size - 1) * (size - 1)];
        int vertexPointer = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                vertices[vertexPointer * 3] = (float) j / ((float) size - 1) * size;
                vertices[vertexPointer * 3 + 2] = (float) i / ((float) size - 1) * size;
                Vector3f normal = new Vector3f(0, 2f, 0);
                normal.normalise();
                normals[vertexPointer * 3] = normal.x;
                normals[vertexPointer * 3 + 1] = normal.y;
                normals[vertexPointer * 3 + 2] = normal.z;
                textureCoords[vertexPointer * 2] = (float) j / ((float) size - 1);
                textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) size - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for (int gz = 0; gz < size - 1; gz++) {
            for (int gx = 0; gx < size- 1; gx++) {
                int topLeft = (gz * size) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz + 1) * size) + gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loader.loadToVAO(vertices, textureCoords, normals, indices);
    }




}
