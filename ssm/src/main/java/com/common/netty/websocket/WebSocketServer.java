package com.common.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServer {

	public static void main(String[] args) {
		int port = 8088;
		try {
			new WebSocketServer().run(port);
		} catch (Exception e) {

		}

	}

	public void run(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel channel) throws Exception {
							ChannelPipeline pipeline = channel.pipeline();
							pipeline.addLast("http-codec", new HttpServerCodec());
							pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
							pipeline.addLast("http-chunked", new ChunkedWriteHandler());
							pipeline.addLast("handler", new WebSocketServerHandler());

						}

					});

			Channel ch = bootstrap.bind(port).sync().channel();

			System.out.println("Web Socket Server started at port:" + port);

			ch.closeFuture().sync();

		} catch (Exception e) {

			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();

			e.printStackTrace();

		}
	}
}
