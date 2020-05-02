package com.example.myshopper.repository;

import com.example.myshopper.repository.model.FridgeStateEntity;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FridgeStateRepository {

    private static final String CREATE_STATE_FOR_NEW_USER = "createFridgeStateForNewUser";
    private static final String GET_STATE_BY_ID = "getFridgeStateByID";
    private static final String GET_STATE_BY_USER_ID = "getFridgeStateByUserID";
    private static final String GET_ACTUAL_STATE_BY_USER_ID = "getActualFridgeStateByUserID";

    private final SqlSession sqlSession;

    @Autowired
    public FridgeStateRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int saveFridgeStateForNewUser(FridgeStateEntity fridgeStateEntity) {
        sqlSession.insert(CREATE_STATE_FOR_NEW_USER, fridgeStateEntity);
        return fridgeStateEntity.getFridgeStateID();
    }

    public FridgeStateEntity getFridgeStateEntityByID(int fridgeStateID) {
        return new FridgeStateEntity(1, "mockActual", true, 1);
//        return sqlSession.selectOne(GET_STATE_BY_ID, fridgeStateID);
    }

    public List<FridgeStateEntity> getFridgeStatesEntityByUserID(int userID) {
        List<FridgeStateEntity> entities = new ArrayList<>();
        entities.add(new FridgeStateEntity(1, "mockActual", true, 1));
        return entities;
//        return sqlSession.selectList(GET_STATE_BY_USER_ID, userID);
    }

    public FridgeStateEntity getActualFridgeStateEntityByUserID(int userID) {
        return new FridgeStateEntity(1, "mockActual", true, 1);
//        return sqlSession.selectOne(GET_ACTUAL_STATE_BY_USER_ID, userID);
    }
}
