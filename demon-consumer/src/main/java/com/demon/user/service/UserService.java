package com.demon.user.service;

import com.demon.client.parame.Response;
import com.demon.client.parame.ResponseUtil;
import com.demon.user.bean.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangpengpeng on 2018/12/12.
 */
@Service
public class UserService {
    public Response save(User user) {
        return ResponseUtil.createSuccessResponse(user);
    }

    public Response saveList(List<User> users) {
        return ResponseUtil.createSuccessResponse(users);
    }
}
