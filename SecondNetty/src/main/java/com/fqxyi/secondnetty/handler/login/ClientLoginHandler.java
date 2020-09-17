package com.fqxyi.secondnetty.handler.login;

import com.fqxyi.secondnetty.packet.login.LoginRequestPacket;
import com.fqxyi.secondnetty.packet.login.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.Random;

public class ClientLoginHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("客户端开始登录....");
        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(new Random().nextInt(10000));
        loginRequestPacket.setUsername("username");
        loginRequestPacket.setPassword("pwd");
        // 写数据
        ctx.channel().writeAndFlush(loginRequestPacket);
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) {
        if (loginResponsePacket.isSuccess()) {
            System.out.println(new Date() + ": 客户端登录成功");
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
        }
    }
}
