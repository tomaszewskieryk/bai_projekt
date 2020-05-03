package com.example.myshopper.repository;

import com.example.myshopper.repository.model.FridgeStateEntity;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FridgeStateRepository {

    private static final String GET_STATE_BY_ID = "getFridgeStateByID";
    private static final String GET_STATES_BY_USER_ID = "getFridgeStatesByUserID";
    private static final String GET_NOT_ACTUAL_STATES_BY_USER_ID = "getNotActualFridgeStatesByUserID";
    private static final String GET_ACTUAL_STATE_BY_USER_ID = "getActualFridgeStateByUserID";
    private static final String GET_IS_ACTUAL_BY_STATE_ID = "getIsActualByStateId";

    private static final String CREATE_STATE_FOR_NEW_USER = "createFridgeStateForNewUser";
    private static final String CREATE_FRIDGE_STATE_ENTITY = "createFridgeStateEntity";

    private static final String UPDATE_FRIDGE_STATE_ENTITY = "updateFridgeStateEntity";

    private static final String DELETE_STATE_ENTITY = "deleteFridgeStateEntity";
    private static final String DELETE_PRODUCT_STATE_ENTITIES_BY_FRIDGE_ID = "deleteAllProductShoppingListEntities";


    private final SqlSession sqlSession;

    @Autowired
    public FridgeStateRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int saveFridgeStateForNewUser(FridgeStateEntity fridgeStateEntity) {
        sqlSession.insert(CREATE_STATE_FOR_NEW_USER, fridgeStateEntity);
        return fridgeStateEntity.getFridgeStateID();
    }

    public Optional<FridgeStateEntity> getFridgeStateEntityByID(int fridgeStateID) {
        return Optional.ofNullable(sqlSession.selectOne(GET_STATE_BY_ID, fridgeStateID));
    }

    public List<FridgeStateEntity> getFridgeStatesEntitiesByUserID(int userID) {
        return sqlSession.selectList(GET_STATES_BY_USER_ID, userID);
    }

    public List<FridgeStateEntity> getNotActualFridgeStatesEntitiesByUserID(int userID) {
        return sqlSession.selectList(GET_NOT_ACTUAL_STATES_BY_USER_ID, userID);
    }

    public Optional<FridgeStateEntity> getActualFridgeStateEntityByUserID(int userID) {
        return Optional.ofNullable(sqlSession.selectOne(GET_ACTUAL_STATE_BY_USER_ID, userID));
    }

    public Optional<Boolean> getFridgeIsActual(int fridgeStateID) {
        return Optional.ofNullable(sqlSession.selectOne(GET_IS_ACTUAL_BY_STATE_ID, fridgeStateID));
    }

    public int saveFridgeStateEntity(FridgeStateEntity fridgeStateEntity) {
        sqlSession.insert(CREATE_FRIDGE_STATE_ENTITY, fridgeStateEntity);
        return fridgeStateEntity.getFridgeStateID();
    }

    public void updateFridgeStateEntity(FridgeStateEntity fridgeStateEntity) {
        sqlSession.update(UPDATE_FRIDGE_STATE_ENTITY, fridgeStateEntity);
    }

    public void deleteFridgeStateEntity(int fridgeStateID) {
        sqlSession.delete(DELETE_STATE_ENTITY, fridgeStateID);
    }

    public void deleteAllFridgeConnections(int fridgeStateID) {
        sqlSession.delete(DELETE_PRODUCT_STATE_ENTITIES_BY_FRIDGE_ID, fridgeStateID);
    }
}
