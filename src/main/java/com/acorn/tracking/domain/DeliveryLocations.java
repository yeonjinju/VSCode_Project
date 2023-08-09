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
public class DeliveryLocations {
    private int delivery_id;
    private LocalDateTime time_stamp;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
