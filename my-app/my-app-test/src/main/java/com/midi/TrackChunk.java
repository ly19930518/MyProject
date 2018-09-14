package com.midi;

public class TrackChunk {

    // MThd
    private byte[] midiId;

    // 长度
    private int length;

    public TrackChunk() {
        this.midiId = new byte[4];
    }

    public byte[] getMidiId() {
        return midiId;
    }

    public void setMidiId(byte[] midiId) {
        this.midiId = midiId;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}