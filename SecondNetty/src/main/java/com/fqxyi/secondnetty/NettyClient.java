package com.fqxyi.secondnetty;

import com.fqxyi.secondnetty.coder.PacketDecoder;
import com.fqxyi.secondnetty.coder.PacketEncoder;
import com.fqxyi.secondnetty.handler.heart.HeartBeatTimerHandler;
import com.fqxyi.secondnetty.handler.login.ClientLoginHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1.指定线程模型
                .group(workerGroup)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        //ch.pipeline().addLast(new FirstClientHandler());

                        //ch.pipeline().addLast(new ClientHandler());

                        /*
                         * 解码一定要放在第一个，在这里pipeline按顺序执行，不然接收消息无法正常使用
                         * 以下三句等同于ch.pipeline().addLast(new ClientHandler());
                         */
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new ClientLoginHandler());
                        ch.pipeline().addLast(new PacketEncoder());

                        //添加到解码和登录请求之后
                        ch.pipeline().addLast(new HeartBeatTimerHandler());
                    }
                });
        // 4.建立连接
//        bootstrap.connect("127.0.0.1", 8000).addListener(future -> {
//            if (future.isSuccess()) {
//                System.out.println("连接成功!");
//            } else {
//                System.err.println("连接失败!");
//                //重新连接
//            }
//        });
        connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔，1<<order相当于1乘以2的order次方
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() ->
                        connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

}
