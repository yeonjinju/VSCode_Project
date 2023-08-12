package com.acorn.tracking.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.acorn.tracking.domain.Admins;
import com.acorn.tracking.mapper.AdminsMapper;
import com.acorn.tracking.service.AdminsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminsServiceImpl implements AdminsService {

    private final AdminsMapper adminsMapper;

    @Override
    public void insertAdmins() throws IOException {
        Admins admins = Admins.builder()
                .name("admin")
                .email("admin@gmail.com")
                .password("1234")
                .build();
        adminsMapper.autoInsertAdmins(admins);
    }
}