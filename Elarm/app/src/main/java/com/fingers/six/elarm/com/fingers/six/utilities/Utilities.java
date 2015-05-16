package com.fingers.six.elarm.com.fingers.six.utilities;

import android.graphics.Path;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Nghia on 5/16/2015.
 */
public class Utilities {
    static BufferedReader fin;
    static PrintWriter fout;
    static StringTokenizer tokenizer;

    public static void loadFile(String fileName) throws FileNotFoundException {
        fin = new BufferedReader(new FileReader(fileName));
    }

    public static void appendToFile(String fname, String newLine) throws IOException {
        fout = new PrintWriter(new FileWriter(fname), false);
        fout.append(newLine + System.getProperty("line.separator"));
        fout.close();
    }

    public static void writeAllToFile(String fname, String str) throws IOException {
        fout = new PrintWriter(new FileWriter(fname));
        fout.println(str);
        fout.close();
    }

    public static String nextString() throws IOException {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(fin.readLine());
        }
        return tokenizer.nextToken();
    }

    public static int nextInt() throws NumberFormatException, IOException {
        return Integer.parseInt(nextString());
    }
}
