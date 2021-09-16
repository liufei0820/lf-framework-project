package com.lf.rpc.server.connect;

import com.lf.rpc.server.entity.User;
import com.lf.rpc.server.protocol.RpcProtocol;
import com.lf.rpc.server.server.UserService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @Classname ServerHandler
 * @Date 2021/9/16 下午4:31
 * @Created by fei.liu
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);

    private static int CMD_CREATE_USER = 1;
    private static int CMD_FIND_USER = 2;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel ch = ctx.channel();
        InetSocketAddress socketAddress = (InetSocketAddress) ch.remoteAddress();

        String clientIp = socketAddress.getAddress().getHostAddress();
        LOGGER.info("client connect to rpc server, client's ip is {}", clientIp);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel ch = ctx.channel();
        InetSocketAddress socketAddress = (InetSocketAddress) ch.remoteAddress();
        String clientIp = socketAddress.getAddress().getHostAddress();

        LOGGER.info("client close the connection to rpc server, client's ip is: " + clientIp);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] receiveData = (byte[]) msg;
        if (receiveData.length == 0) {
            LOGGER.warn("receive request from client, but the data length is 0");
            return;
        }

        LOGGER.info("receive request from client, the data length is: {}", receiveData.length);

        // 反序列化请求数据
        RpcProtocol rpcReq = new RpcProtocol();
        rpcReq.byteArrayToRpcHeader(receiveData);

        if (rpcReq.getMagicNum() != RpcProtocol.CONST_CMD_MAGIC) {
            LOGGER.warn("request magic code error");
            return;
        }

        // 解析请求，并调用处理方法
        int ret = -1;
        if (rpcReq.getCmd() == CMD_CREATE_USER) {
            User user = rpcReq.byteArrayToUserInfo(rpcReq.getBody());
            UserService userService = new UserService();
            ret = userService.addUser(user);

            //构造返回数据
            RpcProtocol rpcResp = new RpcProtocol();
            rpcResp.setCmd(rpcReq.getCmd());
            rpcResp.setVersion(rpcReq.getVersion());
            rpcResp.setMagicNum(rpcReq.getMagicNum());
            rpcResp.setBodyLen(Integer.BYTES);
            byte[] body = rpcResp.createUserRespTobyteArray(ret);
            rpcResp.setBody(body);
            ByteBuf respData = Unpooled.copiedBuffer(rpcResp.generateByteArray());
            ctx.channel().writeAndFlush(respData);
        }
    }
}
