package org.raf.specification;

import org.raf.exceptions.BrokenConfigurationException;
import org.raf.exceptions.FileNotFoundCustomException;
import org.raf.exceptions.IllegalDestinationException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

interface IStorageManager {
    void createStorage() throws Exception;

    /**
     * Creates storage on the given parent path with the given name. This method uses the default configuration,
     * where byteSize = 10000, forbiddenExtensions = "exe" and globalFileCount = 10.
     * @param path Type String.
     * @param name Type String.
     * @throws Exception
     */
    void createStorage(String path, String name) throws Exception;

    /**
     * Creates storage with the given name on a default path. This method uses the default configuration,
     * where byteSize = 10000, forbiddenExtensions = "exe" and globalFileCount = 10.
     * @param name Type String.
     * @throws Exception
     */
    void createStorage(String name) throws Exception;

    /**
     * Creates storage from the given configuration.
     * @param c Type Configuration. Possible parameters for configuration are int byteSize, List<String> forbiddenExtensions, int globalFileCount, Map<String, Integer> mapFileCount and String configPath
     * @throws Exception
     */
    void createStorage(Configuration c) throws Exception;

    /**
     * reates storage from the given configuration with the given name.
     * @param c Type Configuration. Possible parameters for configuration are int byteSize, List<String> forbiddenExtensions, int globalFileCount, Map<String, Integer> mapFileCount and String configPath
     * @param name Type String.
     * @throws Exception
     */
    void createStorage(Configuration c, String name) throws Exception;

    /**
     * Creates storage on the given parent path with the given name using the given configuration.
     * @param c ossible parameters for configuration are int byteSize, List<String> forbiddenExtensions, int globalFileCount, Map<String, Integer> mapFileCount and String configPath
     * @param rootPath Type String. Path of the parent of the storage we want to create.
     * @param name Type String.
     * @throws Exception
     */
    void createStorage(Configuration c, String rootPath, String name) throws Exception;

    /**
     *  Loads storage located on the given path.
     * @param path Type String.
     * @throws IOException
     * @throws BrokenConfigurationException
     */
    void loadStorage(String path) throws IOException, BrokenConfigurationException;

    void saveStorage() throws IOException;

    /**
     * Creates a directory with the default name "dir" on the given parent path.
     * @param rootPath Type String
     * @throws Exception
     */
    void createDirectory(String rootPath) throws Exception;

    /**
     * Creates a directory with the default name "dir" on the given parent path with a specified file number limit.
     * @param rootPath Type String.
     * @param fileCount Type int. Specifies the maximum number of files the created directory can contain.
     * @throws Exception
     */
    void createDirectory(String rootPath, int fileCount) throws Exception;

    /**
     * Creates directories on the given path with names specified using the given pattern.
     * @param rootPath Type String.
     * @param pattern Type String. Pattern that specifies names of created files.
     * @throws Exception
     */
    void createDirectory(String rootPath, String pattern) throws Exception;

    /**
     * Creates directories on the given path with names specified using the given pattern. The created directories have a defined limit of files.
     * @param rootPath Type String.
     * @param pattern Type String. Pattern that specifies names of created files.
     * @param maxFileCount Type int. Specifies the maximum number of files the created directory can contain.
     * @throws Exception
     */
    void createDirectory(String rootPath, String pattern, int maxFileCount) throws Exception;

    /**
     * Adds given files to the given destination.
     * @param files Type List of SpecFiles. List that should be added to the given destination.
     * @param destination Type String. Destination the given files should be added to.
     * @throws Exception
     */
    void addFiles(List<SpecFile> files, String destination) throws Exception;

    /**
     * Removes file with the given path.
     * @param path Type String. Path of the file that should be removed.
     * @throws Exception
     */
    void removeFile(String path) throws Exception;

    /**
     * Removes the given file.
     * @param file Type SpecFile. File that should be removed.
     * @throws Exception
     */
    void removeFile(SpecFile file) throws Exception;

    /**
     * Removes files with the given paths.
     * @param files Type String[]. Array of paths.
     * @throws Exception
     */
    void removeFiles(String[] files) throws Exception;

    /**
     * Removes given files.
     * @param files Type SpecFile[]. Files that should be removed.
     * @throws Exception
     */
    void removeFiles(SpecFile[] files) throws Exception;

    /**
     * Moves given file from its source location to the given destination file.
     * @param file Type SpecFile. File that should be moved.
     * @param destination Type SpecFile. Destination file.
     * @throws BrokenConfigurationException
     * @throws IllegalDestinationException
     * @throws FileNotFoundCustomException
     */
    void moveFile(SpecFile file, SpecFile destination) throws BrokenConfigurationException, IllegalDestinationException, FileNotFoundCustomException;

    /**
     * Moves given file from its source location to the given destination.
     * @param file Type SpecFile. File that should be moved.
     * @param destinationPath Type String. Path the given file should be moved to.
     * @throws Exception
     */
    void moveFile(SpecFile file, String destinationPath) throws Exception;

    /**
     * Moves file with the given source path to the given destination path.
     * @param filePath Type String. Path of the original file.
     * @param destinationPath Type String. Path the given file should be moved to.
     * @throws Exception
     */
    void moveFile(String filePath, String destinationPath) throws Exception;

    /**
     * Moves files with the given source paths to the given destination path.
     * @param files Type List of Strings. List of paths of files that should be moved.
     * @param destinationPath Type String. Path the given files should be moved to.
     * @throws Exception
     */
    void moveFiles(List<String> files, String destinationPath) throws Exception;

    /**
     * Copies the given file to the specified destination file.
     * @param file Type SpecFile. File that should be copied.
     * @param destination Type SpecFile. Destination file.
     * @throws BrokenConfigurationException
     * @throws IllegalDestinationException
     * @throws FileNotFoundCustomException
     */
    void copyFile(SpecFile file, SpecFile destination) throws BrokenConfigurationException, IllegalDestinationException, FileNotFoundCustomException;

    /**
     * Copies given file to the given destination path.
     * @param file Type SpecFile. File that should be moved.
     * @param destinationPath Type String. Destination path.
     * @throws Exception
     */
    void copyFile(SpecFile file, String destinationPath) throws Exception;

    /**
     * Copies file from the given source path to the given destination path.
     * @param filePath Type String. Source file path.
     * @param destinationPath Type String. Destination path.
     * @throws Exception
     */
    void copyFile(String filePath, String destinationPath) throws Exception;

    /**
     * Copies files with the given source paths to the given destination path.
     * @param files Type List of Strings. Source paths of the files that should be copied.
     * @param destinationPath Type String. Destination path.
     * @throws Exception
     */
    void copyFiles(List<String> files, String destinationPath) throws Exception;

    /**
     * Downloads given file from storage to the specified destination file
     * @param path Type SpecFile. File that should be downloaded.
     * @param destinationPath Type SpecFile. Destination file.
     * @throws Exception
     */
    void download(SpecFile path, SpecFile destinationPath) throws Exception;

    /**
     * Downloads given file from storage to the specified destination path.
     * @param path Type SpecFile. File that should be downloaded.
     * @param destinationPath Type String. Destination path.
     * @throws Exception
     */
    void download(SpecFile path, String destinationPath) throws Exception;

    /**
     * Downloads file from the given source path to the given destination path.
     * @param path Type String. Path of the string that should be downloaded.
     * @param destinationPath Type String. Destination path.
     * @throws Exception
     */
    void download(String path, String destinationPath) throws Exception;

    /**
     * Renames given file to the new given name.
     * @param file Type SpecFile. File that should be renamed.
     * @param newName Type String. New name.
     * @return Returns whether the method was successful.
     * @throws Exception
     */
    boolean rename(SpecFile file, String newName)throws Exception;

    /**
     * Renames file on the given path to the new given name.
     * @param sourcePath Type String. Path of the file that should be renamed.
     * @param newName Type String. New name.
     * @return Returns whether the method was successful.
     * @throws Exception
     */
    boolean rename(String sourcePath, String newName) throws Exception;

    /**
     * Returns files from the given parent path.
     * @param directoryPath Type String. Parent path of the files that wil be returned.
     * @return Type List of SpecFiles.
     * @throws Exception
     */
    List<SpecFile> returnDirectoryFiles(String directoryPath) throws Exception;

    /**
     * Returns files from the given parent path that contain given substring in their file name.
     * @param directoryPath Type String. Path of the parent directory.
     * @param substring Type String. Substring that is searched in the names of files on the parent path
     * @return Type List of SpecFiles.
     * @throws Exception
     */
    List<SpecFile> returnDirectoryFiles(String directoryPath, String substring) throws Exception;

    /**
     * Returns files from the given parent path that end with one of the given extensions.
     * @param directoryPath Type String. Path of the parent directory.
     * @param extensions Type List of Strings. Extensions that are required.
     * @return Type List of SpecFiles.
     * @throws Exception
     */
    List<SpecFile> returnDirectoryFiles(String directoryPath, List<String> extensions) throws Exception;

    /**
     * Returns files from the given parent path that end with one of the given extensions and contain given substring.
     * @param directoryPath Type String. Path of the parent directory.
     * @param extensions Type List of Strings. List of required extensions.
     * @param substring Type String. Required substring.
     * @return Type List of SpecFiles.
     * @throws Exception
     */
    List<SpecFile> returnDirectoryFiles(String directoryPath, List<String> extensions, String substring) throws Exception;

    /**
     * Returns files from subdirectories from given parent directory
     * @param directoryPath Type String. Parent directory path.
     * @return Type List of SpecFiles.
     * @throws Exception
     */
    List<SpecFile> returnSubdirectoryFile(String directoryPath) throws Exception;

    /**
     * Returns files, that contain given substring, from subdirectories from given parent directory.
     * @param directoryPath Type String. Parent directory path.
     * @param substring Type String. Required substring.
     * @return Type List of SpecFiles
     * @throws Exception
     */
    List<SpecFile> returnSubdirectoryFile(String directoryPath, String substring) throws Exception;

    /**
     * Returns files, that end with one of the given extensions. from subdirectories from given parent directory.
     * @param directoryPath Type String. Parent directory path.
     * @param extension Type List of Strings. Required extensions.
     * @return Type List of SpecFiles.
     * @throws Exception
     */
    List<SpecFile> returnSubdirectoryFile(String directoryPath, List<String> extension) throws Exception;

    /**
     * Returns files, that end with one of the given extensions and contain given substring, from subdirectories from given directory.
     * @param directoryPath Type String. Parent directory path.
     * @param extension Type List of Strings. Required extensions.
     * @param substring Type String. Required substring.
     * @return
     * @throws Exception
     */
    List<SpecFile> returnSubdirectoryFile(String directoryPath, List<String> extension, String substring) throws Exception;

    /**
     * Returns files from subdirectories and those subdirectories form given directory.
     * @param directoryPath Type String. Parent directory path.
     * @return Type List of SpecFiles.
     * @throws Exception
     */
    List<SpecFile> returnAllDirectoryFiles(String directoryPath) throws Exception;

    /**
     * Returns files from subdirectories and those subdirectories form given directory. Only files that contain given substring will be returned.
     * @param directoryPath Type String. Parent directory path.
     * @param substring Type String. Required substring.
     * @return Type List of SpecFiles.
     * @throws Exception
     */
    List<SpecFile> returnAllDirectoryFiles(String directoryPath, String substring) throws Exception;

    /**
     * Returns files from subdirectories and those subdirectories form given directory. Only files that end with one of the given extensions will be returned.
     * @param directoryPath Type String. Parent directory path.
     * @param extension Type List of Strings. Required extensions.
     * @return Type List of SpecFiles.
     * @throws Exception
     */
    List<SpecFile> returnAllDirectoryFiles(String directoryPath, List<String> extension) throws Exception;

    /**
     * Returns files from subdirectories and those subdirectories form given directory. Only files that end with one of the given extensions and contain given substring will be returned.
     * @param directoryPath Type String. Parent directory path.
     * @param extension Type List of Strings. Required Strings.
     * @param substring Type String. Required Substring.
     * @return Type List of SpecFiles.
     * @throws Exception
     */
    List<SpecFile> returnAllDirectoryFiles(String directoryPath, List<String> extension, String substring) throws Exception;

    /**
     * Returns true if directory on the given path contains files with the given names. Only returns true if all files are found in given directory, else false is returned.
     * @param directoryPath Type String. Parent directory path.
     * @param fileName Type List of Strings. List of file names that are searched for in the given parent directory.
     * @return Type boolean.
     * @throws Exception
     */
    boolean containsFile(String directoryPath, List<String> fileName) throws Exception;

    /**
     * Searches the storage in depth for a file with the given name.
     * @param fileName Type String. Name of the file that is searched for.
     * @return Type String. returns path of the found file.
     * @throws Exception
     */
    String returnFileLocation(String fileName) throws Exception;

    /**
     * Reruns given list of files in a different sorted order that is defined using sortingCriteria. Files can be sorted by name, date of creation and list date of modification, both ascending and descending.
     * @param sortingCriteria Type SortingCriteria. Criteria that is defined using variables Boolean name, Boolean dateCreated, Boolean dateModified and  Enum ESortingOrder sortingOrder that has ASCENDING and DESCENDING options.
     * @param files Type List of SpecFiles. Sorted list of given files.
     * @return Type List of SpecFiles.
     * @throws Exception
     */
    List<SpecFile> sortFiles(SortingCriteria sortingCriteria, List<SpecFile> files) throws Exception;

    /**
     * Returns files form given parent directory that hav been last modified between given LocalDateTimes.
     * @param startDate Type LocalDateTime. Start LocalDateTime of the chosen period.
     * @param endDate Type LocalDAteTime. End LocalDate time of the chosen period.
     * @param directoryPath Type String. Parent directory path.
     * @return Type List of SpecFiles.
     * @throws Exception
     */
    List<SpecFile> returnFilesModifiedDuringPeriod(LocalDateTime startDate, LocalDateTime endDate, String directoryPath) throws Exception;

    /**
     * Returns files form given parent directory that hav been created between given LocalDateTimes.
     * @param startDate Type LocalDateTime. Start LocalDateTime of the chosen period.
     * @param endDate Type LocalDAteTime. End LocalDate time of the chosen period.
     * @param directoryPath Type String. Parent directory path.
     * @return Type List of SpecFiles.
     * @throws Exception
     */
    List<SpecFile> returnFilesCreatedDuringPeriod(LocalDateTime startDate, LocalDateTime endDate, String directoryPath) throws Exception;

    //Wrapper methods

    /**
     * Returns only names of files from the given list.
     * @param files Type List of Strings.
     * @return
     */
    List<String> returnFileName(List<SpecFile> files);
    /**
     * Returns only paths of files from the given list.
     * @param files Type List of Strings.
     * @return
     */
    List<String> returnFilePath(List<SpecFile> files);
    /**
     * Returns only dates of creation of files from the given list.
     * @param files Type List of LocalDateTimes.
     * @return
     */
    List<LocalDateTime> returnDateCreated(List<SpecFile> files);
    /**
     * Returns only last dates of modification of files from the given list.
     * @param files Type List of LocalDateTimes.
     * @return
     */
    List<LocalDateTime> returnDateMModified(List<SpecFile> files);
    /**
     * Returns only booleans whether files form the given list are directories.
     * @param files Type List of Booleans.
     * @return
     */
    List<Boolean> returnIfDepository(List<SpecFile> files);
}
