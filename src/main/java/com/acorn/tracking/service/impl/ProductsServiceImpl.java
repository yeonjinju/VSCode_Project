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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import lombok.RequiredArgsConstructor;

@Service // 서비스 클래스라는걸 정의(인식)
@RequiredArgsConstructor // final (auto안써도됌)
public class ProductsServiceImpl implements ProductsService {

    private final ProductsMapper productsMapper;

    @Override
    public void loadProductsFromFile() throws IOException { // 이곳에서 입셉션을 호출한곳에 던지는 (입센션에서 처리하는 정의 단위)
        InputStream inputStream = getProductsJsonInputStream(); // 내부에 담긴 json으로 된 파일을 읽어와서 인풋스트림에 넣음
        List<Products> products = readProductsFromJson(inputStream);
        insertProductsIntoDatabase(products);
    }

    private InputStream getProductsJsonInputStream() throws FileNotFoundException {
        // "static" 디렉토리에 있는 "Products.json" 파일의 입력 스트림을 가져옵니다.
        InputStream inputStream = getClass().getResourceAsStream("/static/Products.json");
        // 입력 스트림이 null인지 확인하여 파일을 찾을 수 없는 경우 예외를 던집니다.
        if (inputStream == null) {
            throw new FileNotFoundException("File not found: Products.json");
        }
        return inputStream;
    }

    private List<Products> readProductsFromJson(InputStream inputStream) {
        // Gson 인스턴스를 생성하고 LocalDateTime에 대한 사용자 지정 타입 어댑터를 등록합니다.
        // 리더를 사용하여 입력 스트림의 내용을 읽습니다.
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create().fromJson(
                        new InputStreamReader(inputStream),
                        new TypeToken<List<Products>>() {}.getType());
    }

    private void insertProductsIntoDatabase(List<Products> products) {
        // 제품 목록을 반복하며 각 제품을 매퍼를 통해 데이터 저장소에 삽입합니다.
        for (Products product : products) {
            productsMapper.autoInsertProducts(product);
        }
    }
}