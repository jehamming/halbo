package com.wijlen.ter.halbo.lwjgl.renderEngine;

import com.wijlen.ter.halbo.lwjgl.models.RawModel;
import com.wijlen.ter.halbo.lwjgl.shaders.TerrainShader;
import com.wijlen.ter.halbo.lwjgl.terrains.FlatTerrain;
import com.wijlen.ter.halbo.lwjgl.toolbox.Maths;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;


public class FlatTerrainRenderer {

    private TerrainShader shader;

    public FlatTerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.connectTextureUnits();
        shader.stop();
    }

    public void render(List<FlatTerrain> terrains) {
        for (FlatTerrain terrain : terrains) {
            prepareTerrain(terrain);
            loadModelMatrix(terrain);
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(),
                    GL11.GL_UNSIGNED_INT, 0);
            unbindTexturedModel();
        }
    }

    private void prepareTerrain(FlatTerrain terrain) {
        RawModel rawModel = terrain.getModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        bindTextures(terrain);
        shader.loadShineVariables(1, 0);
    }

    private void bindTextures(FlatTerrain terrain) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getTexture().getTextureId());
    }

    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void loadModelMatrix(FlatTerrain terrain) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0, 0, 1);
        shader.loadTransformationMatrix(transformationMatrix);
    }

}
