package com.github.wmarkow.klondiklon.tiled.tmx;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class TmxFileWriterTest
{
    private TmxFileReader tmxReader = new TmxFileReader();
    private TmxFileWriter subject = new TmxFileWriter();

    @Before
    public void init()
    {
        File file = new File("target/test");
        file.mkdirs();
    }

    @Test
    public void testReadAndWrite() throws Exception
    {
        // read
        MapElement mapElement = tmxReader.readTmx("src/test/resources/home.tmx");

        // write
        subject.writeTmx(mapElement, "target/test/home.tmx");
        
        // and read again
        MapElement mapElement2 = tmxReader.readTmx("target/test/home.tmx");
    }
}
