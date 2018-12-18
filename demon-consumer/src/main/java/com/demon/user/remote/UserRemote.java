package com.demon.user.remote;


import com.demon.client.parame.Response;
import com.demon.user.bean.User;

import java.util.List;

/**
 * Created by wangpengpeng on 2018/12/14.
 */
public interface UserRemote {
    public Response saveUser(User u);

    public Response saveList(List<User> u);
}
