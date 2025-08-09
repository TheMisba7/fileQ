package org.mansar.fileq.model;

public enum FileType {
    XLSX("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    ZIP("zip", "application/zip", "application/x-zip-compressed"),
    JSON("json", "application/json"),
    XML("xml", "application/xml"),
    TXT("txt", "text/plain");

    private final String extension;
    private final String[] contentTypes;

    FileType(String extension, String... types) {
        this.extension = extension;
        this.contentTypes = types;
    }

    public static FileType fromValue(String value) {
        for (FileType type: FileType.values()) {
            if (type.extension.equals(value))
                return type;
        }
        return null;
    }

    public static FileType getFileType(String contentType) {
        if (contentType == null)
            return null;
        for (FileType fileType: FileType.values()) {
            for (String type: fileType.contentTypes) {
                if (type.equals(contentType))
                    return fileType;
            }
        }
        return null;
    }
    public String getExtension() {
        return extension;
    }
}
