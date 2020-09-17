package com.fqxyi.secondnetty.handler.heart;

import com.fqxyi.secondnetty.packet.heart.HeartBeatRequestPacket;
import com.fqxyi.secondnetty.packet.heart.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

//回复客户端发送的心跳数据包
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();
    private HeartBeatRequestHandler() {
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket requestPacket) {
        ctx.writeAndFlush(new HeartBeatResponsePacket());
    }
}
