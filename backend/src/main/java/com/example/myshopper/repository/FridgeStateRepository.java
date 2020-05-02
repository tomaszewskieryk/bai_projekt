package com.example.myshopper.repository;

import com.example.myshopper.repository.model.FridgeStateEntity;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FridgeStateRepository {

    private static final String CREATE_STATE_FOR_NEW_USER = "createFridgeStateForNewUser";
    private static final String GET_STATE_BY_ID = "getFridgeStateByID";
    private static final String GET_STATES_BY_USER_ID = "getFridgeStatesByUserID";
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

    public Optional<FridgeStateEntity> getFridgeStateEntityByID(int fridgeStateID) {
        return Optional.ofNullable(sqlSession.selectOne(GET_STATE_BY_ID, fridgeStateID));
    }

    public List<FridgeStateEntity> getFridgeStatesEntityByUserID(int userID) {
        return sqlSession.selectList(GET_STATES_BY_USER_ID, userID);
    }

    public Optional<FridgeStateEntity> getActualFridgeStateEntityByUserID(int userID) {
        return Optional.ofNullable(sqlSession.selectOne(GET_ACTUAL_STATE_BY_USER_ID, userID));
    }
}
