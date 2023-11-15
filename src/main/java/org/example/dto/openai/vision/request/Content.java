package org.example.dto.openai.vision.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Content {

    private String type;
    private String text;
    private ImageUrl image_url;
}
