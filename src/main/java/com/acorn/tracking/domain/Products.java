package com.acorn.tracking.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Products {
    private int product_id;
    private String product_name;
    private int price;
    private Category category;
    private int quantity_available;
    private LocalDateTime last_update;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductInfo {
        private String product_id;
        private int total_price;
    }
}
