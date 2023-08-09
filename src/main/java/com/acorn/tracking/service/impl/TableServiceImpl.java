package com.acorn.tracking.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acorn.tracking.mapper.TableMapper;
import com.acorn.tracking.service.TableService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableMapper tableMapper;

    @Override
    @Transactional
    public void resetDatabase() {
        // 삭제
        tableMapper.dropRecalls();
        tableMapper.dropDeliveryLocations();
        tableMapper.dropDeliveries();
        tableMapper.dropOrderDetails();
        tableMapper.dropProductsStocks();
        tableMapper.dropOrders();
        tableMapper.dropBaskets();
        tableMapper.dropProducts();
        tableMapper.dropAdmins();
        // 생성
        tableMapper.createAdmins();
        tableMapper.createProducts();
        tableMapper.createBaskets();
        tableMapper.createOrders();
        tableMapper.createProductsStocks();
        tableMapper.createOrderDetails();
        tableMapper.createDeliveries();
        tableMapper.createDeliveryLocations();
        tableMapper.createRecalls();
    }
}
