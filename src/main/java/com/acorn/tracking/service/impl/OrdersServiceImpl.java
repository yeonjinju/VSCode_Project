package com.acorn.tracking.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Scheduled(initialDelay = 5000, fixedRate = 10000) // 어플리케이션 시작되면 5초뒤에 자동실행 10초마다 재실행된다.
    @Transactional // 메소드안에 메퍼를 두개 끌고온다. 안붙혀도되지만, 붙히면 이메소드안에서 실행하면 둘중 하나만 오류떠도 롤백이뜬다. 둘다성공하면 커밋.
    public void autoInsertOrders() {
        Faker faker = createFakerInstance();
        Products.ProductInfo productInfo = getRandomProductInfo(faker);
        List<Integer> productIds = getProductIds(productInfo);
        Orders orders = createOrder(productInfo, productIds.size());
        insertOrders(orders, productIds);
    }

    private Faker createFakerInstance() {
        // 가짜 데이터를 생성하기 위해 한국 로캘을 사용하여 Faker 인스턴스 생성
        return new Faker(new Locale("ko"));
    }

    private Products.ProductInfo getRandomProductInfo(Faker faker) {
        // productsMapper를 사용하여 랜덤한 제품 정보를 가져옴
        return productsMapper.getRandomProductsInfo(faker.random().nextInt(1, 5));
    }

    private List<Integer> getProductIds(Products.ProductInfo productInfo) {
        // productInfo에서 쉼표로 구분된 제품 ID들을 문자열로 가져옴
        // 쉼표로 구분된 문자열을 정수 리스트로 변환
        return Arrays.stream(productInfo.getProduct_id().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private Orders createOrder(Products.ProductInfo productInfo, int quantity) {
        // 생성된 가짜 데이터로 Orders 객체 생성
        return Orders.builder() // setter=builder
                .admin_id(1) // 관리자 ID 설정
                .quantity_ordered(quantity) // 상품 총 개수
                .total_price(productInfo.getTotal_price()) // productInfo에서 총 가격 설정
                .build();
    }

    private void insertOrders(Orders orders, List<Integer> productIds) {
        // ordersMapper를 사용하여 주문 데이터베이스에 삽입
        ordersMapper.autoInsertOrders(orders);
        // 각 제품의 재고를 productsMapper를 사용하여 감소
        for (Integer product_id : productIds) {
            productsMapper.inventoryReduction(product_id, 1);
        }
    }
}