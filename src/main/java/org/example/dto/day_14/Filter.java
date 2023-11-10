package org.example.dto.day_14;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Filter {
    private List<Must> must;
}
