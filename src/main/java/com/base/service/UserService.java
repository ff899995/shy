package com.base.service;

import com.base.domain.user.dto.*;
import com.base.http.ResultMap;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    ResultMap register(RegisterDto registerDto) throws Exception;

    ResultMap login(HttpServletRequest request, LoginDto loginDto) throws Exception;

    ResultMap details(HttpServletRequest request) throws Exception;

    ResultMap modify(HttpServletRequest request, UserModifyDto userModifyDto) throws Exception;

    ResultMap c_register(HttpServletRequest request, C_UserRegisterDto c_userRegisterDto) throws Exception;

    ResultMap userList(HttpServletRequest request, M_PageUserDto pageDto);

    ResultMap m_details(HttpServletRequest request, String rowguid) throws Exception;

    ResultMap m_modify(HttpServletRequest request, M_UserModifyDto m_userModifyDto) throws Exception;

    ResultMap delete(HttpServletRequest request, List<String> list);
}
