package com.github.wmarkow.klondiklon.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class ShadersManager
{
    public ShaderProgram SHADER_OUTLINE;

    public void init()
    {
        String vertexShader;
        String fragmentShader;
        vertexShader = Gdx.files.classpath("shaders/outline_vertex.glsl").readString();
        fragmentShader = Gdx.files.classpath("shaders/outline_fragment.glsl").readString();
        SHADER_OUTLINE = new ShaderProgram(vertexShader, fragmentShader);
        if (!SHADER_OUTLINE.isCompiled())
        {
            throw new GdxRuntimeException("Couldn't compile shader: " + SHADER_OUTLINE.getLog());
        }

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        SHADER_OUTLINE.begin();
        SHADER_OUTLINE.setUniformf("u_viewportInverse", new Vector2(1f / width, 1f / height));
        SHADER_OUTLINE.setUniformf("u_offset", 10);
        SHADER_OUTLINE.setUniformf("u_step", Math.min(1f, width / 70f));
        SHADER_OUTLINE.setUniformf("u_color", new Vector3(0.0f, 1.0f, 1.0f));
        SHADER_OUTLINE.end();
    }
}
