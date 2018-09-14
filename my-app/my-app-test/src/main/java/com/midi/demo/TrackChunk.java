package com.midi.demo;

import com.util.HexUtil;
import org.apache.shiro.codec.Hex;

import java.util.Arrays;
import java.util.logging.Logger;
public class TrackChunk {
	private byte[] midiId;
	// 长度
	private int length;
	private byte[] midiData;

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

	public void setData(byte[] data) {
		this.midiData = data;
	}

	public String getData() {
		/*StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < midiData.length; i++) {
			stringBuffer.append(midiData[i]);
		}*/
//		return String.valueOf(stringBuffer);

		return HexUtil.BinaryToHexString(midiData);
	}

	public static void main(String[] args) {
		//{-1, 3, 4, -46, -80, -41, -45,}
		byte[] b = "卧室".getBytes();
		System.out.println(Arrays.toString(b));
		System.out.println(new String(b));
	}
}
