package org.example;

import org.example.example.Implementation;
import org.raf.specification.SpecFile;

import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception {
        Implementation im = new Implementation();
        im.createStorage("/lolcina", "nesto");
        SpecFile s = new SpecFile("/home/ijevtic/Desktop/proba.pdf", "proba.pdf");
        SpecFile dest = new SpecFile("/lolcina/lol1", "lol1");
        im.addFiles(List.of(s), dest.getPath());
    }
}