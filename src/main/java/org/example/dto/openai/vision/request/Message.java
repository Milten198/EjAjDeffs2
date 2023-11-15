package org.example.dto.openai.vision.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Message {

    private String role;
    private List<Content> content;
}
