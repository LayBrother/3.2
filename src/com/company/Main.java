package com.company;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String pathZip, List<String> filesToZip) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathZip))) {
            for (String item : filesToZip) {
                try (FileInputStream fis = new FileInputStream(item)) {
                    ZipEntry entry = new ZipEntry(item);
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                } catch (
                        Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            zout.closeEntry();
        } catch (
                Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        GameProgress player1 = new GameProgress(75, 10, 5, 1000.20);
        GameProgress player2 = new GameProgress(50, 7, 6, 1500);
        GameProgress player3 = new GameProgress(10, 5, 2, 700.777);

        saveGame("E://Games//savegames//save1.dat", player1);
        saveGame("E://Games//savegames//save2.dat", player2);
        saveGame("E://Games//savegames//save3.dat", player3);

        List<String> fileGameProgress = Arrays.asList("E://Games//savegames//save1.dat",
                "E://Games//savegames//save2.dat",
                "E://Games//savegames//save3.dat");

        zipFiles("E://Games//savegames//save.zip", fileGameProgress);

        for (String nameFile : fileGameProgress) {
            File delFile = new File(nameFile);
            if (delFile.delete()) {
                System.out.println("Файл " + nameFile + " удален\n");
            }
        }
    }
}