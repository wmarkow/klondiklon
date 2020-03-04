package com.github.wmarkow.klondiklon.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class ShadersManager
{
    public ShaderProgram SHADER_OUTLINE;
    public ShaderProgram SHADER_OUTLINE_GREEN;
    public ShaderProgram SHADER_OUTLINE_RED;

    public void init()
    {
        String vertexShader;
        String fragmentShader;
        vertexShader = Gdx.files.classpath("shaders/outline_vertex.glsl").readString();
        fragmentShader = Gdx.files.classpath("shaders/outline_fragment.glsl").readString();

        SHADER_OUTLINE = new ShaderProgram(vertexShader, fragmentShader);
        configureShader(SHADER_OUTLINE, new Vector3(0.0f, 1.0f, 1.0f));

        SHADER_OUTLINE_GREEN = new ShaderProgram(vertexShader, fragmentShader);
        configureShader(SHADER_OUTLINE_GREEN, new Vector3(0.0f, 1.0f, 0.0f));

        SHADER_OUTLINE_RED = new ShaderProgram(vertexShader, fragmentShader);
        configureShader(SHADER_OUTLINE_RED, new Vector3(1.0f, 0.0f, 0.0f));
    }

    private void configureShader(ShaderProgram shader, Vector3 color)
    {
        if (!shader.isCompiled())
        {
            throw new GdxRuntimeException("Couldn't compile shader: " + shader.getLog());
        }

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        shader.begin();
        shader.setUniformf("u_viewportInverse", new Vector2(1f / width, 1f / height));
        shader.setUniformf("u_offset", 10);
        shader.setUniformf("u_step", Math.min(1f, width / 70f));
        shader.setUniformf("u_color", color);
        shader.end();
    }
}
