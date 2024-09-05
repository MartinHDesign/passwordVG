package com.example.passwordvg.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class SearchAlgorithm {
    public String binarySearchInTextFile(String filePath, String searchWord) {
        final String NOT_FOUND = "not found";
        final String READ_MODE = "r";

        try {
            int rowLength = getRowLength(filePath);

            try (RandomAccessFile file = new RandomAccessFile(filePath, READ_MODE)) {
                long fileSize = file.length();
                long lowIndex = 0L;
                long highIndex = fileSize / rowLength - 1;

                while (lowIndex <= highIndex) {
                    long middleRowIndex = lowIndex + (highIndex - lowIndex) / 2;
                    String middleRowText = readRowAtIndex(file, middleRowIndex, rowLength);
                    System.out.println(middleRowText);
                    System.out.println();
                    String[] parts = middleRowText.split(":");
                    if (parts.length > 0 && parts[0].equals(searchWord)) {
                        return parts[1];
                    }

                    if (middleRowText.compareTo(searchWord) < 0) {
                        lowIndex = middleRowIndex + 1;
                    } else {
                        highIndex = middleRowIndex - 1;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }

        return NOT_FOUND;
    }

    private int getRowLength(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String firstLine = reader.readLine();
            if (firstLine != null) {
                return firstLine.getBytes(StandardCharsets.UTF_8).length + System.lineSeparator().getBytes(StandardCharsets.UTF_8).length;
            }
        }
        return 0;
    }

    private String readRowAtIndex(RandomAccessFile file, long index, int rowLength) throws IOException {
        byte[] rowBytes = new byte[rowLength];
        file.seek(index * rowLength);
        file.readFully(rowBytes);
        return new String(rowBytes, StandardCharsets.UTF_8).trim();
    }

}
