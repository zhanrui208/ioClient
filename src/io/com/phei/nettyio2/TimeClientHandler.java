package io.com.phei.nettyio2;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;



public class TimeClientHandler extends ChannelInboundHandlerAdapter {
	
//	private final ByteBuf  firstMessage;
	private int counter;
	
	private byte[] req ;
	
//	public TimeClientHandler(){
//		byte[] req = "QUERY TIME ORDER".getBytes();
//		firstMessage = Unpooled.buffer(req.length);
//		firstMessage.writeBytes(req);
//	}
	
	public TimeClientHandler(){
		System.out.println("服务启动！");
		req = ("QUERY TIME ORDER" +  System.getProperty("line.separator")).getBytes();
	}
	
	
//	@Override
//	public void channelActive(ChannelHandlerContext ctx){
//		ctx.writeAndFlush(firstMessage);
//	}
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx){
		ByteBuf message= null;
		for(int i =0;i<100;i++){
				message= Unpooled.buffer(req.length);
				message.writeBytes(req);
				ctx.writeAndFlush(message);
		}
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
		ByteBuf buf =(ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.out.println("Now is :" + body + ";the counter id :" + counter);
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
		System.out.println("Unexpected exception from downstream :" + cause.getMessage());
		ctx.close();
	}
}
