package org.example;

import org.example.example.Implementation;
import org.raf.specification.SpecFile;


public class Main {
    public static void main(String[] args) throws Exception {
        Implementation im = new Implementation();
        im.createStorage("/lolcina", "nesto");
        SpecFile s = new SpecFile("/lolcina/lol2/proba.pdf", "proba.pdf");
        SpecFile dest = new SpecFile("/lolcina/lol1", "lol1");
        im.copyFile(s, dest);
    }
}