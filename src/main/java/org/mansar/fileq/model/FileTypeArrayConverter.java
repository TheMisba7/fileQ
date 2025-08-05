package org.mansar.fileq.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Converter
public class FileTypeArrayConverter implements AttributeConverter<FileType[], String> {
    @Override
    public String convertToDatabaseColumn(FileType[] attribute) {
        return attribute == null ? null :
                Arrays.stream(attribute)
                        .map(Enum::name)
                        .collect(Collectors.joining(","));
    }

    @Override
    public FileType[] convertToEntityAttribute(String dbData) {
        return dbData == null || dbData.isEmpty() ? new FileType[0] :
                Arrays.stream(dbData.split(","))
                        .map(FileType::valueOf)
                        .toArray(FileType[]::new);
    }
}
