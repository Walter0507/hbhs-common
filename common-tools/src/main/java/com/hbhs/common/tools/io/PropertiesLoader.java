package com.hbhs.common.tools.io;

import java.io.FileInputStream;
import java.io.IOException;

public class PropertiesLoader {
    public static String folderPath;

    public static void loadFile(String filename) throws IOException {
        if (folderPath==null||"".equals(folderPath.trim())){
            throw new RuntimeException("CONFIG ERROR!!! No folderPath found.");
        }
        String fullPath = (folderPath.endsWith("/")?folderPath:(folderPath+"/")) + filename;
        System.getProperties().load(new FileInputStream(fullPath));
    }

}
