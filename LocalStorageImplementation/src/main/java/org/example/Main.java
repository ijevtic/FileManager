package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        Implementation im = new Implementation();
        im.loadStorage("/home/ijevtic/Desktop/PredefinedName");
        File f = new File(im.getStorage().getPath());
        System.out.println(f.getTotalSpace());
    }
}