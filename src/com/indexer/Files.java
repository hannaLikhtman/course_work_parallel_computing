package com.indexer;

import java.io.File;
import java.util.ArrayList;

public class Files {
    //Constants used to bear with files
    public static int N1 = 12500;
    public static int N2 = 50000;
    public static int V = 7;

    //Files and folders
    private static ArrayList<File> files = new ArrayList<>();
    private static ArrayList<File> folders = new ArrayList<>();

    private static void readFiles(ArrayList<File> folders, int n, int variant){
        for (int FileBeginWith = n / 50 * variant; FileBeginWith < n / 50 * (variant + 1); FileBeginWith++) {
            for (File file : folders.get(4).listFiles()) {
                if (file.getName().startsWith(String.valueOf(FileBeginWith))) {
                    files.add(file.getAbsoluteFile());
                }
            }
        }
    }
}
