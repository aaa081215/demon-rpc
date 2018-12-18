package com.demon.user.service;


import com.demon.demonrpc.util.Response;
import com.demon.demonrpc.util.ResponseUtil;
import com.demon.user.model.User;
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
