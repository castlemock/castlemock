package com.castlemock.deploy.jetty.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipper {

    private static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 4 * 1024;

    private final Logger log = LoggerFactory.getLogger(Unzipper.class);

    public void unzip(String zipFile, File outputDir, Predicate<String> entryFilter) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            //create output directory is not exists
            if (!outputDir.exists()) {
                outputDir.mkdir();
            }
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                if (entryFilter.test(fileName)) {
                    File newFile = new File(outputDir + File.separator + fileName);
                    //create all non exists folders
                    //else you will hit FileNotFoundException for compressed folder
                    if (ze.isDirectory())
                        newFile.mkdirs();
                    else {
                        new File(newFile.getParent()).mkdirs();
                        try (FileOutputStream fos = new FileOutputStream(newFile)) {
                            copy(zis, fos);
                        } catch (IOException ex) {
                            log.warn("Could not extract zip entry '{}' due to {} ", fileName, ex.getMessage());
                        }
                    }
                }
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
        } catch (IOException ex) {
            log.warn("Could not extract file '{}' to {} ", zipFile, outputDir);
        }
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    public static long copyLarge(InputStream input, OutputStream output)
            throws IOException {
        return copyLarge(input, output, new byte[DEFAULT_BUFFER_SIZE]);
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer)
            throws IOException {
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

}