package org.example.dto.day_1.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerPayload {

    private Object answer;

    public AnswerPayload(Object answer) {
        this.answer = answer;
    }
}
