package com.lf.rpc.client;

import com.lf.rpc.client.entity.User;
import com.lf.rpc.client.service.UserService;

/**
 * @Classname RpcClient
 * @Date 2021/9/16 下午8:11
 * @Created by fei.liu
 */
public class RpcClient {

    public static void main(String[] args) {
        UserService proxyUserService = new UserService();

        User user = new User();
        user.setAge((short) 26);
        user.setSex((short) 1);

        int ret = proxyUserService.addUser(user);
        if (ret == 0) {
            System.out.println("调用远程服务创建用户成功！！！");
        } else {
            System.out.println("调用远程服务创建用户失败！！！");
        }
    }
}
