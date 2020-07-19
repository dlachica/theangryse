package org.dvillanueva.io.speedtest;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RandomContentFileWriter {
    private final RandomContentGenerator generator = new RandomContentGenerator();
    private final int numberOfFiles;
    private final File dir;

    public RandomContentFileWriter(int numberOfFiles, File dir) {
        this.numberOfFiles = numberOfFiles;
        this.dir = dir;
    }

    public List<File> write() throws IOException {
        String contents = generator.generateRandomString();
        List<File> toReturn = new ArrayList<>();
        for(int i=0; i < numberOfFiles; i++){
            File dummyFile = new File(dir, "dummyFile" + i + ".txt");
            FileUtils.write(
                    dummyFile,
                    contents,
                    "UTF-8");
            toReturn.add(dummyFile);
        }
        return toReturn;
    }
}
