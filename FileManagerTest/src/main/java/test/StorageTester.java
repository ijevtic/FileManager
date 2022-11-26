package test;

import org.raf.exceptions.BrokenConfigurationException;
import org.raf.exceptions.FileNotFoundCustomException;
import org.raf.specification.FileHandler;
import org.raf.specification.StorageManager;
import org.raf.specification.Storage;
import org.raf.specification.StorageProvider;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class StorageTester {
    public static void main(String[] args) {
        System.out.println("stvarno je buildovao");
        Scanner in = new Scanner(System.in);
        boolean play = true;

        if(args.length<2){
            System.exit(0);
        }

        try {
            Class.forName("org.example.example.GDImplementation");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String path = args[0];
        String name = args[1];
        String loadStorage = "0";
        if(args.length > 2)
            loadStorage = args[2];
        StorageManager storageManager = null;
        try {
            storageManager = StorageProvider.getStorageManager(path, name, loadStorage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String input = "";
        PrintUtils.printUtils();
        boolean listed = true;

        while(true) {
            input = in.nextLine();

            if(input.equals("test1")) {
                new File("/home/ijevtic/Desktop/aaa/test1").mkdirs();
                continue;
            }
            if(input.equals("test2")) {
                new File("/home/ijevtic/Desktop/aaa/test2/").mkdirs();
                continue;
            }

            if(input.equals("exit")) {
                try {
                    storageManager.saveStorage();
                } catch (IOException e) {
                    System.out.println("failed saving the state, " + e.getMessage());
                }
                System.exit(0);
            }
            if(input.equals(".")) {
                PrintUtils.printUtils();
            }
            else {
                //"X&Y a b c"
                //{"/nesto/nesto","/nesto2/nesto2"}
                input = input.replaceAll("( )+", " ");
                String [] niz = input.split(" ");
                int firstOption = 0;
                int wrapperOption = -1;
                if(niz[0].contains("&")) {
                    firstOption = Integer.parseInt(niz[0].split("&")[0]);
                    wrapperOption = Integer.parseInt(niz[0].split("&")[1]);
                }
                else
                    firstOption = Integer.parseInt(niz[0]);
                try {
                    Object response = PrintUtils.callFunction(firstOption, niz, storageManager);
                    PrintUtils.showResponse(firstOption, wrapperOption, response);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }
}
