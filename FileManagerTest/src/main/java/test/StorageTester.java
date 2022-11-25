package test;

import org.raf.specification.Storage;
import org.raf.specification.StorageManager;

import java.util.Scanner;

public class StorageTester {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean play = true;

        try {
            Class.forName("org.example.Implementation");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

//        String path = args[0];
//        Storage st = StorageManager.getStorage(path);
//        System.out.println(st.getPath());
        System.out.println("Ide gascina");
        boolean init = false;

    }
}
