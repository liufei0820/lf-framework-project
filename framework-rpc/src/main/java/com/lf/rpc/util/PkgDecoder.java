package com.lf.rpc.util;

import com.lf.rpc.client.protocol.RpcProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Classname PkgDecoder
 * @Date 2021/9/16 下午1:32
 * @Created by fei.liu
 */
public class PkgDecoder extends ByteToMessageDecoder {

    private static final Logger LOGGER  = LoggerFactory.getLogger(PkgDecoder.class);

    public PkgDecoder() {}

    @Override
    protected void decode(ChannelHandlerContext chc, ByteBuf buffer, List<Object> out) throws Exception {
        if (buffer.readableBytes() < RpcProtocol.HEAD_LEN) {
            return;  // 未读完足够字节流，缓存后续继续读
        }

        byte[] intBuf = new byte[4];
        buffer.getBytes(buffer.readerIndex() + RpcProtocol.HEAD_LEN - 4, intBuf); // ImHeader的bodyLen在第68位到71为, int类型
        int bodyLen = ByteConverter.bytesToIntBigEndian(intBuf);

        if (buffer.readableBytes() < RpcProtocol.HEAD_LEN + bodyLen) {
            return; // 未读完足够字节流，缓存后续继续读
        }

        byte[] bytesReady = new byte[RpcProtocol.HEAD_LEN + bodyLen];
        buffer.readBytes(bytesReady);
        out.add(bytesReady);
    }
}
