package com.example.myshopper.repository;

import com.example.myshopper.model.Product;
import com.example.myshopper.model.enums.Unit;
import com.example.myshopper.repository.model.ProductStateEntity;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private static final String PRODUCT_STATE_ENTITY_LIST_BY_STATE_ID = "getProductStateEntityListByStateID";
    private static final String PRODUCT_LIST_BY_IDS = "getProductListByIDs";

    private final SqlSession sqlSession;

    @Autowired
    public ProductRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<ProductStateEntity> getProductStateEntitiesByStateID(int fridgeStateID) {
        List<ProductStateEntity> mockedList = new ArrayList<>();
        mockedList.add(new ProductStateEntity(1, 1, 4));
        mockedList.add(new ProductStateEntity(2, 1, 5));
        mockedList.add(new ProductStateEntity(3, 1, 6));
        return mockedList;
//        return sqlSession.selectList(PRODUCT_STATE_ENTITY_LIST_BY_STATE_ID, fridgeStateID);
    }

    public List<Product> getProductsByIds(List<Integer> productIDs) {
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1, "mockPomidor", Unit.KILOGRAM, BigDecimal.valueOf(5.33)));
        mockProducts.add(new Product(2, "mockHerbatniki", Unit.PIECE, BigDecimal.valueOf(2.99)));
        mockProducts.add(new Product(3, "mockWoda", Unit.PIECE, BigDecimal.valueOf(1.39)));
        return mockProducts;
//        return sqlSession.selectList(PRODUCT_LIST_BY_IDS, productIDs);
    }
}
