package com.demon.user.remote;


import com.demon.demonrpc.annotation.Remote;
import com.demon.demonrpc.util.ResponseUtil;
import com.demon.user.model.User;
import com.demon.user.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangpengpeng on 2018/12/14.
 */
@Remote
public class UserRemoteImpl implements UserRemote {
    @Resource
    private UserService userService;

    public Object saveUser(User u) {
        return ResponseUtil.createSuccessResponse(u);
    }

    public Object saveList(List<User> u) {
        return userService.saveList(u);
    }
}
