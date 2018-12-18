package com.demon.user.remote;


import com.demon.user.model.User;

import java.util.List;

/**
 * Created by wangpengpeng on 2018/12/14.
 */
public interface UserRemote {
    public Object saveUser(User u);

    public Object saveList(List<User> u);
}
