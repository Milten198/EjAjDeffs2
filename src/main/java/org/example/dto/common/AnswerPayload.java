package org.example.dto.common;

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
