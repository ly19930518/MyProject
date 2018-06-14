package com.test.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class test {
    public static void main(String[] args) {
        ByteBuffer byteBuffer =    ByteBuffer.allocate(1024);
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);//设置为非堵塞
            socketChannel.connect(new InetSocketAddress("127.0.0.1",8888));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
