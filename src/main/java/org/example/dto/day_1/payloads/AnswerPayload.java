package org.example.dto.day_1.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerPayload {

    private String answer;

    public AnswerPayload(String answer) {
        this.answer = answer;
    }
}
