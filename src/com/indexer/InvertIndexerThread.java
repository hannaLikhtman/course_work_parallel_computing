package com.indexer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class InvertIndexerThread extends Thread {

    int startIndex;
    int endIndex;

    ArrayList<File> processingFiles;
    HashMap<String, ArrayList<String>> HashMapOneThread = new HashMap<String, ArrayList<String>>();

    public InvertIndexerThread(int startIndex, int endIndex, ArrayList<File> processingFiles) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.processingFiles = processingFiles;
    }

    public HashMap<String, ArrayList<String>> getInvertedIdx() {
        return HashMapOneThread;
    }

}
