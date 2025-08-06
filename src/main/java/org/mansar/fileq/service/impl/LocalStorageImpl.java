package org.mansar.fileq.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.mansar.fileq.service.IStorageService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Service
@Slf4j
public class LocalStorageImpl implements IStorageService {
    @Override
    public String upload(String des, byte[] content, String contentType) {
        Path folder = getFolder(des);
        String name = UUID.randomUUID().toString();
        Path filepath = folder.resolve(name + contentType);

        try {
            Path tempFile = Files.createTempFile(
                    filepath.getParent(),
                    "tmp_" + name,
                    ".upload"
            );

            Files.write(tempFile, content, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

            Files.move(tempFile, filepath, StandardCopyOption.ATOMIC_MOVE);
            try {
                Files.deleteIfExists(tempFile);
            } catch (IOException cleanup) {
                log.warn("Failed to cleanup temporary file: {}", tempFile, cleanup);
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }

        return name + contentType;
    }

    @Override
    public File download(String path) {
        Path folder = getFolder(null);
        return folder.resolve(path).toFile();
    }


    private Path getFolder(String des) {
        Path path;
        if (des != null && !des.isBlank())
            path = Paths.get(System.getProperty("user.home"), "fileQ", "uploads", des);
        else
            path = Paths.get(System.getProperty("user.home"), "fileQ", "uploads");
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return path;
    }
}
