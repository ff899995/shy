package com.base.mapper;

import com.base.domain.user.entity.User;
import com.base.domain.user.vo.M_UserDetailsVo;
import com.base.domain.user.vo.UserDetailsVo;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

    UserDetailsVo details(String rowguid);
    // 查看普通用户信息
    M_UserDetailsVo m_details_1(String rowguid);
}