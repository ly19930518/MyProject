package com.midi;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.xml.bind.SchemaOutputResolver;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取Midi文件信息
 *
 * @author Jonny
 *
 */
public class ReadMidi {

    public static void main(String[] args) {

    }

    /**
     * 获取midi信息
     *
     * @param midiFile
     * @throws Exception
     */
    public void readDateFIle(File midiFile) throws Exception {
        FileInputStream stream = new FileInputStream(midiFile);
        byte[] data = new byte[stream.available()];
        stream.read(data);
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        System.out.println("data:"+byteBuffer);
        // 调用获取MTHD信息方法得到头部信息
        HeaderChunks headerChunks = this.readHeaderChunks(byteBuffer);
        List<TrackChunk> trackChunklist = this.readTreackChunks(byteBuffer,
                headerChunks);
        this.outInforMidi(headerChunks, trackChunklist);
    }

    public void outInforMidi(HeaderChunks headerChunks,
                             List<TrackChunk> trackChunklist) {
        System.out.println("-------------MThd-----------------");
        System.out.println("头块类型:" + new String(headerChunks.getMidiId()));
        System.out.println("头块长度:" + headerChunks.getLength());
        System.out.println("格式:" + headerChunks.getFormat());
        System.out.println("音轨数:" + headerChunks.getTrackNumber());
        System.out.println("分区:" + headerChunks.getMidiTimeSet());

        for (int i = 0; i < trackChunklist.size(); i++) {
            System.out.println("-------------MTrk-----------------");
            System.out.println("音轨块" + i + ":"
                    + new String(trackChunklist.get(i).getMidiId()));
            System.out.println("音轨数据长度:" + trackChunklist.get(i).getLength());
        }
    }

    /**
     * 获取头部信息MThd
     *
     * @param buffer
     * @return
     */
    public HeaderChunks readHeaderChunks(ByteBuffer buffer) {
        HeaderChunks headerChunks = new HeaderChunks();
        for (int i = 0; i < headerChunks.getMidiId().length; i++) {
            headerChunks.getMidiId()[i] = (byte) (buffer.get());
        }
        headerChunks.setLength(buffer.getInt());
        headerChunks.setFormat(buffer.getShort());
        headerChunks.setTrackNumber(buffer.getShort());
        headerChunks.setMidiTimeSet(buffer.getShort());
        return headerChunks;
    }

    /**
     * 获取MTrk信息块
     *
     * @param buffer
     * @param headerChunks
     * @return
     */
    public List<TrackChunk> readTreackChunks(ByteBuffer buffer,
                                             HeaderChunks headerChunks) {

        ArrayList<TrackChunk> trackChunklist = new ArrayList<TrackChunk>();
        TrackChunk trackChunk;
        for (int i = 0; i < headerChunks.getTrackNumber(); i++) {
            trackChunk = new TrackChunk();
            for (int j = 0; j < trackChunk.getMidiId().length; j++) {
                trackChunk.getMidiId()[j] = (byte) (buffer.get());
            }
            trackChunk.setLength(buffer.getInt());
            byte[] data = new byte[trackChunk.getLength()];
            buffer.get(data);
            trackChunklist.add(trackChunk);
        }
        return trackChunklist;
    }

}