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
public class Recalls {
    private int recall_id;
    private int product_id;
    private boolean is_recalled;
    private LocalDateTime recall_date;
    private LocalDateTime postponed_delivery_date;
}