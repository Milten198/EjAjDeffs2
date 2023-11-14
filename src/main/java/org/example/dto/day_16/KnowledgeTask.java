package org.example.dto.day_16;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KnowledgeTask {

    private int code;
    private String message;
    private String question;

}