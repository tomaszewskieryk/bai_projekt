package com.example.myshopper.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FridgeStateRepository {

    private final SqlSession sqlSession;

    @Autowired
    public FridgeStateRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int saveFridgeStateForNewUser(int userID) {
        return sqlSession.insert("createFridgeStateForNewUser", userID);
    }
}
