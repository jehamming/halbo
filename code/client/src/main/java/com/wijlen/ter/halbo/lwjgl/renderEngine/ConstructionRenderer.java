package com.wijlen.ter.halbo.lwjgl.renderEngine;

import com.hamming.halbo.ldraw.LDRenderedModel;
import com.hamming.halbo.ldraw.LDRenderedPart;
import com.wijlen.ter.halbo.lwjgl.entities.Entity;
import com.wijlen.ter.halbo.lwjgl.models.RawModel;
import com.wijlen.ter.halbo.lwjgl.models.TexturedModel;
import com.wijlen.ter.halbo.lwjgl.shaders.StaticShader;
import com.wijlen.ter.halbo.lwjgl.textures.ModelTexture;
import com.wijlen.ter.halbo.lwjgl.toolbox.Maths;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;
import java.util.Map;

public class ConstructionRenderer {

    private StaticShader shader;

    public ConstructionRenderer(StaticShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(List<LDRenderedModel> constructions) {
        constructions.forEach(construction -> {
            render(construction);
        });
    }

    private void render(LDRenderedModel model) {
        boolean wireframe = false;
        boolean polygon = false;

        for (LDRenderedPart p : model.getParts()) {
            // draw triangles and lines
            if (p.getTriangleVertexCount() > 0 && !p.isHidden()) {
                GL11.glEnable(GL11.GL_LIGHTING);
                GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, p.getTriangleName());
                //GL11.glVertexPointer( 3, GL11.GL_FLOAT, 6 * Buffers.SIZEOF_FLOAT, 0 );
                //GL11.glNormalPointer(GL11.GL_FLOAT,6 * Buffers.SIZEOF_FLOAT ,3 * Buffers.SIZEOF_FLOAT);
                GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, p.getTriangleColorName());
                //GL11.glColorPointer( 4, GL11.GL_UNSIGNED_BYTE, 4 * Buffers.SIZEOF_BYTE, 0 );
                GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, p.getTriangleVertexCount());
                GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
                GL11.glDisable(GL11.GL_LIGHTING);
            }
            if (p.isSelected() && !p.isHidden()) {
                GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
                GL11.glLineWidth(3f);
                GL11.glColor4f(0.6f, 1f, 0.5f, 1f);
                if (p.getLineVertexCount() > 0) {
                    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, p.getLineName());
                    //GL11.glVertexPointer( 3, GL11.GL_FLOAT, 3 * Buffers.SIZEOF_FLOAT, 0 );
                    GL11.glDrawArrays(GL11.GL_LINES, 0, p.getLineVertexCount());
                    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
                }
                if (p.getAuxLineVertexCount() > 0) {
                    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, p.getAuxLineName());
                    //GL11.glVertexPointer( 3, GL11.GL_FLOAT, 3 * Buffers.SIZEOF_FLOAT, 0 );
                    GL11.glDrawArrays(GL11.GL_LINES, 0, p.getAuxLineVertexCount());
                    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
                }
                GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
                GL11.glLineWidth(1f);
            } else if (wireframe && !p.isHidden()) {
                if (p.getLineVertexCount() > 0) {
                    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, p.getLineName());
                    //GL11.glVertexPointer( 3, GL11.GL_FLOAT, 3 * Buffers.SIZEOF_FLOAT, 0 );
                    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, p.getLineColorName());
                    //GL11.glColorPointer( 4, GL11.GL_UNSIGNED_BYTE, 4 * Buffers.SIZEOF_BYTE, 0 );
                    GL11.glDrawArrays(GL11.GL_LINES, 0, p.getLineVertexCount());
                    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
                }
                if (!polygon && p.getAuxLineVertexCount() > 0) {
                    // display aux lines only if polygons are hidden
                    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, p.getAuxLineName());
                    //GL11.glVertexPointer( 3, GL11.GL_FLOAT, 3 * Buffers.SIZEOF_FLOAT, 0 );
                    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, p.getAuxLineColorName());
                    //GL11.glColorPointer( 4, GL11.GL_UNSIGNED_BYTE, 4 * Buffers.SIZEOF_BYTE, 0 );
                    GL11.glDrawArrays(GL11.GL_LINES, 0, p.getAuxLineVertexCount());
                    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
                }
            }
        }
    }

}

