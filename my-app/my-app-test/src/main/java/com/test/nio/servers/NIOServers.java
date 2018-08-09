package com.test.nio.servers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 利用NIO 实现 tcp 服务端
 */
public class NIOServers {

    private static final int PORT = 8888;
    private static final int BUF_SIZE = 1024;
    private static final int TIME_OUT = 3000;


    /**
     * 处理 新的连接
     * @param key
     * @throws IOException
     */
    public static void handleAccept(SelectionKey key) throws IOException{
        ServerSocketChannel ssChannel = (ServerSocketChannel)key.channel();
        SocketChannel sc = ssChannel.accept();
        if(null != sc){
            System.out.println("新链接");
            sc.configureBlocking(false);
            //还可以在用register()方法向Selector注册Channel的时候附加对象
            sc.register(key.selector(),SelectionKey.OP_READ,"1");

            //Object attachedObj = selectionKey.attachment(); 获取通道表示
            System.out.println(key.attachment()+"链接成功");
        }
    }

    /**
     * 处理正在读的内容
     * @param key
     * @throws IOException
     */
    public static void handleRead(SelectionKey key) throws IOException{
        SocketChannel sc = (SocketChannel)key.channel();
        ByteBuffer buf = ByteBuffer.allocate(BUF_SIZE); //(ByteBuffer)key.attachment();
        long bytesRead = sc.read(buf);
        while(bytesRead>0){
            buf.flip();
            StringBuffer stringBuffer = new StringBuffer();
            while(buf.hasRemaining()){
                stringBuffer.append((char)buf.get());
            }
            System.out.println("id:"+key.attachment() +" / "+stringBuffer.toString());
            buf.clear();
            bytesRead = sc.read(buf);
        }
        if(bytesRead == -1){
            sc.close();
        }
    }

    /**
     * 处理正在写
     * @param key
     * @throws IOException
     */
    public static void handleWrite(SelectionKey key) throws IOException{
        ByteBuffer buf = (ByteBuffer)key.attachment();
        buf.flip();
        SocketChannel sc = (SocketChannel) key.channel();
        while(buf.hasRemaining()){
            sc.write(buf);
        }
        buf.compact();
    }

    public static void selector(){
        Selector selector = null;
        ServerSocketChannel ssc = null;
        try{
            selector = Selector.open();
            ssc = ServerSocketChannel.open();//Selector的创建
            ssc.socket().bind(new InetSocketAddress(PORT));//监听端口
            ssc.configureBlocking(false);//非堵塞
            ssc.register(selector,SelectionKey.OP_ACCEPT);//selector注册监听
            //Selector一起使用时，Channel必须处于非阻塞模式下。这意味着不能将FileChannel与Selector一起使用，
            // 因为FileChannel不能切换到非阻塞模式。而套接字通道都可以。

            while (true){
                if(selector.select(TIME_OUT) == 0){
                    System.out.println("==");
                    continue;
                }
                //迭代当前活动的
                Iterator<SelectionKey> iter  = selector.selectedKeys().iterator();
                while (iter.hasNext()){
                    SelectionKey key = iter.next();
                    //连接成功 准备接收
                    if(key.isAcceptable()){
                        handleAccept(key);
                    }
                    //读的操作
                    if(key.isReadable()){
                        handleRead(key);
                    }
                    //写操作
                    if(key.isWritable() && key.isValid()){
                        handleWrite(key);
                    }
                    //正连接
                    if(key.isConnectable()){
                        System.out.println("isConnectable = true");
                    }
                    iter.remove();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(selector!=null){
                    selector.close();
                }

                if(ssc!=null){
                    ssc.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        selector();
    }
}
