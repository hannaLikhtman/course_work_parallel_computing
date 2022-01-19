package com.indexer;

import java.io.File;
import java.util.ArrayList;

public class Files {

    //Constants used to bear with files
    private int num1 = 12500;
    private int num2 = 50000;
    private String filepath = "D:\\Study\\Parallel computing\\";
    private int variant = 7;

    //Files and folders
    private static ArrayList<File> files = new ArrayList<>();
    private static ArrayList<File> folders = new ArrayList<>();

    private static void readFiles(File[] folders, int n, int variant) {
        for (int FileBeginWith = n / 50 * variant; FileBeginWith < n / 50 * (variant + 1); FileBeginWith++) {
            for (File file : folders) {
                if (file.getName().startsWith(String.valueOf(FileBeginWith))) {
                    files.add(file.getAbsoluteFile());
                }
            }
        }
    }

    //read files
    public ArrayList<File> getFiles() {

        //Folders with rating
        folders.add(new File(filepath + "test\\neg"));
        folders.add(new File(filepath + "test\\pos"));
        folders.add(new File(filepath + "train\\neg"));
        folders.add(new File(filepath + "train\\pos"));
        //Folder contains files w/o rating
        folders.add(new File(filepath + "train\\unsup"));

        //read files from all folders
        for (int processingFolder = 0; processingFolder < folders.size() - 1; processingFolder++) {
            readFiles(folders.get(processingFolder).listFiles(), num1, variant);
        }
        readFiles(folders.get(4).listFiles(), num2, variant);

        return files;
    }
}
