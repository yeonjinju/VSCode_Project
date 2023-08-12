package com.acorn.tracking.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Deliveries {
    private int delivery_id;
    private int basket_id;
    private int order_id;
    private Status status;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime delivery_time;
}