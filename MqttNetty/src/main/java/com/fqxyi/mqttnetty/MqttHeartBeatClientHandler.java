package com.fqxyi.mqttnetty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.MqttConnectMessage;
import io.netty.handler.codec.mqtt.MqttConnectPayload;
import io.netty.handler.codec.mqtt.MqttConnectVariableHeader;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.codec.mqtt.MqttProperties;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

public class MqttHeartBeatClientHandler extends ChannelInboundHandlerAdapter {

    private static final String PROTOCOL_NAME_MQTT_3_1_1 = "MQTT";
    private static final int PROTOCOL_VERSION_MQTT_3_1_1 = 4;

    private final String clientId;
    private final String userName;
    private final byte[] password;

    public MqttHeartBeatClientHandler(String clientId, String userName, String password) {
        this.clientId = clientId;
        this.userName = userName;
        this.password = password.getBytes();
    }

    /**
     * 业务逻辑处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // discard all messages
        /*
         * ReferenceCountUtil.release()其实是ByteBuf.release()方法（从ReferenceCounted接口继承而来）的包装。
         * netty4中的ByteBuf使用了引用计数（netty4实现了一个可选的ByteBuf池），每一个新分配的ByteBuf的引用计数值为1，
         * 每对这个ByteBuf对象增加一个引用，需要调用ByteBuf.retain()方法，而每减少一个引用，需要调用ByteBuf.release()方法。
         * 当这个ByteBuf对象的引用计数值为0时，表示此对象可回收。
         */
        ReferenceCountUtil.release(msg);
    }

    /**
     * 建立连接时
     * 一个MQTT报文主要由三部分组成：固定报头（Fix Header），可变报头（Variable Header）和Payload。
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /*
         * 固定报头（Fix Header）
         * 使用一个Byte来标识，其中高四位用于控制报文类型。所以，MQTT最多能够表示16中报文类型。低四位是标志位（Flags）。
         * 参数：https://mcxiaoke.gitbooks.io/mqtt-cn/content/mqtt/02-ControlPacketFormat.html
         * MqttMessageType messageType, MQTT控制报文的类型
         * boolean isDup, 控制报文的重复分发标志
         * MqttQoS qosLevel, PUBLISH报文的服务质量等级
         * boolean isRetain, PUBLISH报文的保留标志
         * int remainingLength, 当前报文剩余部分的字节数，包括可变报头和负载的数据。
         */
        MqttFixedHeader connectFixedHeader =
                new MqttFixedHeader(MqttMessageType.CONNECT, false, MqttQoS.AT_MOST_ONCE, false, 0);
        /*
         * 可变报头（Variable Header）
         * 存在于部分报文中，并且不同的报文类型其可变报头也不一样。
         * 比如PUBLISH（QoS > 0时）， PUBACK，PUBREC，PUBREL，PUBCOMP，SUBSCRIBE, SUBACK， UNSUBSCIBE，UNSUBACK这些报文拥有一个两个字节长度的Package Identifier；
         * CONNECT有四个可变报头：Protocol Name，Protocol Level，Connect Flags，Keep Alive。
         * 参数：https://mcxiaoke.gitbooks.io/mqtt-cn/content/mqtt/0301-CONNECT.html
         * String name,
         * int version,
         * boolean hasUserName,
         * boolean hasPassword,
         * boolean isWillRetain, 是否保留
         * int willQos, 消息的服务等级
         * boolean isWillFlag, 是否在意外断开时发布遗嘱消息
         * boolean isCleanSession, 如果清理会话（CleanSession）标志被设置为0，服务端必须基于当前会话（使用客户端标识符识别）的状态恢复与客户端的通信。
         * int keepAliveTimeSeconds,
         * MqttProperties properties
         */
        MqttConnectVariableHeader connectVariableHeader =
                new MqttConnectVariableHeader(PROTOCOL_NAME_MQTT_3_1_1, PROTOCOL_VERSION_MQTT_3_1_1, true, true, false,
                        0, false, false, 20, MqttProperties.NO_PROPERTIES);
        /*
         * 有效载荷（Payload）
         * 部分报文拥有。
         * 比如PUBLISH，用它来存储推送的消息内容；CONNECT消息可用它来存储用户名密码；SUBSCRIBE可用它来存储订阅的主题名；等等。
         * 参数：https://mcxiaoke.gitbooks.io/mqtt-cn/content/mqtt/0301-CONNECT.html
         * String clientIdentifier, 客户端标识符
         * MqttProperties willProperties,
         * String willTopic, 遗嘱主题
         * byte[] willMessage, 遗嘱消息
         * String userName, 用户名
         * byte[] password 密码
         */
        MqttConnectPayload connectPayload = new MqttConnectPayload(clientId,
                MqttProperties.NO_PROPERTIES,
                null,
                null,
                userName,
                password);
        MqttConnectMessage connectMessage =
                new MqttConnectMessage(connectFixedHeader, connectVariableHeader, connectPayload);
        ctx.writeAndFlush(connectMessage);
        System.out.println("Sent CONNECT");
    }

    /**
     * 心跳请求处理
     * 每5秒发送一次心跳请求;
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            MqttFixedHeader pingreqFixedHeader =
                    new MqttFixedHeader(MqttMessageType.PINGREQ, false, MqttQoS.AT_MOST_ONCE, false, 0);
            MqttMessage pingreqMessage = new MqttMessage(pingreqFixedHeader);
            ctx.writeAndFlush(pingreqMessage);
            System.out.println("Sent PINGREQ");
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
