package org.example.dto.day_7;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InPromptTask {

    private int code;
    private String msg;
    private List<String> input;
    private String question;
}
