package org.mansar.fileq.model;

public enum FileType {
    XLSX("xlsx"),
    ZIP("zip"),
    JSON("json"),
    XML("xml"),
    TXT("txt");

    private final String value;

    FileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
