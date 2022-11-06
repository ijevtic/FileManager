package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        Implementation im = new Implementation();
        im.createStorage();
        im.createDirectory(im.getStorage().getPath(), "ab{1-5}c");
    }

}