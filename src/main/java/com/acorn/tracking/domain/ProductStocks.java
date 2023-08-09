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
public class ProductStocks {
    private int product_id;
    private int quantity_available;
    private LocalDateTime last_update;
}
