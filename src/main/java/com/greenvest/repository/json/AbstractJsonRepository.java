package com.greenvest.repository.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenvest.common.JsonFileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

abstract class AbstractJsonRepository<T> {

    private final ObjectMapper objectMapper;
    private final Path filePath;
    private final Class<T[]> arrayType;

    protected AbstractJsonRepository(JsonFileUtil fileUtil, String fileName, Class<T[]> arrayType) {
        fileUtil.ensureDataDirectoryExists();
        this.filePath = fileUtil.resolve(fileName);
        this.arrayType = arrayType;
        this.objectMapper = new ObjectMapper().findAndRegisterModules();
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                Files.writeString(filePath, "[]");
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to initialise data file: " + filePath, e);
        }
    }

    protected synchronized List<T> readAllInternal() {
        try {
            if (!Files.exists(filePath)) {
                return new ArrayList<>();
            }
            String json = Files.readString(filePath).trim();
            if (json.isEmpty()) {
                return new ArrayList<>();
            }
            T[] array = objectMapper.readValue(filePath.toFile(), arrayType);
            List<T> list = new ArrayList<>();
            if (array != null) {
                for (T element : array) {
                    list.add(element);
                }
            }
            return list;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read JSON file: " + filePath, e);
        }
    }

    protected synchronized void writeAllInternal(List<T> elements) {
        try {
            String json = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(elements);
            Files.writeString(filePath, json);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write JSON file: " + filePath, e);
        }
    }
}
