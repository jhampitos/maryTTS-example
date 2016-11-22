package br.com.citta;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;

import org.apache.commons.io.FileUtils;

import marytts.LocalMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.MaryAudioUtils;

public class App {


	public static void main(String[] args) throws MaryConfigurationException {
		String outputFileName = null;

		outputFileName = "c:\\jhampa.wav";

		// get input
		String inputText = null;
		String inputFileName = "c:\\jhampa.txt";
		File file = new File(inputFileName);
		try {
			inputText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			System.err.println("Could not read from file " + inputFileName + ": " + e.getMessage());
			System.exit(1);
		}

		// init mary
		LocalMaryInterface mary = null;
		try {
			mary = new LocalMaryInterface();
		} catch (MaryConfigurationException e) {
			System.err.println("Could not initialize MaryTTS interface: " + e.getMessage());
			throw e;
		}

		// synthesize
		AudioInputStream audio = null;
		try {
			audio = mary.generateAudio(inputText);
		} catch (SynthesisException e) {
			System.err.println("Synthesis failed: " + e.getMessage());
			System.exit(1);
		}

		// write to output
		double[] samples = MaryAudioUtils.getSamplesAsDoubleArray(audio);
		try {
			MaryAudioUtils.writeWavFile(samples, outputFileName, audio.getFormat());
			System.out.println("Output written to " + outputFileName);
		} catch (IOException e) {
			System.err.println("Could not write to file: " + outputFileName + "\n" + e.getMessage());
			System.exit(1);
		}
		
	}
}