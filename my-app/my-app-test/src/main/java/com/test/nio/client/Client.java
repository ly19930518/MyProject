package com.test.nio.client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * 利用 NIO 实现客户端
 */
public class Client {
    SocketChannel socketChannel = null;
    ByteBuffer byteBuffer  = ByteBuffer.allocate(1024);
    String ip = "192.168.0.9";
    int port = 8888;
    public void init() {
        try{
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);//设置为非堵塞
            socketChannel.connect(new InetSocketAddress(ip,port));
            if(socketChannel.finishConnect()){
                int i = 0 ;
                while (true){
                    TimeUnit.SECONDS.sleep(1);
                    String info = "hello  "+i;
                    byteBuffer.clear();//清空缓冲区数据
                    byteBuffer.put(info.getBytes()); // 将数据写入缓冲区
                    byteBuffer.flip();//
                    while (byteBuffer.hasRemaining()){
                        System.out.println(byteBuffer);
                        socketChannel.write(byteBuffer);
                    }
                    i++;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
           try{
               if(socketChannel != null){
                   socketChannel.close();
               }
           }catch(Exception e){
               e.printStackTrace();
           }
        }

    }

    public static void main(String[] args) {
        Client client  = new Client();
        client.init();
    }
}
