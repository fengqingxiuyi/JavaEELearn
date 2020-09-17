package com.fqxyi.firstnetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {

    public void bind(int port) throws Exception {
        //配置服务端的NIO线程组
        //bossGroup用于服务端接受客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //workerGroup用于进行SocketChannel的网络读写
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //ServerBootstrap是Netty用于启动NIO服务端的辅助启动类，目的是降低服务端的开发复杂度
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    //设置创建Channel的工厂，后面会调用NioServerSocketChannel的无参构造进行初始化，
                    //它的功能对应于JDK NIO类库中的ServerSocketChannel类
                    .channel(NioServerSocketChannel.class)
                    //option：用于配置TCP参数
                    //初始化服务端可连接队列，服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，
                    //多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //这个最终会绑定到新连进来的SocketChannel的pipeline上，它的作用类似于Reactor模式中的handler类，
                    //主要用于处理网络I/O事件，例如记录日志、对消息进行编解码等。
                    .childHandler(new ChildChannelHandler());
            //绑定端口，同步等待成功，f用于异步操作的通知回调
            ChannelFuture f = b.bind(port).sync();
            //阻塞，等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            //优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private static class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                //采用默认值
            }
        }
        new TimeServer().bind(port);
    }

}
