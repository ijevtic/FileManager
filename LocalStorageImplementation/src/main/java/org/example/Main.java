package org.example;

import org.raf.specification.Configuration;

public class Main {

    public static void main(String[] args) throws Exception {
        Implementation im = new Implementation();
        Configuration c = new Configuration();
        im.createStorage(c, "/home/ijevtic/Desktop", "gas");

//        im.loadStorage("/home/ijevtic/Desktop/gas");


    }
}