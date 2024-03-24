package com.ksy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ksy.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User> {

    /**
     * 查找全部用户数据
     */
    List<User> findList();
}