package com.csu.ecbackend.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csu.ecbackend.bean.UserFeedback;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeedbackDao extends BaseMapper<UserFeedback> {
}
