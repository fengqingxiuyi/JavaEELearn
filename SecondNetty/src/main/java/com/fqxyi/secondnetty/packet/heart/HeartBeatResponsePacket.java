package com.fqxyi.secondnetty.packet.heart;

import com.fqxyi.secondnetty.packet.Packet;
import lombok.Data;

import static com.fqxyi.secondnetty.packet.Command.HEART_RESPONSE;

@Data
public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEART_RESPONSE;
    }
}
