package com.lf.rpc.server;

import com.lf.rpc.server.connect.TcpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname RpcServer
 * @Date 2021/9/16 下午8:14
 * @Created by fei.liu
 */
public class RpcServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServer.class);

    private static int SERVER_LISTEN_PORT = 58885;

    public static void main(String[] args) throws InterruptedException {
        Thread tcpServerThread = new Thread("tcpServer") {
            public void run() {
                TcpServer tcpServer = new TcpServer(SERVER_LISTEN_PORT);
                try {
                    tcpServer.start();
                } catch (Exception e) {
                    LOGGER.info("TcpServer start exception: {}", e.getMessage());
                }
            }
        };

        tcpServerThread.start();
        tcpServerThread.join();
    }
}
