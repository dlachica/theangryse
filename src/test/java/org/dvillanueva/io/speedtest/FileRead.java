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
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class FileRead {
    private final static int NUMBER_OF_FILES = 1000;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void javaIOReadViaScannerTest() throws IOException {
        StopWatch stopWatch = new StopWatch();
        File tempDir = temporaryFolder.newFolder();
        StringBuilder stringBuilder = new StringBuilder();
        List<File> files = new RandomContentFileWriter(NUMBER_OF_FILES, tempDir).write();
        stopWatch.start();
        files.forEach(file -> {
            try {
                Scanner myReader = new Scanner(file);
                while (myReader.hasNextLine()) {
                    stringBuilder.append(myReader.nextLine());
                }
                myReader.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        stopWatch.stop();
        System.out.println("Java IO via Scanner: Running time of \"" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "\" milliseconds");
    }

    @Test
    public void java11IOReadViaFilesStrategyATest() throws IOException {
        StopWatch stopWatch = new StopWatch();
        File tempDir = temporaryFolder.newFolder();
        StringBuilder stringBuilder = new StringBuilder();
        List<File> files = new RandomContentFileWriter(NUMBER_OF_FILES, tempDir).write();
        stopWatch.start();
        files.forEach(file -> {
            try {
                 stringBuilder.append(Files.readString(file.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        stopWatch.stop();
        System.out.println("Java 11 IO via Files: Strategy A : Running time of \"" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "\" milliseconds");
    }

    @Test
    public void java11IOReadViaFilesStrategyBTest() throws IOException {
        StopWatch stopWatch = new StopWatch();
        File tempDir = temporaryFolder.newFolder();
        StringBuilder stringBuilder = new StringBuilder();
        new RandomContentFileWriter(NUMBER_OF_FILES, tempDir).write();
        stopWatch.start();
        Files.walk(tempDir.toPath())
            .forEach(path -> {
                try {
                    if (!path.toFile().isDirectory())
                        stringBuilder.append(Files.readString(path));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
        });
        stopWatch.stop();
        System.out.println("Java 11 IO File Read via Files: Strategy B: Running time of \"" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "\" milliseconds");
    }

    @Test
    public void apacheCommonsUtilTest() throws IOException {
        StopWatch stopWatch = new StopWatch();
        File tempDir = temporaryFolder.newFolder();
        List<File> files = new RandomContentFileWriter(NUMBER_OF_FILES, tempDir).write();
        StringBuilder stringBuilder = new StringBuilder();
        stopWatch.start();
        files.forEach(file -> {
            try {
                stringBuilder.append(FileUtils.readFileToString(file, "UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        stopWatch.stop();
        System.out.println("Apache Commons File Util File Read: Running time of \"" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "\" milliseconds");
    }

    @After
    public void cleanUp(){
        temporaryFolder.delete();
    }
}
