package com.acorn.tracking.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.stereotype.Service;

import com.acorn.tracking.domain.Products;
import com.acorn.tracking.mapper.ProductsMapper;
import com.acorn.tracking.service.ProductsService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {

    private final ProductsMapper productsMapper;

    @Override
    public void loadProductsFromFile() throws IOException{
        Gson gson = new Gson();
        InputStream inputStream = getClass().getResourceAsStream("/static/Products.json");
        if (inputStream == null) {
            throw new FileNotFoundException("File not found: Products.json");
        }
        InputStreamReader reader = new InputStreamReader(inputStream);
        List<Products> products = gson.fromJson(reader, new TypeToken<List<Products>>() {
        }.getType());
        for (Products product : products) {
            productsMapper.insertProducts(product);
        }
    }
}
