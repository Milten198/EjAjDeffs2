package org.example.dto.day_19;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnApiRequest {

    @NotNull
    @NotEmpty(message = "Please, ask me anything but not nothing!")
    @NotBlank(message = "Question cannot be empty")
    @Size(max = 500, message = "Question must be less than 500 characters")
    private String question;
}
