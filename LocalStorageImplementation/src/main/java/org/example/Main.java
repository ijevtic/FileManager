package org.example;

import org.raf.specification.SpecFile;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        File lol = new File("/home/ijevtic/Desktop/lol");
        File dir = new File("/home/ijevtic/Desktop/testDir/lol");

//        Files.copy(Paths.get("/home/ijevtic/Desktop/lol"), Paths.get("/home/ijevtic/Desktop/testDir/lol"));
        Implementation im = new Implementation();
        im.rename(new SpecFile("/home/ijevtic/Desktop/testDir/lol", "lol"), "lol2");
//        im.createStorage("/home/ijevtic/Desktop", "testDir");

//        System.out.println(f.getPath());
//        System.out.println(f.getParentFile().getPath());
//        im.moveFile("/home/ijevtic/Desktop/proba/", "/home/ijevtic/Desktop/proba2");
    }
}