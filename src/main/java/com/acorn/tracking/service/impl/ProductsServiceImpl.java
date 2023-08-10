package com.acorn.tracking.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.acorn.tracking.adapter.LocalDateTimeAdapter;
import com.acorn.tracking.domain.Products;
import com.acorn.tracking.mapper.ProductsMapper;
import com.acorn.tracking.service.ProductsService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {

    private final ProductsMapper productsMapper;

    @Override
    public void loadProductsFromFile() throws IOException {
        // LocalDateTime에 대한 사용자 지정 타입 어댑터를 등록하여 Gson 인스턴스를 생성합니다.
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        // "static" 디렉토리에 있는 "Products.json" 파일의 입력 스트림을 가져옵니다.
        InputStream inputStream = getClass().getResourceAsStream("/static/Products.json");
        // 입력 스트림이 null인지 확인하여 파일을 찾을 수 없는 경우 예외를 던집니다.
        if (inputStream == null) {
            throw new FileNotFoundException("File not found: Products.json");
        }
        // 리더를 생성하여 입력 스트림의 내용을 읽습니다.
        InputStreamReader reader = new InputStreamReader(inputStream);
        // Gson을 사용하여 JSON 내용을 Products 객체의 리스트로 역직렬화합니다.
        List<Products> products = gson.fromJson(reader, new TypeToken<List<Products>>() {
        }.getType());
        // 제품 목록을 반복하며 각 제품을 매퍼를 통해 데이터 저장소에 삽입합니다.
        for (Products product : products) {
            productsMapper.autoInsertProducts(product);
        }
    }
}
