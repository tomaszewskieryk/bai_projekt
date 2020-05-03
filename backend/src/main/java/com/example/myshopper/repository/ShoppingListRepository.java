package com.example.myshopper.repository;

import com.example.myshopper.repository.model.ProductShoppingListEntity;
import com.example.myshopper.repository.model.ShoppingListEntity;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShoppingListRepository {

    private static final String CREATE_SHOPPING_LIST_ENTITY = "createShoppingListEntity";
    private static final String CREATE_PRODUCT_SHOPPING_LIST_ENTITIES = "createProductShoppingListEntities";

    private static final String GET_SHOPPING_LIST_ENTITIES_BY_USER_ID = "getShoppingListEntitiesByUserID";

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
        return sqlSession.<ShoppingListEntity>selectList(GET_SHOPPING_LIST_ENTITIES_BY_USER_ID, userID);
    }

}
