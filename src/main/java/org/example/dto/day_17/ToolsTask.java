package org.example.dto.day_17;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ToolsTask {

    private int code;
    private String msg;
    private String question;
}
