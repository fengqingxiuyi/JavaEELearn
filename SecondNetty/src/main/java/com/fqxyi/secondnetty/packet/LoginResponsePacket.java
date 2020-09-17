package com.fqxyi.secondnetty.packet;

import lombok.Data;

import static com.fqxyi.secondnetty.packet.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {
    //是否登录成功
    private boolean success;
    //如果失败，返回的信息
    private String reason;
    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
