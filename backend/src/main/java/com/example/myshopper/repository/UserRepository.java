package com.example.myshopper.repository;

import com.example.myshopper.model.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    private static final String GET_USER_BY_ID = "getUserByID";
    private static final String GET_USER_BY_EMAIL = "getUserByEmail";
    private static final String GET_USER_BY_NICKNAME = "getUserByNickname";
    private static final String GET_TEST_USER = "testUserSelect";

    private static final String CREATE_USER = "createNewUser";

    private final SqlSession sqlSession;

    @Autowired
    public UserRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }


    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(sqlSession.selectOne(GET_USER_BY_EMAIL, email));
    }

    public User getTestUser() {
        return sqlSession.selectOne(GET_TEST_USER);
    }

    public int createUser(User user) {
        sqlSession.insert(CREATE_USER, user);
        return user.getUserID();
    }

    public Optional<User> getUserByNickname(String nickname) {
        return Optional.ofNullable(sqlSession.selectOne(GET_USER_BY_NICKNAME, nickname));
    }

    public User getUserByID(int userID) {
        return sqlSession.selectOne(GET_USER_BY_ID, userID);
    }
}
