package org.dvillanueva.io.speedtest;

import java.util.UUID;

public class RandomContentGenerator {
    public String generateRandomString(){
        StringBuilder sb = new StringBuilder();
        for (int j=0; j < 5000; j++){
            sb.append(UUID.randomUUID().toString());
        }
        return sb.toString();
    }
}
