package com.csu.ecbackend.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csu.ecbackend.bean.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDao  extends BaseMapper<UserInfo> {
}
