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
    // 이 메서드는 트랜잭션 내에서 테이블 삭제 및 생성 작업을 수행하여 데이터베이스를 재설정합니다.
    public void resetDatabase() {
        try {
            // 기존 테이블들에 대한 삭제 작업을 수행합니다.
            tableMapper.dropRecalls();
            tableMapper.dropDeliveryLocations();
            tableMapper.dropDeliveries();
            tableMapper.dropOrders();
            tableMapper.dropBaskets();
            tableMapper.dropProducts();
            tableMapper.dropAdmins();
            // 새로운 테이블들에 대한 생성 작업을 수행합니다.
            tableMapper.createAdmins();
            tableMapper.createProducts();
            tableMapper.createBaskets();
            tableMapper.createOrders();
            tableMapper.createDeliveries();
            tableMapper.createDeliveryLocations();
            tableMapper.createRecalls();
            // 모든 작업이 성공하면 트랜잭션이 커밋됩니다.
        } catch (Exception e) {
            // 데이터베이스 재설정 작업 중 예외가 발생한 경우,
            // 트랜잭션은 초기 상태로 롤백되며 예외는 런타임 예외로 다시 던져집니다.
            // 이를 통해 어떤 문제가 발생했는지 추적하고 확인할 수 있습니다.
            throw new RuntimeException("Database reset failed", e);
        }
    }
}
