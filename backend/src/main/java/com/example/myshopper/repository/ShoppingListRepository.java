package com.example.myshopper.repository;

import com.example.myshopper.repository.model.ProductShoppingListEntity;
import com.example.myshopper.repository.model.ShoppingListEntity;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ShoppingListRepository {

    private static final String CREATE_SHOPPING_LIST_ENTITY = "createShoppingListEntity";
    private static final String CREATE_PRODUCT_SHOPPING_LIST_ENTITIES = "createProductShoppingListEntities";

    private static final String GET_SHOPPING_LIST_ENTITIES_BY_USER_ID = "getShoppingListEntitiesByUserID";
    private static final String GET_SHOPPING_LIST_BY_ID = "getShoppingListEntitiesByID";
    private static final String DELETE_PRODUCT_SHOPPING_LIST_ENTITIES_BY_LIST_ID = "deleteProductShoppingListEntitiesByListID";
    private static final String DELETE_SHOPPING_LIST_ENTITIES_BY_LIST_ID = "deleteShoppingListEntitiesByListID";


    private final SqlSession sqlSession;

    @Autowired
    public ShoppingListRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int createShoppingListEntity(ShoppingListEntity shoppingListEntity) {
        sqlSession.insert(CREATE_SHOPPING_LIST_ENTITY, shoppingListEntity);
        return shoppingListEntity.getShoppingListID();
    }

    public void createProductShoppingListEntities(List<ProductShoppingListEntity> productShoppingListEntities) {
        sqlSession.insert(CREATE_PRODUCT_SHOPPING_LIST_ENTITIES, productShoppingListEntities);
    }

    public List<ShoppingListEntity> getShoppingListEntitiesByUserID(int userID) {
        return sqlSession.selectList(GET_SHOPPING_LIST_ENTITIES_BY_USER_ID, userID);
    }

    public Optional<ShoppingListEntity> getShoppingListEntitiesByID(int shoppingListID) {
        return Optional.ofNullable(sqlSession.selectOne(GET_SHOPPING_LIST_BY_ID, shoppingListID));
    }

    public void deleteAllShoppingListConnections(int shoppingListID) {
        sqlSession.delete(DELETE_PRODUCT_SHOPPING_LIST_ENTITIES_BY_LIST_ID, shoppingListID);
    }

    public void deleteShippingListEntity(int shoppingListID) {
        sqlSession.delete(DELETE_SHOPPING_LIST_ENTITIES_BY_LIST_ID, shoppingListID);
    }
}
