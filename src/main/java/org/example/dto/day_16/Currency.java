package org.example.dto.day_16;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Currency {

    private String table;
    private String currency;
    private String code;
    private Rates[] rates;
}
