package org.example.dto.openai.vision.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisionChoice {
    private VisionMessage message;
    private VisionFinishDetails finish_details;
    private int index;
}
