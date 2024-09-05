package com.example.passwordvg.services;

import com.example.passwordvg.utils.HashAlgorithm;
import com.example.passwordvg.utils.HashUtils;
import com.example.passwordvg.utils.SearchAlgorithm;
import lombok.NoArgsConstructor;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@NoArgsConstructor
public class ManageFiles {

    private final String sha256FilePath= "src/main/java/com/example/passwordvg/utils/sha256Hashes.txt";
    private final String md5FilePath= "src/main/java/com/example/passwordvg/utils/md5Hashes.txt";
    private static final int TOTAL_FILLER_LENGTH = 100;

    private final HashUtils hashUtils = new HashUtils();

    public void createMD5andSHA256TextFiles(){

        createHashTextFile(Path.of(sha256FilePath));

        String leakedPasswordsPath = "src/main/java/com/example/passwordvg/utils/100k-most-used-passwords-NCSC.txt";

        if (isFileEmpty(sha256FilePath)){
            List<String> sha256 = sortedListOfHashesReadFromFile(HashAlgorithm.SHA256, leakedPasswordsPath);
            writeToFile(sha256, sha256FilePath);
        }

        createHashTextFile(Path.of(md5FilePath));

        if (isFileEmpty(md5FilePath)){
            List<String> md5 = sortedListOfHashesReadFromFile(HashAlgorithm.MD5, leakedPasswordsPath);
            writeToFile(md5, md5FilePath);
        }

    }



    private void createHashTextFile(Path path) {
        try {

            Path parentDir = path.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            if (Files.notExists(path)) {
                Files.createFile(path);
                System.out.println("File created: " + path.getFileName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    private List<String> sortedListOfHashesReadFromFile(HashAlgorithm HashAlgorithms, String filePath) {
        List<String> HashedText = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (isValidUtf8(line)) {
                    String hashedLine = hashUtils.hashText(line, HashAlgorithms);

                    String baseString = hashedLine + ":" + line + ":";

                    byte[] byteArray = baseString.getBytes(StandardCharsets.UTF_8);
                    int baseLength = byteArray.length;

                    int fillerLength = TOTAL_FILLER_LENGTH - baseLength;
                    if (fillerLength < 0) fillerLength = 0;

                    String filler = createFiller(fillerLength);

                    String finalString = baseString + filler;

                    byte[] finalBytes = finalString.getBytes(StandardCharsets.UTF_8);
                    boolean is100Bytes = finalBytes.length == TOTAL_FILLER_LENGTH;

                    if (is100Bytes){
                        HashedText.add(finalString);
                    }

                }
            }
            Collections.sort(HashedText);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
        }
        return HashedText;
    }

    private boolean isValidUtf8(String line) {
        try {
            byte[] bytes = line.getBytes(StandardCharsets.UTF_8);
            String test = new String(bytes, StandardCharsets.UTF_8);
            return line.equals(test);
        } catch (Exception e) {
            return false;
        }
    }

    private static String createFiller(int length) {
        return new String(new char[length]).replace('\0', '!');
    }


    public void writeToFile(List<String> HashedText, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (String line:HashedText) {
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public boolean isFileEmpty(String filePath) {
        Path path = Paths.get(filePath);
        try {

            if (Files.exists(path)) {

                long fileSize = Files.size(path);
                return fileSize == 0;
            }
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
        return false;
    }

    public String searchTextFile(String hash) {
        SearchAlgorithm searchAlgorithm = new SearchAlgorithm();
        String result;

        if (hash.length() == 64){
            result = searchAlgorithm.binarySearchInTextFile(sha256FilePath,hash);
        } else {
            result = searchAlgorithm.binarySearchInTextFile(md5FilePath,hash);
        }
        return result;
    }
}
