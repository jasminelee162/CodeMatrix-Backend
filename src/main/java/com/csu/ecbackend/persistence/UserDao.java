package com.csu.ecbackend.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csu.ecbackend.bean.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseMapper<User> {

}
