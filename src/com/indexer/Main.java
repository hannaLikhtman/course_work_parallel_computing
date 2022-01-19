package com.indexer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    //Hence we can alter number of threads to execute program
    public static int THREADS = 500;

    public static void main(String[] args) throws IOException {
        Files filesClass = new Files();
        filesClass.setFilepath("D:\\Study\\Parallel computing\\aclImdb\\");

        ArrayList<File> files = filesClass.getFiles();
        HashMap<String, ArrayList<String>> FullInvertedIndex = new HashMap<String, ArrayList<String>>();
        long start = System.currentTimeMillis();

        InvertIndexerThread[] ThreadArray = new InvertIndexerThread[THREADS];

        //run all threads
        for(int i = 0; i < THREADS; i++) {
            ThreadArray[i] = new InvertIndexerThread(files.size() / THREADS * i,
                    i == (THREADS - 1) ? files.size() : files.size() / THREADS * (i + 1),
                    files);
            ThreadArray[i].start();
        }

        //join threads
        for (int i = 0; i < THREADS; i++) {
            try {
                ThreadArray[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //process threads to map values into indexer
        for (int OneThread = 0; OneThread < THREADS; OneThread++) {
            ThreadArray[OneThread].getInvertedIdx().forEach((k, v) -> FullInvertedIndex.merge(k, v, (v1, v2) -> {
                ArrayList<String> list = new ArrayList<>(v1);
                list.addAll(v2);
                return list;
            }));
        }

        System.out.println("Processing time: " + (System.currentTimeMillis() - start));

        //Write down index in file
        FileWriter fw = new FileWriter( "D:\\Study\\Parallel computing\\InvertedIdx.txt" );
        for(Map.Entry<String, ArrayList<String>> entry: FullInvertedIndex.entrySet()) {
            fw.write(entry.getKey() + " : " + entry.getValue() + "\n");
        }

        fw.close();
    }
}
