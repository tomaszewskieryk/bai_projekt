package com.example.myshopper.repository;

import com.example.myshopper.repository.model.ProductEntity;
import com.example.myshopper.repository.model.ProductShoppingListEntity;
import com.example.myshopper.repository.model.ProductStateEntity;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository {

    private static final String GET_PRODUCT_STATE_ENTITY_LIST_BY_STATE_ID = "getProductStateEntityListByStateID";
    private static final String GET_PRODUCT_LIST_BY_IDS = "getProductListByIDs";
    private static final String GET_PRODUCT_SHOPPING_LIST_ENTITIES_BY_LIST_ID = "getProductShoppingListEntitiesByListID";

    private static final String CREATE_PRODUCT_ENTITY = "createProductEntity";
    private static final String CREATE_PRODUCT_STATE_ENTITY = "createProductStateEntity";

    private static final String UPDATE_PRODUCT_STATE_ENTITY = "updateProductStateEntity";
    private static final String UPDATE_PRODUCT_ENTITY = "updateProductEntity";

    private static final String DELETE_PRODUCT_ENTITY = "deleteProductEntity";
    private static final String DELETE_PRODUCT_STATE_ENTITY = "deleteProductStateEntity";
    private static final String DELETE_PRODUCT_SHOPPING_LIST_ENTITY = "deleteProductShoppingListEntity";
    private static final String DELETE_ALL_PRODUCT_STATE_ENTITIES = "deleteAllProductStateEntities";
    private static final String DELETE_ALL_PRODUCT_SHOPPING_LIST_ENTITIES = "deleteAllProductShoppingListEntities";

    private final SqlSession sqlSession;

    @Autowired
    public ProductRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<ProductStateEntity> getProductStateEntitiesByStateID(int fridgeStateID) {
        return sqlSession.selectList(GET_PRODUCT_STATE_ENTITY_LIST_BY_STATE_ID, fridgeStateID);
    }

    public List<ProductEntity> getProductListByIDs(List<Integer> productIDs) {
        return sqlSession.selectList(GET_PRODUCT_LIST_BY_IDS, productIDs);
    }

    public List<ProductShoppingListEntity> getProductShoppingListEntitiesByListID(int shoppingListID) {
        return sqlSession.selectList(GET_PRODUCT_SHOPPING_LIST_ENTITIES_BY_LIST_ID, shoppingListID);
    }

    public int createProductEntity(ProductEntity product) {
        sqlSession.insert(CREATE_PRODUCT_ENTITY, product);
        return product.getProductID();
    }

    public void saveNewProductStateEntity(ProductStateEntity productStateEntity) {
        sqlSession.insert(CREATE_PRODUCT_STATE_ENTITY, productStateEntity);
    }

    public void updateProductStateEntity(ProductStateEntity productStateEntity) {
        sqlSession.update(UPDATE_PRODUCT_STATE_ENTITY, productStateEntity);
    }

    public void updateProductEntity(ProductEntity productEntity) {
        sqlSession.update(UPDATE_PRODUCT_ENTITY, productEntity);
    }

    public void deleteProductEntity(int productID) {
        sqlSession.delete(DELETE_PRODUCT_ENTITY, productID);
    }

    public void deleteProductStateEntity(int fridgeStateID, int productID) {
        Map<String, Integer> params = new HashMap<>();
        params.put("fridgeStateID", fridgeStateID);
        params.put("productID", productID);
        sqlSession.delete(DELETE_PRODUCT_STATE_ENTITY, params);
    }

    public void deleteAllProductStateEntities(int productID) {
        sqlSession.delete(DELETE_ALL_PRODUCT_STATE_ENTITIES, productID);
    }

    public void deleteProductShoppingListEntity(int shoppingListID, int productID) {
        Map<String, Integer> params = new HashMap<>();
        params.put("shoppingListID", shoppingListID);
        params.put("productID", productID);
        sqlSession.delete(DELETE_PRODUCT_SHOPPING_LIST_ENTITY, params);
    }

    public void deleteAllProductShoppingListEntities(int productID) {
        sqlSession.delete(DELETE_ALL_PRODUCT_SHOPPING_LIST_ENTITIES, productID);
    }
}
