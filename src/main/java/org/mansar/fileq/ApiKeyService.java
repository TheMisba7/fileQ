package org.mansar.fileq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyService {

    @Value("${app.api-key}")
    private String apiKey;


    public boolean isValid(String apiKey) {
        return this.apiKey.equals(apiKey);
    }
}
