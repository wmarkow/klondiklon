package com.github.wmarkow.klondiklon;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mapeditor.core.Map;
import org.mapeditor.io.TMXMapReader;
import org.mapeditor.io.TMXMapWriter;

public class MapReadWriteTest
{

    @Test
    public void testReadWrite() throws IOException
    {
        File file = new File("target/classes/worlds/home/home.tmx");
        TMXMapReader tmxMapReader = new TMXMapReader();
        Map tmxMap = null;
        try
        {
            tmxMap = tmxMapReader.readMap(file.getAbsolutePath());
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        TMXMapWriter tmxMapWriter = new TMXMapWriter();
        tmxMapWriter.settings.compressLayerData = false;
        File finalDst = new File("target/tests/worlds/home/home2.tmx");
        finalDst.getParentFile().mkdirs();
        finalDst.delete();

        File tempDst = new File("target/classes/worlds/home/home2.tmx");

        tmxMapWriter.writeMap(tmxMap, tempDst.getAbsolutePath());
        FileUtils.moveFile(tempDst, finalDst);
    }
}
