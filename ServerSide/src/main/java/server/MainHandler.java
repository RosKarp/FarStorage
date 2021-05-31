package server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class MainHandler extends SimpleChannelInboundHandler<String> {
        private static final List<Channel> channels = new ArrayList<>();// заменить на вебинарный вариант
        //public static final ConcurrentLinkedQueue<SocketChannel> channels = new ConcurrentLinkedQueue<>();
        //channels.add((SocketChannel) ctx.channel());
        private static int newClientIndex = 1;
        private String clientName;
        //boolean Authentication = false;
        public static Connection connectMySQL;
        private PreparedStatement preparedStatement;

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Клиент подключился: " + ctx);
            channels.add(ctx.channel());
            clientName = "Клиент #" + newClientIndex;
            newClientIndex++;
            ctx.channel().writeAndFlush("Введите логин и пароль через пробел, нажмите \"Вход\"");

            //broadcastMessage("SERVER", "Подключился новый клиент: " + clientName);
        }


        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
            System.out.println("Получено сообщение: " + s);
            if (s.startsWith("/")) {
                if (s.startsWith("/Auth ")) {
                    String login = s.split("\\s", 3)[1];
                    String password = s.split("\\s", 3)[2];
                    String AuthResult = "";
                    try {
                        connectMySQL = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "root");    // подключение к MySQL

                        preparedStatement = connectMySQL.prepareStatement("SELECT * FROM logpass where login = ? and password = ?");
                        preparedStatement.setString(1, login);
                        preparedStatement.setString(2, password);
                        ResultSet rs = preparedStatement.executeQuery();
                        if(rs.next()){
                            //Authentication = true;
                            System.out.println("Вход выполнен");
                            AuthResult = "Вход выполнен";
                        } else {
                            AuthResult = "Неверный логин или пароль";
                        }
                        channelHandlerContext.writeAndFlush(AuthResult);
            /*rs.next();
            if ((rs.getString("password")).equals(msg.split(" ")[1])) {
                System.out.println("Authentication - ok");
                msg = "Authentication - ok";
                ctx.writeAndFlush(msg);

            }*/
                    } catch(SQLException e){
                        System.out.println("Ошибка подключения к БД.");
                    }
                    //broadcastMessage("SERVER", "Клиент " + clientName + " сменил ник на " + newNickname);
                    //clientName = newNickname;
                }
            }
            //broadcastMessage(clientName, s);
        }

       /* public void broadcastMessage(String clientName, String message) {
            String out = String.format("[%s]: %s\n", clientName, message);
            for (Channel c : channels) {
                c.writeAndFlush(out);
            }
        }*/

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Клиент " + clientName + " вышел из сети");
            channels.remove(ctx.channel());
           //broadcastMessage("SERVER", "Клиент " + clientName + " вышел из сети");
            ctx.close();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("Клиент " + clientName + " отвалился");
            channels.remove(ctx.channel());
           //broadcastMessage("SERVER", "Клиент " + clientName + " вышел из сети");
            ctx.close();
        }
}
