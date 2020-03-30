package com.test.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpServer {
  public static void main(String[] args) {
    //构造两个线程组,bossGroup 用于接收客户端传过来的请求，接收到请求后将后续操作交由 workerGroup 处理
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      //服务端启动辅助类，为 Netty 程序的启动组装配置一些必须要组件
      ServerBootstrap bootstrap = new ServerBootstrap();

      //channel()用于指定服务器端监听套接字通道 NioServerSocketChannel
      //childHandler()用于设置业务链，业务链是由一个个的 ChannelHandler 串联而成，形成的链式结构
      bootstrap.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .childHandler(new HttpServerInitializer());

      // bootstrap 的 bind 方法将服务绑定到 8080 端口上，bind 方法内部会执行端口绑定等一系列操
      //sync()用于阻塞当前 Thread，一直到端口绑定操作完成
      ChannelFuture future = bootstrap.bind(8080).sync();

      //阻塞等待直到服务器的 Channel 关闭
      future.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }finally {
      // 优雅退出，释放线程池资源
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }
}
