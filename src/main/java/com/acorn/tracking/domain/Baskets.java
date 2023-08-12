package com.acorn.tracking.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Baskets {
    private int basket_id;
    private int product_id;
    private BigDecimal current_temperature;
    private BigDecimal current_humidity;
    private BigDecimal freshness_level;
}