package com.fqxyi.secondnetty.handler.heart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

//心跳检测类
public class IMIdleStateHandler extends IdleStateHandler {
    //读空闲时间，也就是多久没读到数据了
    private static final int READER_IDLE_TIME = 15;
    public IMIdleStateHandler() {
        //调用父类构造函数，四个参数分别为：
        //读空闲时间、写空闲时间、读写空闲时间、时间单位
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }
    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        System.out.println(READER_IDLE_TIME + "秒内未读到数据，关闭连接");
        ctx.channel().close();
    }
}
