package org.dvillanueva.io.speedtest;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RandomContentFileWriter {
    private final int numberOfFiles;
    private final File dir;

    public RandomContentFileWriter(int numberOfFiles, File dir) {
        this.numberOfFiles = numberOfFiles;
        this.dir = dir;
    }

    public List<File> write() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int j=0; j < 5000; j++){
            sb.append(UUID.randomUUID().toString());
        }
        String contents = sb.toString();
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
