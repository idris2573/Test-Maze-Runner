package com.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
    private List<String> lines = new ArrayList<>();

    public ReadFile(String file) throws Exception{

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(file));

            String line;
            while ((line = br.readLine()) != null) {
                this.lines.add(line);
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Cannot find file, please enter full path...");
        }

    }

    public List<String> getLines() {
        return lines;
    }
}
