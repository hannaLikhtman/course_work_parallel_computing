package com.indexer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    //Hence we can alter number of threads to execute program
    private static long doThreads(int threads) throws IOException {
        Files filesClass = new Files();
        filesClass.setFilepath("D:\\Study\\Parallel computing\\aclImdb\\");

        ArrayList<File> files = filesClass.getFiles();

        long start = System.currentTimeMillis();

        HashMap<String, ArrayList<String>> FullInvertedIndex = new HashMap<String, ArrayList<String>>();
        InvertIndexerThread[] ThreadArray = new InvertIndexerThread[threads];

        //run all threads
        for (int i = 0; i < threads; i++) {
            ThreadArray[i] = new InvertIndexerThread(files.size() / threads * i,
                    i == (threads - 1) ? files.size() : files.size() / threads * (i + 1),
                    files);
            ThreadArray[i].start();
        }

        //join threads
        for (int i = 0; i < threads; i++) {
            try {
                ThreadArray[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //process threads to map values into indexer
        for (int OneThread = 0; OneThread < threads; OneThread++) {
            ThreadArray[OneThread].getInvertedIdx().forEach((k, v) -> FullInvertedIndex.merge(k, v, (v1, v2) -> {
                ArrayList<String> list = new ArrayList<>(v1);
                list.addAll(v2);
                return list;
            }));
        }


        long processingTime = (System.currentTimeMillis() - start);
        System.out.println("Threads: " + threads + "    Processing time: " + processingTime + "ms");

        String writeToFilepath = "D:\\Study\\Parallel computing\\InvertedIdx" + threads + ".txt";

        TreeMap<String, ArrayList<String>> sortedInvertedIndex = convertToTreeMap(FullInvertedIndex);

        new FileWriter(writeToFilepath, false).close();
        //Write down index in file
        FileWriter fw = new FileWriter(writeToFilepath);
        for (Map.Entry<String, ArrayList<String>> entry : sortedInvertedIndex.entrySet()) {
            fw.write(entry.getKey() + " : " + entry.getValue() + "\n");
        }

        fw.close();
        return processingTime;
    }

    public static TreeMap<String, ArrayList<String>> convertToTreeMap(HashMap<String, ArrayList<String>> hashMap) {
        // Create a new TreeMap
        TreeMap<String, ArrayList<String>> treeMap = new TreeMap<>();

        // Convert the HashMap to TreeMap manually
        for (Map.Entry<String, ArrayList<String>> e : hashMap.entrySet()) {
            treeMap.put(e.getKey(), e.getValue());
        }

        // Return the TreeMap
        return treeMap;
    }

    public static void main(String[] args) throws IOException {
        int[] threadNum = {4};
        for (int threads : threadNum) {
            doThreads(threads);
        }
    }
}
