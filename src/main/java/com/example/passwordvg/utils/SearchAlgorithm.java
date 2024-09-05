package com.example.passwordvg.utils;

import java.io.IOException;
import java.io.RandomAccessFile;

public class SearchAlgorithm {
    public String binarySearchInTextFile(String filePath, String searchWord) {
        final String NOT_FOUND = "not found";
        final String READ_MODE = "r";

        try (RandomAccessFile file = new RandomAccessFile(filePath, READ_MODE)) {
            long fileSize = file.length();
            int rowLength = getRowLength(file);

            long lowIndex = 0L;
            long highIndex = fileSize / rowLength - 1;

            while (lowIndex <= highIndex) {
                long middleRowIndex = lowIndex + (highIndex - lowIndex) / 2;
                String middleRowText = readRowAtIndex(file, middleRowIndex, rowLength);

                if (middleRowText.equals(searchWord)) {
                    return searchWord;
                }

                if (middleRowText.compareTo(searchWord) < 0) {
                    lowIndex = middleRowIndex + 1;
                } else {
                    highIndex = middleRowIndex - 1;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }

        return NOT_FOUND;
    }

    private int getRowLength(RandomAccessFile file) throws IOException {
        String firstRow = file.readLine();
        return firstRow.getBytes().length + System.lineSeparator().getBytes().length;
    }

    private String readRowAtIndex(RandomAccessFile file, long index, int rowLength) throws IOException {
        file.seek(index * rowLength);
        return file.readLine().split(":")[0];
    }

}
