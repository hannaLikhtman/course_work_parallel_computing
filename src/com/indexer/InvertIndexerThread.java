package com.indexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

    @Override
    public void run() {

        for (int idx = startIndex; idx < endIndex; idx++) {
            try (BufferedReader file = new BufferedReader(new FileReader(processingFiles.get(idx)))) {
                String line;
                ArrayList<String> words = new ArrayList<>();

                while ((line = file.readLine()) != null) {
                    line = line.replaceAll("[^a-zA-Z0-9 ]", "")
                            .trim()
                            .replaceAll(" +", " ")
                            .toLowerCase();
                    Collections.addAll(words, line.split(" "));
                }

                int PositionInFile = 1;

                for(String word: words) {
                    if(HashMapOneThread.containsKey(word)) {
                        ArrayList<String> list = HashMapOneThread.get(word);
                        list.add(String.valueOf(PositionInFile));
                        HashMapOneThread.put(word, list);
                    }
                    else {
                        ArrayList<String> list = new ArrayList<String>();
                        list.add(String.valueOf(PositionInFile));
                        HashMapOneThread.put(word, list);
                    }

                    PositionInFile ++;
                }

                for (String word: words) {
                    ArrayList<String> list = HashMapOneThread.get(word);

                    if (!list.contains(String.valueOf(processingFiles.get(idx)))) {
                        list.add(String.valueOf(processingFiles.get(idx)));
                        HashMapOneThread.put(word, list);
                    }
                }
            }catch (IOException e) {
                System.out.println("File: " + processingFiles.get(idx) + " not found.");
            }
        }
    }
}
