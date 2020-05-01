package com.example.myshopper.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository {

    private final SqlSession sqlSession;

    @Autowired
    public TestRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public String getTest() {
        return sqlSession.selectOne("testSelect");
    }
}
