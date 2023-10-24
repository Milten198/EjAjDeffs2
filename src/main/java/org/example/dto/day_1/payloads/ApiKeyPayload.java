package org.example.dto.day_1.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiKeyPayload {

    private String apikey;

    public ApiKeyPayload(String apikey) {
        this.apikey = apikey;
    }

}
