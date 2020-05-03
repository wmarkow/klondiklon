package com.github.wmarkow.klondiklon.tiled.tmx;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class TmxFileWriter
{

    public void writeTmx(MapElement mapElement, String relativeFilePath) throws Exception
    {
        Serializer serializer = new Persister();
        File dstFile = new File(relativeFilePath);

        serializer.write(mapElement, dstFile);
    }
}
