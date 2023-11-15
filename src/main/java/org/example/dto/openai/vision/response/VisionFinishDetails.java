package org.example.dto.openai.vision.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisionFinishDetails {

    private String type;
    private String stop;
}
