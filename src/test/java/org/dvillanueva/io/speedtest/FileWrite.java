package org.dvillanueva.io.speedtest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

public class FileWrite {
    private final static int NUMBER_OF_FILES = 1000;
    private final RandomContentGenerator generator = new RandomContentGenerator();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void javaIOFilesTest() throws IOException {
        StopWatch stopWatch = new StopWatch();
        File tempDir = temporaryFolder.newFolder();
        String contents = generator.generateRandomString();
        stopWatch.start();
        for (int i = 0; i < NUMBER_OF_FILES; i ++){
            Files.write(Paths.get(tempDir.getPath(), "dummy_" + i + ".txt"), contents.getBytes(), StandardOpenOption.CREATE_NEW);
        }
        stopWatch.stop();
        System.out.println("Java IO Write Test: Running time of \"" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "\" milliseconds");
    }

    @Test
    public void apacheCommonsFileUtilsTest() throws IOException {
        StopWatch stopWatch = new StopWatch();
        File tempDir = temporaryFolder.newFolder();
        String contents = generator.generateRandomString();
        stopWatch.start();
        for (int i = 0; i < NUMBER_OF_FILES; i ++){
            FileUtils.write(new File(tempDir, "dummy_" + i + ".txt"), contents, "UTF-8");
        }
        stopWatch.stop();
        System.out.println("FileUtils IO Write Test: Running time of \"" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "\" milliseconds");
    }

    @After
    public void cleanUp(){
        temporaryFolder.delete();
    }

}
