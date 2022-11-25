package org.example;

import org.example.example.Implementation;
import org.raf.specification.SpecFile;

import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception {
        Implementation im = new Implementation();
        im.createStorage("/lolcina", "nesto");
        SpecFile dest = new SpecFile("/home/ijevtic/Desktop/", "Desktop");
        SpecFile source = new SpecFile("/lolcina/lol1/proba.pdf", "proba.pdf");
        im.download(source, dest.getPath());
    }
}