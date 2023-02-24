package org.bih.eos.fileloader;

import org.bih.eos.exceptions.UnprocessableEntityException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileLoader {

    public static List<File> loadFiles(String resourceFolder, String exceptionMessage) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String osName = System.getProperty("os.name");
        if(osName.toLowerCase().contains("windows")){
            return load(Objects.requireNonNull(classLoader.getResource(resourceFolder)).getPath().substring(1), exceptionMessage);
        }else{
            return load(Objects.requireNonNull(classLoader.getResource(resourceFolder)).getPath(), exceptionMessage);
        }
    }

    private static List<File> load(String substring, String exceptionMessage) {
        try {
            return Files.walk(Paths.get(substring))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UnprocessableEntityException(exceptionMessage);
        }
    }


}
