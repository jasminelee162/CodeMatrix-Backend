package com.csu.ecbackend.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csu.ecbackend.bean.UserRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRecordDao extends BaseMapper<UserRecord> {

}
