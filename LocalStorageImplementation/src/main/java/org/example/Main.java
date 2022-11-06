package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.raf.specification.Configuration;
import org.raf.specification.SpecFile;
import org.raf.specification.Storage;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {
        Implementation im = new Implementation();
        im.createStorage();
        im.loadStorage("/home/ijevtic/Desktop/PredefinedName");
    }

}