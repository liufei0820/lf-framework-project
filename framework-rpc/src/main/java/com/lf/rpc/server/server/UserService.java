package com.lf.rpc.server.server;

import com.lf.rpc.server.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname UserService
 * @Date 2021/9/16 下午4:08
 * @Created by fei.liu
 */
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public int addUser(User userInfo) {
        LOGGER.debug("create user success, uid = {}", userInfo.getUid());
        return 0;
    }
}
