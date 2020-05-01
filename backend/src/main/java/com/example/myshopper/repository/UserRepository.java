package com.example.myshopper.repository;

import com.example.myshopper.model.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final SqlSession sqlSession;

    @Autowired
    public UserRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }


    public User getUserByEmail(String email) {
        return sqlSession.selectOne("getUserByEmail", email);
    }

    public User getTestUser() {
        return sqlSession.selectOne("testUserSelect");
    }

    public int createUser(User user) {
        sqlSession.insert("createNewUser", user);
        return user.getUserID();
    }

    public User getUserByNickname(String nickname) {
        return sqlSession.selectOne("getUserByNickname", nickname);
    }
}
