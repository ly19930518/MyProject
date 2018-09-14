package com.midi.demo;

import java.io.File;

public class AnalysisMain {
	// 获取midi
	private File midiFile;
	private ReadMidi readMidi;

	/**
	 * 构造方法，初始化数据
	 *
	 * @param filePath
	 *            文件路径
	 */
	public AnalysisMain(String filePath) {
		this.readMidi = new ReadMidi();
		this.midiFile = new File(filePath);
	}

	public static void main(String[] args) {
		AnalysisMain analysisMain = new AnalysisMain(
				"C:\\Users\\ZEVUN-ZY\\Desktop\\音轨\\音轨\\野子 苏运莹 (Voc+Lyric+Chord).mid");
		try {
			analysisMain.readMidi.readDateFIle(analysisMain.midiFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
