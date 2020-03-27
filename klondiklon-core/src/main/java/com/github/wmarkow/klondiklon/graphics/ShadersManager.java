package com.github.wmarkow.klondiklon.graphics;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class ShadersManager
{
    public final static String SHADER_OUTLINE = "SHADER_OUTLINE";
    public final static String SHADER_OUTLINE_GREEN = "SHADER_OUTLINE_GREEN";
    public final static String SHADER_OUTLINE_RED = "SHADER_OUTLINE_RED";

    private Map<String, ShaderProgram> shaders = new HashMap<String, ShaderProgram>();

    public void init()
    {
        String vertexShader;
        String fragmentShader;
        vertexShader = Gdx.files.classpath("shaders/outline_vertex.glsl").readString();
        fragmentShader = Gdx.files.classpath("shaders/outline_fragment.glsl").readString();

        ShaderProgram shaderOutline = new ShaderProgram(vertexShader, fragmentShader);
        configureShader(shaderOutline, new Vector3(0.0f, 1.0f, 1.0f));
        registerShader(SHADER_OUTLINE, shaderOutline);

        ShaderProgram shaderOutlineGreen = new ShaderProgram(vertexShader, fragmentShader);
        configureShader(shaderOutlineGreen, new Vector3(0.0f, 1.0f, 0.0f));
        registerShader(SHADER_OUTLINE_GREEN, shaderOutlineGreen);

        ShaderProgram shaderOutlineRed = new ShaderProgram(vertexShader, fragmentShader);
        configureShader(shaderOutlineRed, new Vector3(1.0f, 0.0f, 0.0f));
        registerShader(SHADER_OUTLINE_RED, shaderOutlineRed);
    }

    public void registerShader(String name, ShaderProgram shader)
    {
        shaders.put(name, shader);
    }

    public ShaderProgram getShadere(String name)
    {
        return shaders.get(name);
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
