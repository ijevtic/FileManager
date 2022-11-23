package org.example;

import org.example.example.Implementation;
import org.raf.specification.SpecFile;


public class Main {
    public static void main(String[] args) throws Exception {
        Implementation im = new Implementation();
        im.createStorage("nesto");
        SpecFile s = new SpecFile("/lolcina/nije/docs", "docs");
        im.rename(s, "nije");
    }
}