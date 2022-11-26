package test;

import org.raf.exceptions.BrokenConfigurationException;
import org.raf.exceptions.FileNotFoundCustomException;
import org.raf.specification.ESortingOrder;
import org.raf.specification.SortingCriteria;
import org.raf.specification.SpecFile;
import org.raf.specification.StorageManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PrintUtils {
    public static void printUtils() {
        System.out.println("1 Create Directory");
        System.out.println("2 Add files");
        System.out.println("3 Remove files");
        System.out.println("4 Move files");
        System.out.println("5 Copy files");
        System.out.println("6 Download files");
        System.out.println("7 Rename files");
        System.out.println("8 Return directory files");
        System.out.println("9 Return subdirectory files");
        System.out.println("10 Return all directory files");
        System.out.println("11 Contains file");
        System.out.println("12 Return file location");
        System.out.println("13 Sort files");
        System.out.println("14 Return files modified during period");
        System.out.println("15 Return files created during period");
        System.out.println("16 Create file");

        System.out.println("Wrapper methods");
        System.out.println();
        //Wrapper methods
        System.out.println("1 File name");
        System.out.println("2 File path");
        System.out.println("3 Date created");
        System.out.println("4 Date modified");
        System.out.println("5 if directory");
    }

    static List<SpecFile> list = null;
    static List<String> strList = null;
    static LocalDateTime time1 = null;
    static LocalDateTime time2 = null;

    public static Object callFunction(int x, String [] args, StorageManager sm) throws Exception{

        for(int i = 0; i < args.length; i++) {
            args[i] = formatInputString(args[i]);
        }

        if(x == 1) {
            if(args.length == 2) {
                sm.createDirectory(args[1]);
                return null;
            }
            if(args.length == 3 && isNumeric(args[2])) {
                sm.createDirectory(args[1], Integer.parseInt(args[2]));
                return null;
            }
            if(args.length == 3) {
                sm.createDirectory(args[1], args[2]);
                return null;
            }
            if(args.length == 4 && isNumeric(args[3])) {
                sm.createDirectory(args[1], args[2],
                        Integer.parseInt(args[3]));
                return null;
            }
            System.out.println("false command");
            return null;
        }
        if(x == 2) {
            if(isSpecFileList(args[1]) && args.length == 3) {
                sm.addFiles(list, args[2]);
                return null;
            }
            System.out.println("wrong command");
            return null;
        }
        if(x == 3) {
            if(isValidPath(args[1]) && args.length == 2) {
                sm.removeFile(args[1]);
                return null;
            }
            if(isSpecFileList(args[1]) && args.length == 2) {
                sm.removeFiles((SpecFile[]) list.toArray());
                return null;
            }
            System.out.println("wrong command");
            return null;
        }
        if(x == 4) {
            if(isValidPath(args[1]) && isValidPath(args[2]) && args.length == 3) {
                sm.moveFile(args[1], args[2]);
                return null;
            }
            if(isSpecFileList(args[1]) && isValidPath(args[2]) && args.length == 3) {
                sm.moveFiles(listToStringList(), args[2]);
                return null;
            }
            System.out.println("wrong command");
            return null;
        }
        if(x == 5) {
            if(isValidPath(args[1]) && isValidPath(args[2]) && args.length == 3) {
                sm.copyFile(args[1], args[2]);
                return null;
            }
            if(isSpecFileList(args[1]) && isValidPath(args[2]) && args.length == 3) {
                sm.copyFiles(listToStringList(), args[2]);
                return null;
            }
            System.out.println("wrong command");
            return null;
        }
        if(x == 6) {
            if(isValidPath(args[1]) && isValidPath(args[2]) && args.length == 3) {
                sm.download(args[1], args[2]);
                return null;
            }
            System.out.println("wrong command");
            return null;
        }
        if(x == 7) {
            if(isValidPath(args[1]) && args.length == 3) {
                sm.rename(args[1], args[2]);
                return null;
            }
            System.out.println("wrong command");
            return null;
        }
        if(x == 8) {
            if(isValidPath(args[1]) && args.length == 2) {
                return sm.returnDirectoryFiles(args[1]);
            }
            if(isValidPath(args[1]) && args.length == 3) {
                return sm.returnDirectoryFiles(args[1], args[2]);
            }
            if(isValidPath(args[1]) && stringToList(args[2]) && args.length == 3) {
                return sm.returnDirectoryFiles(args[1], strList);
            }
            if(isValidPath(args[1]) && stringToList(args[2]) && args.length == 4) {
                return sm.returnDirectoryFiles(args[1], strList, args[3]);
            }
            System.out.println("wrong command");
            return null;
        }
        if(x == 9) {
            if(isValidPath(args[1]) && args.length == 2) {
                return sm.returnSubdirectoryFile(args[1]);
            }
            if(isValidPath(args[1]) && args.length == 3) {
                return sm.returnSubdirectoryFile(args[1], args[2]);
            }
            if(isValidPath(args[1]) && stringToList(args[2]) && args.length == 3) {
                return sm.returnSubdirectoryFile(args[1]);
            }
            if(isValidPath(args[1]) && stringToList(args[2]) && args.length == 4) {
                return sm.returnSubdirectoryFile(args[1], strList, args[3]);
            }
            System.out.println("wrong command");
            return null;
        }
        if(x == 10) {
            if(isValidPath(args[1]) && args.length == 2) {
                return sm.returnAllDirectoryFiles(args[1]);
            }
            if(isValidPath(args[1]) && args.length == 3) {
                return sm.returnAllDirectoryFiles(args[1], args[2]);
            }
            if(isValidPath(args[1]) && stringToList(args[2]) && args.length == 3) {
                return sm.returnAllDirectoryFiles(args[1], strList);
            }
            if(isValidPath(args[1]) && stringToList(args[2]) && args.length == 4) {
                return sm.returnAllDirectoryFiles(args[1], strList, args[3]);
            }
            System.out.println("wrong command");
            return null;
        }
        if(x == 11) {
            if(isValidPath(args[1]) && stringToList(args[2]) && args.length == 3) {
                return sm.containsFile(args[1], strList);
            }
            System.out.println("wrong command");
            return null;
        }
        if(x == 12) {
            if(isValidPath(args[1]) && args.length == 2)
                return sm.returnFileLocation(args[1]);
            System.out.println("wrong command");
            return null;
        }
        if(x == 13) {
            SortingCriteria sc = sortingCriteria(args[1]);
            if(sc != null && isSpecFileList(args[2]) && args.length == 3) {
                System.out.println(sc.toString());
                System.out.println("posle f sort " + list.size());
                return sm.sortFiles(sc, list);
            }
        }
        if(x == 14) {
            if(isLocalDateTime1(args[1]) && isLocalDateTime2(args[2]) && isValidPath(args[3]) &&
            args.length == 4) {
                return sm.returnFilesModifiedDuringPeriod(time1, time2, args[3]);
            }
            System.out.println("wrong command");
            return null;
        }
        if(x == 15) {
            if(isLocalDateTime1(args[1]) && isLocalDateTime2(args[2]) && isValidPath(args[3]) &&
                    args.length == 4) {
                return sm.returnFilesCreatedDuringPeriod(time1, time2, args[3]);
            }
            System.out.println("wrong command");
            return null;
        }
        if(x == 16) {
            if(isValidPath(args[1]) && args.length == 2) {
                sm.createFile(formatInputString(args[1]));
                return null;
            }
            if(isValidPath(args[1]) && args.length == 3) {
                sm.createFile(formatInputString(args[1]), args[2]);
                return null;
            }
            System.out.println("wrong command");
            return null;
        }
        System.out.println("wrong command");
        return null;
    }

    public static void showResponse(int x, int wrapperOption, Object response) {
        if(x == 8 || x == 9 || x == 10 || x == 13 || x == 14 || x == 15)
            printSpecFileList(response, wrapperOption);
        if(x == 11) {
            boolean contains = (boolean) response;
            System.out.println("contains = " + contains);
        }
        if(x == 12) {
            String fileLocation = (String) response;
            System.out.println("File location: " + fileLocation);
        }
    }

    static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    private static boolean isNumeric(String s) {
        if (s == null) {
            return false;
        }
        return pattern.matcher(s).matches();
    }

    private static boolean isSpecFileList(String s) {
        String scopy = new String(s);
        list = new ArrayList<>();
        if(scopy.indexOf("{") != 0 || scopy.lastIndexOf("}") != scopy.length() - 1)
            return false;
        scopy = scopy.substring(1, scopy.length()-1);
        String [] niz = scopy.split(",");
        for(String path: niz) {
            if(!isValidPath(path))
                return false;
            list.add(new SpecFile(formatInputString(path)));
        }
        return true;
    }

    private static boolean isValidPath(String path) {
        String scopy = new String(path);
        scopy = scopy.substring(0, scopy.length()-1);
        if(scopy.charAt(0) == '/') scopy = scopy.substring(1);
        if(scopy.charAt(scopy.length()-1) == '/') scopy = scopy.substring(0, scopy.length()-1);
        if(scopy.equals("")) return true;
        return !scopy.contains("//");
    }
    
    private static List<String> listToStringList(){
        List<String> retList = new ArrayList<>();
        for(SpecFile s: list) {
            retList.add(s.getPath());
        }
        return retList;
    }
    
    private static boolean stringToList(String s) {
        strList = new ArrayList<>();
        String scopy = new String(s);

        if(scopy.indexOf("{") != 0 || scopy.lastIndexOf("}") != scopy.length() - 1)
            return false;
        scopy = scopy.substring(1, scopy.length()-1);

        String [] niz = scopy.split(",");
        for(String t:niz) {
            if(t.charAt(0) != '\"' || t.charAt(t.length()-1) != '\"')
                return false;
            strList.add(t.replaceAll("\"", ""));
        }
        return true;
    }

    //{0,0,1,asc}
    private static SortingCriteria sortingCriteria(String s) {
        String scopy = new String(s);
        if(scopy.indexOf("{") != 0 || scopy.lastIndexOf("}") != scopy.length() - 1)
            return null;
        scopy = scopy.substring(1, scopy.length()-1);

        String [] niz = scopy.split(",");
        List<Boolean> bArr = new ArrayList<>();
        ESortingOrder sortingOrder = ESortingOrder.ASCENDING;
        for(String t:niz) {
            if(bArr.size() < 3) {
                if(t.equals("0")) bArr.add(false);
                else if(t.equals("1")) bArr.add(true);
                else return null;
                continue;
            }
            if(t.equals("desc"))
                sortingOrder = ESortingOrder.DESCENDING;
        }
        if(bArr.size() < 3) return null;
        return new SortingCriteria(bArr.get(0), bArr.get(1), bArr.get(2), sortingOrder);
    }

    //{gg,m,d,s,m,s}
    private static boolean isLocalDateTime1(String s) {
        String scopy = new String(s);
        if(scopy.indexOf("{") != 0 || scopy.lastIndexOf("}") != scopy.length() - 1)
            return false;
        scopy = scopy.substring(1, scopy.length()-1);
        String[] lista = scopy.split(",");
        List<Integer> listaBr = new ArrayList<>();
        for(String l : lista) {
            if(!isNumeric(l))
                return false;
            listaBr.add(Integer.parseInt(l));
        }
        if(listaBr.size() != 6)
            return false;
        time1 = LocalDateTime.of(listaBr.get(0), listaBr.get(1), listaBr.get(2), listaBr.get(3), listaBr.get(4),
                listaBr.get(5));
        return true;
    }
    private static boolean isLocalDateTime2(String s) {
        String scopy = new String(s);
        if(scopy.indexOf("{") != 0 || scopy.lastIndexOf("}") != scopy.length() - 1)
            return false;
        scopy = scopy.substring(1, scopy.length()-1);
        String[] lista = scopy.split(",");
        List<Integer> listaBr = new ArrayList<>();
        for(String l : lista) {
            if(!isNumeric(l))
                return false;
            listaBr.add(Integer.parseInt(l));
        }
        if(listaBr.size() != 6)
            return false;
        time2 = LocalDateTime.of(listaBr.get(0), listaBr.get(1), listaBr.get(2), listaBr.get(3), listaBr.get(4),
                listaBr.get(5));
        return true;
    }


    private static void printSpecFileList(Object s, int wrapperOption) {
        List<SpecFile> specFiles = (List<SpecFile>) s;
        for(SpecFile file:specFiles) {
            if(wrapperOption == 1)
                System.out.println(file.getFileName());
            else if(wrapperOption == 2)
                System.out.println(file.getPath());
            else if(wrapperOption == 3)
                System.out.println(file.getDateCreated());
            else if(wrapperOption == 4)
                System.out.println(file.getDateModified());
            else if(wrapperOption == 5)
                System.out.println(file.isDirectory());

            else {
                System.out.println(file.toString());
            }

//            System.out.println("1 File name");
//            System.out.println("2 File path");
//            System.out.println("3 Date created");
//            System.out.println("4 Date modified");
//            System.out.println("5 if directory");

//            System.out.println(file.toString());
        }
        return;
    }
    private static String formatInputString(String s) {
        String scopy = new String(s);
        if(scopy.charAt(0) == '\"') scopy = scopy.substring(1);
        if(scopy.charAt(scopy.length()-1) == '\"') scopy = scopy.substring(0, scopy.length()-1);
        return scopy;
    }
}
