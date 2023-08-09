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
public class Orders {
    private int order_id;
    private int admin_id;
    private LocalDateTime date_time;
    private int total_price;
    private String delivery_address;
    private int basket_id;
    private Status status;
}