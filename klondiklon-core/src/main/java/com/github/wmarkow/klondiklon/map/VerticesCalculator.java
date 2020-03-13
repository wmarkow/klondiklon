package com.github.wmarkow.klondiklon.map;

import static com.badlogic.gdx.graphics.g2d.Batch.C1;
import static com.badlogic.gdx.graphics.g2d.Batch.C2;
import static com.badlogic.gdx.graphics.g2d.Batch.C3;
import static com.badlogic.gdx.graphics.g2d.Batch.C4;
import static com.badlogic.gdx.graphics.g2d.Batch.U1;
import static com.badlogic.gdx.graphics.g2d.Batch.U2;
import static com.badlogic.gdx.graphics.g2d.Batch.U3;
import static com.badlogic.gdx.graphics.g2d.Batch.U4;
import static com.badlogic.gdx.graphics.g2d.Batch.V1;
import static com.badlogic.gdx.graphics.g2d.Batch.V2;
import static com.badlogic.gdx.graphics.g2d.Batch.V3;
import static com.badlogic.gdx.graphics.g2d.Batch.V4;
import static com.badlogic.gdx.graphics.g2d.Batch.X1;
import static com.badlogic.gdx.graphics.g2d.Batch.X2;
import static com.badlogic.gdx.graphics.g2d.Batch.X3;
import static com.badlogic.gdx.graphics.g2d.Batch.X4;
import static com.badlogic.gdx.graphics.g2d.Batch.Y1;
import static com.badlogic.gdx.graphics.g2d.Batch.Y2;
import static com.badlogic.gdx.graphics.g2d.Batch.Y3;
import static com.badlogic.gdx.graphics.g2d.Batch.Y4;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class VerticesCalculator
{
    static protected final int NUM_VERTICES = 20;
    
    float[] calculate(TextureRegion region, float objectX, float objectY, float color, float unitScale)
    {
        float vertices[] = new float[NUM_VERTICES];
        
        float x1 = (float) (objectX - (region.getRegionWidth() / 2.0) * unitScale);
        float y1 = objectY * unitScale;
        float x2 = x1 + region.getRegionWidth() * unitScale;
        float y2 = y1 + region.getRegionHeight() * unitScale;

        float u1 = region.getU();
        float v1 = region.getV2();
        float u2 = region.getU2();
        float v2 = region.getV();
        
        vertices[X1] = x1;
        vertices[Y1] = y1;
        vertices[C1] = color;
        vertices[U1] = u1;
        vertices[V1] = v1;

        vertices[X2] = x1;
        vertices[Y2] = y2;
        vertices[C2] = color;
        vertices[U2] = u1;
        vertices[V2] = v2;

        vertices[X3] = x2;
        vertices[Y3] = y2;
        vertices[C3] = color;
        vertices[U3] = u2;
        vertices[V3] = v2;

        vertices[X4] = x2;
        vertices[Y4] = y1;
        vertices[C4] = color;
        vertices[U4] = u2;
        vertices[V4] = v1;
        
        return vertices;
    }
}
