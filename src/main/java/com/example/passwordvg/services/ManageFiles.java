package com.example.passwordvg.services;

import com.example.passwordvg.utils.HashAlgorithm;
import com.example.passwordvg.utils.HashUtils;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManageFiles {

    private final Path filePath;

    private final HashUtils hashUtils = new HashUtils();


    public ManageFiles() {
        this.filePath = Paths.get("src/main/java/com/example/passwordvg/utils/Hashes.txt");
    }

    public void createHashTextFile() {
        try {

            Path parentDir = filePath.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
                System.out.println("File created: " + filePath.getFileName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public List<String> readFileAndHashContent(String filePath){
        List<String> HashedText = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                StringBuilder stringBuilder = new StringBuilder();

                String md5 = hashUtils.hashText(line, HashAlgorithm.MD5);
                String sha256 = hashUtils.hashText(line, HashAlgorithm.SHA256);

                stringBuilder.append(line)
                        .append(":")
                        .append(md5)
                        .append(":")
                        .append(sha256);

                String HashedString = stringBuilder.toString();

                HashedText.add(HashedString);

            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
        }

        return HashedText;
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

    public String searchTextFile(String textToSearchFor){
        String result = "Could not find password for given hash";

        try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(filePath)))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] seprateHashes = line.split(":");

                if (listContainsInput(seprateHashes,textToSearchFor)){
                    result = seprateHashes[0];
                    break;
                }

            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
        }
        System.out.println(result);
        return result;
    }
    private boolean listContainsInput(String[] seperatedList, String textToSearchFor){
        return Arrays.stream(seperatedList,1,seperatedList.length).anyMatch(textToSearchFor::equals);
    }
}
