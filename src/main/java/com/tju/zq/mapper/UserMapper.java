package com.tju.zq.mapper;


import com.tju.zq.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserMapper {
    User findById(User user);

    User selectByNameAndPwd(User user);

    int insert(User user);

    int update(User user);

    int delete(User user);

    int selectIsName(User user);

    List<User> list(User user);

    int count(User user);

    String selectPasswordByName(User user);
}
