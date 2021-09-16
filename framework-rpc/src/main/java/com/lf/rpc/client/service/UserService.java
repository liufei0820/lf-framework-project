package com.lf.rpc.client.service;

import com.lf.rpc.client.connect.TcpClient;
import com.lf.rpc.client.entity.User;
import com.lf.rpc.client.protocol.RpcProtocol;
import com.lf.rpc.util.ByteConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname UserService
 * @Date 2021/9/16 下午3:29
 * @Created by fei.liu
 */
public class UserService {

    private static final Logger LOGGER  = LoggerFactory.getLogger(UserService.class);

    public int addUser(User userInfo) {
        // 初始化客户端连接
        TcpClient client = TcpClient.getInstance();
        try {
            client.init();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("init rpc client error");
        }

        // 构造请求数据
        RpcProtocol rpcReq = new RpcProtocol();
        rpcReq.setCmd(RpcProtocol.CMD_CREATE_USER);
        rpcReq.setVersion(0x01);
        rpcReq.setMagicNum(0x20210916);
        byte[] body = rpcReq.userInfoToByteArray(userInfo);
        rpcReq.setBody(body);
        rpcReq.setBodyLen(body.length);

        // 序列化
        byte[] reqData = rpcReq.generateByteArray();

        // 发送数据
        client.sendData(reqData);

        // 接受请求结果
        byte[] receiveData = client.receiveData();

        // 反序列化结果
        RpcProtocol rpcResp = new RpcProtocol();
        rpcResp.byteArrayToRpcHeader(receiveData);
        int ret = ByteConverter.bytesToInt(rpcResp.getBody(), 0);
        return ret;
    }

}
