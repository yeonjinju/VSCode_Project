package com.acorn.tracking.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TableMapper {
    // 관리자
    void dropAdmins();
    void createAdmins();
    // 상품
    void dropProducts();
    void createProducts();
    // 상품 재고
    void dropProductsStocks();
    void createProductsStocks();
    // 바구니
    void dropBaskets();
    void createBaskets();
    // 주문
    void dropOrders();
    void createOrders();
    // 주문 상세
    void dropOrderDetails();
    void createOrderDetails();
    // 배송
    void dropDeliveries();
    void createDeliveries();
    // 배송 위치
    void dropDeliveryLocations();
    void createDeliveryLocations();
    // 리콜
    void dropRecalls();
    void createRecalls();
}