package com.fqxyi.secondnetty.handler.heart;

import com.fqxyi.secondnetty.packet.heart.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {
    //心跳数据包发送时间间隔，这里设为5秒，实际使用时建议服务端和客户端都设成分钟级别
    private static final int HEARTBEAT_INTERVAL = 5;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);
        super.channelActive(ctx);
    }
    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        //schedule类似延时任务，每隔5秒发送心跳数据包
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(new HeartBeatRequestPacket());
                scheduleSendHeartBeat(ctx);
            }
        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
