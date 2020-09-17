package com.fqxyi.secondnetty.packet.heart;

import com.fqxyi.secondnetty.packet.Packet;
import lombok.Data;

import static com.fqxyi.secondnetty.packet.Command.HEART_REQUEST;

@Data
public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEART_REQUEST;
    }
}
