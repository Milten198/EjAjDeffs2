package org.example.dto.openai.vision.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisionUsage {

    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;
}
