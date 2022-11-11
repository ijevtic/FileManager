package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        Implementation im = new Implementation();
        im.createStorage("home/ijevtic/Desktop", "S1");
//        im.loadStorage("/home/ijevtic/Desktop/PredefinedName");
//        File f = new File(im.getStorage().getPath());
//        im.createDirectory(im.getStorage().getPath(), "abc{1-20}d");
//        System.out.println(f.length());

    }
}