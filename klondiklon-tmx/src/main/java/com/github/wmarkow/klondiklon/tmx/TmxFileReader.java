package com.github.wmarkow.klondiklon.tmx;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class TmxFileReader
{

    public MapElement readTmx(String relativeFilePath) throws Exception
    {
        Serializer serializer = new Persister();
        File source = new File(relativeFilePath);

        return serializer.read(MapElement.class, source);
    }
}
