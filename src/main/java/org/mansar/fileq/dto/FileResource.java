package org.mansar.fileq.dto;

import org.springframework.core.io.Resource;

public record FileResource(Resource resource, String fileName) {
}
