package com.acorn.tracking.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.acorn.tracking.domain.Orders;
import com.acorn.tracking.domain.Products;
import com.acorn.tracking.mapper.OrdersMapper;
import com.acorn.tracking.mapper.ProductsMapper;
import com.acorn.tracking.service.OrdersService;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersMapper ordersMapper;
    private final ProductsMapper productsMapper;

    @Override
    @Scheduled(initialDelay = 5000, fixedRate = 10000)
    public void autoInsertOrders() {
        // 가짜 데이터를 생성하기 위해 한국 로캘을 사용하여 Faker 인스턴스 생성
        Faker faker = new Faker(new Locale("ko"));
        // productsMapper를 사용하여 랜덤한 제품 정보를 가져옴
        Products.ProductInfo productInfo = productsMapper
                .getRandomProductsInfo(faker.random().nextInt(1, 5));
        // productInfo에서 쉼표로 구분된 제품 ID들을 문자열로 가져옴
        // 쉼표로 구분된 문자열을 정수 리스트로 변환
        List<Integer> productIds = Arrays
                .stream(productInfo.getProduct_id().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        // 생성된 가짜 데이터로 Orders 객체 생성
        Orders orders = Orders
                .builder()
                .admin_id(1) // 관리자 ID 설정
                .quantity_ordered(productIds.size())
                .total_price(productInfo.getTotal_price()) // productInfo에서 총 가격 설정
                .customer_name(faker.name().fullName()) // 가짜 고객 이름 생성
                .delivery_address(faker.address().fullAddress()) // 가짜 배송 주소 생성
                .build();
        // ordersMapper를 사용하여 주문 데이터베이스에 삽입
        ordersMapper.autoInsertOrders(orders);
        // 각 제품의 재고를 productsMapper를 사용하여 감소
        for (Integer product_id : productIds) {
            productsMapper.inventoryReduction(product_id, 1);
        }
    }
}
