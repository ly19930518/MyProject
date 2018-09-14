package com.midi.demo;

import com.util.HexUtil;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        try {
            Sequence sequence = MidiSystem.getSequence(new File("C:\\Users\\ZEVUN-ZY\\Desktop\\音轨\\音轨\\1.mid"));

            long length = sequence.getMicrosecondLength(); // 获取序列的总时间（微秒）
            int trackCount = sequence.getTracks().length; // 获取序列的音轨数
            float divType = sequence.getDivisionType(); // 获取序列的（计时方式？）
            // 原文 ： Obtains the timing division type for this sequence.
            int resolution = sequence.getResolution(); // 获
            System.out.println(sequence.getMicrosecondLength());
            System.out.println(sequence.getResolution());
            System.out.println("序列："+length+" 音轨数："+trackCount+" 计时方式："+divType +"获取序列的时间解析度 :"+resolution);

            // 花费时间 = （tick / 时间解析度） * （60 / bpm）


            /**
             * 如果存在多个 bpm
             * 变更时间 = 话费时间 - （变更开始tick / 时间解析度） * （60 / bpm(当前)）
             * 最后时间 =  花费时间 -  变更时间
             */
            //System.exit(0);

           /* MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo(); // 获取所有 midi 设备的信息
            Sequencer sequencer = MidiSystem.getSequencer(); // 获取默认的音序器
            Synthesizer synthsizer = MidiSystem.getSynthesizer(); // 获取默认的合成器
            Receiver receiver = MidiSystem.getReceiver(); // 获取默认的接收器*/


            Sequencer sequencer = MidiSystem.getSequencer();
            if(sequencer == null){
                throw new IOException("未找到可用音序器！");
            }


            Track[] tracks = sequence.getTracks();
            Track t  = tracks[0];
            /*for(int i = 0 ;i < t.size();i++){
                MidiEvent event = t.get(i);
                byte[] buf1 = event.getMessage().getMessage();
                buf1[0] = 0;
//                System.out.println(event.getMessage().getMessage().getClass().getSimpleName());
                System.out.println(new String(buf1,"GB2312"));
            }*/
            int index = 0 ;
            for(Track track:tracks){
                System.out.println(">>>>>>>>>track"+index+"<<<<<<<<<<<<<"+track.size());
                for(int i = 0 ;i < track.size();i++){
                    MidiEvent midiEvent = track.get(i);

                    MidiMessage midiMessage = midiEvent.getMessage();
                    midiMessage.getLength();
                    byte[] buf = midiMessage.getMessage();
                    byte[] bstate = {buf[0]};
                    String value = null;
                    String type = "";
                    String event;
                    long times = midiEvent.getTick() ;
                    event = HexUtil.BinaryToHexString(bstate);

                    //不同状态不同格式
                    if(buf[0] == -1){
                        //非事件
                        byte[] types = {buf[1]};
                       if(buf.length-2 != 0){
                           byte[] nbuf = new byte[buf.length-2];
                           for(int k = 2 ; k < buf.length;k++){
                               nbuf[k-2] = buf[k];
                           }
                           type = HexUtil.BinaryToHexString(types);
                           System.out.println(Arrays.toString(nbuf));
                           value = new String(nbuf,"GB2312");
                       }else{
                           System.out.println("*");
                       }
                    }else{
                        buf[0] = 0;
                        byte[] nbuf = new byte[buf.length-1];
                        for(int k = 1 ; k < buf.length ; k++){
                            nbuf[k-1] = buf[k];
                        }
                        value = HexUtil.BinaryToHexString(nbuf);
                    }
                    System.out.println("Event:"+event +" "+ type+" data:"+value+" time:"+times +" hex:"+Long.toHexString(times) +" length:"+ midiMessage.getLength());

                }
            }

//            sequencer.setSequence(sequence);
//            sequencer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
