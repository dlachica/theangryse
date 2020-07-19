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
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FileDelete {
    private final static int NUMBER_OF_FILES = 1000;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void java11IOReadViaFilesStrategyATest() throws IOException {
        StopWatch stopWatch = new StopWatch();
        File tempDir = temporaryFolder.newFolder();
        new RandomContentFileWriter(NUMBER_OF_FILES, tempDir).write();
        stopWatch.start();
        Files.walk(tempDir.toPath())
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        stopWatch.stop();
        System.out.println("Java 11 IO File Delete via Files: Running time of \"" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "\" milliseconds");
    }

    @Test
    public void apacheCommonsFileUtils() throws IOException {
        StopWatch stopWatch = new StopWatch();
        File tempDir = temporaryFolder.newFolder();
        new RandomContentFileWriter(NUMBER_OF_FILES, tempDir).write();
        stopWatch.start();
        FileUtils.deleteDirectory(tempDir);
        stopWatch.stop();
        System.out.println("Apache Commons File Delete via FileUtils: Running time of \"" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "\" milliseconds");
    }

    @After
    public void cleanUp(){
        temporaryFolder.delete();
    }
}
