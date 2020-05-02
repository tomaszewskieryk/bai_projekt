package com.example.myshopper.repository;

import com.example.myshopper.repository.model.ProductEntity;
import com.example.myshopper.repository.model.ProductStateEntity;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        return sqlSession.selectList(PRODUCT_STATE_ENTITY_LIST_BY_STATE_ID, fridgeStateID);
    }

    public List<ProductEntity> getProductsByIds(List<Integer> productIDs) {
        return sqlSession.<ProductEntity>selectList(PRODUCT_LIST_BY_IDS, productIDs);
    }
}
