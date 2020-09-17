package com.fqxyi.secondnetty.handler.login;

import com.fqxyi.secondnetty.packet.LoginRequestPacket;
import com.fqxyi.secondnetty.packet.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerLoginHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
        //服务端登录逻辑
        ctx.channel().writeAndFlush(login(loginRequestPacket));
    }
    private LoginResponsePacket login(LoginRequestPacket loginRequestPacket) {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        // 登录校验(自行判断用户信息是否正确)
        if (true) {
            // 校验成功
            loginResponsePacket.setSuccess(true);
            System.out.println("客户端登录成功！");
            return loginResponsePacket;
        } else {
            // 校验失败
            loginResponsePacket.setReason("账号或密码错误");
            loginResponsePacket.setSuccess(false);
            System.out.println("客户端登录失败！");
            return loginResponsePacket;
        }
    }
}
