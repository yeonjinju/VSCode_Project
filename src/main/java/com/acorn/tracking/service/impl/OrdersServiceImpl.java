package com.acorn.tracking.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersMapper ordersMapper;
    private final ProductsMapper productsMapper;

    @Override
    @Scheduled(initialDelay = 5000, fixedRate = 10000)
    @Transactional
    public void autoInsertOrders() throws IOException {
        // Faker 인스턴스 생성: 로케일을 한국어로 설정하여 한국어 데이터를 생성합니다.
        Faker faker = new Faker(new Locale("ko"));
        // 랜덤한 제품 정보를 가져옵니다. 1에서 5 사이의 랜덤 숫자를 인자로 넘깁니다.
        Products.ProductInfo productInfo = productsMapper.getRandomProductsInfo(faker.random().nextInt(1, 5));
        // "static/Orders.json" 파일을 읽기 위한 입력 스트림을 가져옵니다.
        InputStream inputStream = getOrdersJsonInputStream();
        // JSON 파일을 읽어 Orders.JsonOrderInfo 타입의 리스트로 변환합니다.
        List<Orders.OrderInfo> jsonOrderInfos = readOrdersFromJson(inputStream);
        // jsonOrderInfos 리스트 중에서 랜덤하게 한 요소를 선택합니다.
        Orders.OrderInfo orderInfo = jsonOrderInfos.get(faker.random().nextInt(jsonOrderInfos.size()));
        // 제품 ID를 콤마로 분리하여 정수 목록으로 변환합니다.
        List<Integer> productIds = getProductIds(productInfo);
        // 선택된 제품 정보, 수량, 랜덤 JSON 주문 정보를 기반으로 주문 객체를 생성합니다.
        Orders orders = createOrder(productInfo, productIds.size(), orderInfo);
        // 생성된 주문 객체와 제품 ID를 사용해 데이터베이스에 주문을 삽입합니다.
        insertOrders(orders, productIds);
    }

    private List<Integer> getProductIds(Products.ProductInfo productInfo) {
        // 제품 ID 문자열을 콤마로 분리하고 각 부분을 정수로 파싱하여 리스트로 반환합니다.
        return Arrays.stream(productInfo.getProduct_id().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private InputStream getOrdersJsonInputStream() throws FileNotFoundException {
        // 클래스 경로의 "static/Orders.json" 파일에 대한 입력 스트림을 반환합니다.
        // 파일을 찾을 수 없는 경우 예외를 발생시킵니다.
        InputStream inputStream = getClass().getResourceAsStream("/static/Orders.json");
        if (inputStream == null) {
            throw new FileNotFoundException("File not found: Orders.json");
        }
        return inputStream;
    }

    private List<Orders.OrderInfo> readOrdersFromJson(InputStream inputStream) {
        // Gson을 사용해 JSON을 파싱하고 해당 타입의 객체 리스트로 반환합니다.
        return new GsonBuilder()
                .create().fromJson(
                        new InputStreamReader(inputStream),
                        new TypeToken<List<Orders.OrderInfo>>() {
                        }.getType());
    }

    private Orders createOrder(Products.ProductInfo productInfo, int quantity,
            Orders.OrderInfo orderInfo) {
        // 빌더 패턴을 사용해 주문 객체를 생성하고 반환합니다.
        return Orders.builder()
                .quantity_ordered(quantity)
                .total_price(productInfo.getTotal_price())
                .latitude(orderInfo.getLatitude())
                .longitude(orderInfo.getLongitude())
                .customer_name(orderInfo.getCustomer_name())
                .build();
    }

    private void insertOrders(Orders orders, List<Integer> productIds) {
        // 주문을 데이터베이스에 삽입하고, 해당 제품 ID의 재고를 1만큼 감소시킵니다.
        ordersMapper.autoInsertOrders(orders);
        for (Integer product_id : productIds) {
            productsMapper.inventoryReduction(product_id, 1);
        }
    }
}