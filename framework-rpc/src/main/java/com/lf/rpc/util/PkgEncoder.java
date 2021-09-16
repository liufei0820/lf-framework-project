package com.lf.rpc.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Classname PkgEncoder
 * @Date 2021/9/16 下午4:50
 * @Created by fei.liu
 */
public class PkgEncoder extends MessageToByteEncoder<Object> {

    public PkgEncoder() {}

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        try {
            //在这之前可以实现编码工作。
            out.writeBytes((byte[])msg);
        } finally {

        }
    }


}
