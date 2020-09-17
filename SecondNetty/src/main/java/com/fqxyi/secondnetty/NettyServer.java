package com.fqxyi.secondnetty;

import com.fqxyi.secondnetty.coder.PacketDecoder;
import com.fqxyi.secondnetty.coder.PacketEncoder;
import com.fqxyi.secondnetty.handler.heart.HeartBeatRequestHandler;
import com.fqxyi.secondnetty.handler.heart.IMIdleStateHandler;
import com.fqxyi.secondnetty.handler.login.ServerLoginHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NettyServer {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                //开启TCP底层心跳机制
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //开启Nagle算法，如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        System.out.println("服务端启动中");
                        //ch.pipeline().addLast(new FirstServerHandler());

                        //ch.pipeline().addLast(new ServerHandler());

                        //添加到最前面
                        ch.pipeline().addLast(new IMIdleStateHandler());

                        /*
                         * 基于长度域拆包器
                         * 以上面客户端和服务端双向通信的代码为例。简单修改一下，在建立连接后，客户端用一个循环向服务器发送消息。然后服务端打印这些消息。
                         * 等次数多了以后，服务端打印时会发现一些问题，比如我们发送的字符串为“123456789”，大部分打印的是123456789；
                         * 有一部分变成了123456789123，这就是粘包；有一部分变为了1234，这就是拆包。
                         */
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));

                        /*
                         * 解码一定要放在第一个，在这里pipeline按顺序执行，不然接收消息无法正常使用
                         * 以下三句等同于ch.pipeline().addLast(new ServerHandler());
                         */
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new ServerLoginHandler());
                        ch.pipeline().addLast(new PacketEncoder());

                        //添加到解码和登录请求之后
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                    }
                });

        serverBootstrap.bind(8000);
    }

}
