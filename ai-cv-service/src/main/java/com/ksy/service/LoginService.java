package com.ksy.service;


import com.baomidou.dynamic.datasource.annotation.Slave;
import com.ksy.domain.User;

import java.util.List;

public interface LoginService {

    @Slave
    List<User> findUserlist();

}
