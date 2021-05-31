package client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

    public class ClientHandler extends SimpleChannelInboundHandler<String> {
        private Callback ReceivedMessage;

        public ClientHandler(Callback ReceivedMessage) {
            this.ReceivedMessage = ReceivedMessage;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
            if (ReceivedMessage != null) {
                ReceivedMessage.callback(s);
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
            cause.printStackTrace();
        }
}
