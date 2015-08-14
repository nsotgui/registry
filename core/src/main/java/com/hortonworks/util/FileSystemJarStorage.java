package com.hortonworks.util;

import com.google.common.io.ByteStreams;

import java.io.*;
import java.nio.file.FileSystems;

/**
 * Created by pshah on 8/7/15.
 * Implementation of JarStorage interface backed by local file system
 */
public class FileSystemJarStorage implements JarStorage {
    //TODO for now we are using hardcoded /tmp directory as the path to
    // store the jars. Change it to retrieve it from config later
    private final String DIRECTORY_PREFIX = "/tmp";

    /**
     *
     * @param inputStream stream to read the jar content from
     * @param name identifier of the jar file to be used later to retrieve
     *             using downloadJar
     * @throws java
    .io.IOException
     */
    public void uploadJar (InputStream inputStream, String name) throws java
            .io.IOException{
        java.nio.file.Path path = FileSystems.getDefault().getPath(this
                        .DIRECTORY_PREFIX, name);
        File file = path.toFile();
        if (!file.exists()) {
            file.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(file);
        ByteStreams.copy(inputStream, outputStream);
        outputStream.close();
    }

    /**
     *
     * @param name identifier of the jar file to be downloaded that was first
     *             passed during uploadJar
     * @return InputStream representing the jar file
     * @throws java.io.IOException
     */
    public InputStream downloadJar (String name) throws java.io.IOException {
        java.nio.file.Path path = FileSystems.getDefault().getPath(this
                .DIRECTORY_PREFIX, name);
        File file = path.toFile();
        return new FileInputStream(file);
    }
}
