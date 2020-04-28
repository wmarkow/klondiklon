package com.github.wmarkow.klondiklon.tiled.tsx;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class TsxFileReader
{
    public TilesetElement readTsx(String relativeFilePath) throws Exception
    {
        Serializer serializer = new Persister();
        File source = new File(relativeFilePath);

        return serializer.read(TilesetElement.class, source);
    }
}
