package com.test.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class HttpServerChannelHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

  protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
    //Netty 的设计中把 Http 请求分为了 HttpRequest 和 HttpContent 两个部分，
    // HttpRequest 主要包含请求头、请求方法等信息，HttpContent 主要包含请求体的信息
    //FullHttpRequest 包含请求的所有信息，直接或者间接继承了 HttpRequest 和 HttpContent，实现类是 DefalutFullHttpRequest
    FullHttpRequest request = msg;
    System.out.println("请求地址:" + ctx.channel().remoteAddress());
    System.out.println("请求方法名称:" + request.method().name());
    System.out.println("uri:" + request.uri());

    ByteBuf buf = request.content();
    System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));

    ByteBuf byteBuf = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
    response.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/plain");
    response.headers().add(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

    ctx.writeAndFlush(response);

  }
}
