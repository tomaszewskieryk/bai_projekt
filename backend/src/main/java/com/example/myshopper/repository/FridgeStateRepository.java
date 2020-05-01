package com.example.myshopper.repository;

import com.example.myshopper.repository.model.FridgeStateEntity;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FridgeStateRepository {

    private static final String CREATE_STATE_FOR_NEW_USER = "createFridgeStateForNewUser";
    private static final String GET_STATE_BY_ID = "getFridgeStateByID";

    private final SqlSession sqlSession;

    @Autowired
    public FridgeStateRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int saveFridgeStateForNewUser(FridgeStateEntity fridgeStateEntity) {
        sqlSession.insert(CREATE_STATE_FOR_NEW_USER, fridgeStateEntity);
        return fridgeStateEntity.getFridgeStateID();
    }
}
