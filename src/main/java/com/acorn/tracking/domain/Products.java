package com.acorn.tracking.domain;

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
}
