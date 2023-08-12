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
public class Orders {
    private int order_id;
    private LocalDateTime date_time;
    private int quantity_ordered;
    private int total_price;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String customer_name;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderInfo {
        private BigDecimal latitude;
        private BigDecimal longitude;
        private String customer_name;
    }
}