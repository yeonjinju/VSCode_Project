package com.acorn.tracking.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
    private int order_id;
    private int product_id;
    private int quantity_ordered;
    private int price_each;
}
