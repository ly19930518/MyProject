package com.test;

import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;

public class webSocketClient {
    public static WebSocketClient client;
    public void test(){
        {
            try {
                client = new WebSocketClient(new URI("ws://192.168.2.15:8900/websocket?userid=b9176a5835044bb4b6a7ecc394e439e3&pwd=8f4b2ff0f31ac9f0"),new Draft_6455()) {
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {

                    }

                    @Override
                    public void onMessage(String msg) {
                        System.out.println("收到消息=========="+msg);
                        if(msg.equals("over")){
                            client.close();
                        }
                    }

                    @Override
                    public void onClose(int i, String s, boolean b) {
                        System.out.println("链接已关闭");
                    }

                    @Override
                    public void onError(Exception e){
                        e.printStackTrace();
                        System.out.println("发生错误已关闭");
                    }
                };
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            client.connect();
            System.out.println(client.getDraft());
            while(!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
                System.out.println("正在连接...");
            }
            //连接成功,发送信息
            client.send("哈喽,连接一下啊");

        }
    }
    public static void main(String[] args) throws InterruptedException {
       for(int i = 0 ; i < 1500 ; i ++){
           new Thread() {
               public void run(){
                   webSocketClient w = new webSocketClient();
                   w.test();
               }
           }.start();
           Thread.sleep(50);
       }
    }


}
