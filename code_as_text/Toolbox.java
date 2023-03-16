package hdm.shared;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 * The Toolbox contains useful utility methods, e.g. for reading input from the
 * console oder input/output from/to files.
 * 
 * Using the toolbox saves time typing source code and makes exercise solutions
 * more succinct and understandable.
 * 
 * @version 6.0.1 (2018-11-14 wiest)
 */
public class Toolbox {

	/**
	 * Writes a prompt to console and returns user input as String.
	 * 
	 * @param prompt Prompt to be presented to the user
	 * @return Input received form user
	 */
	public static String readString(String prompt) {
		System.out.print(prompt);
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}

	/**
	 * Writes a prompt to console and returns user input as boolean. The inputs
	 * "true", "TRUE", "TrUe", "yes", "YES", "oui", "OUI" "ja", "JA", "y", "Y", "j",
	 * "J", "o", "O" return true, all other false.
	 * 
	 * @param prompt Prompt to be presented to the user
	 * @return Input received from user
	 */
	public static boolean readBoolean(String prompt) {
		String eingabe = readString(prompt);

		String words[] = { "true", "yes", "oui", "ja", "y", "j", "o" };
		for (String word : words) {
			if (word.equals(eingabe.toLowerCase())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Writes a prompt to console and returns user input as byte.
	 * 
	 * @param prompt Prompt to be presented to the user
	 * @return Input received from user
	 */
	public static byte readByte(String prompt) {
		return Byte.parseByte(readString(prompt));
	}

	/**
	 * Writes a prompt to console and returns user input as short.
	 * 
	 * @param prompt Prompt to be presented to the user
	 * @return Input received form user
	 */
	public static short readShort(String prompt) {
		return Short.parseShort(readString(prompt));
	}

	/**
	 * Writes a prompt to console and returns user input as int.
	 * 
	 * @param prompt Prompt to be presented to the user
	 * @return Input received from user
	 */
	public static int readInt(String prompt) {
		return Integer.parseInt(readString(prompt));
	}

	/**
	 * Writes a prompt to console and returns user input as long.
	 * 
	 * @param prompt Prompt to be presented to the user
	 * @return Input received from user
	 */
	public static long readLong(String prompt) {
		return Long.parseLong(readString(prompt));
	}

	/**
	 * Writes a prompt to console and returns user input as float.
	 * 
	 * @param prompt Prompt to be presented to the user
	 * @return Input received from user
	 */
	public static float readFloat(String prompt) {
		return Float.parseFloat(readString(prompt));
	}

	/**
	 * Writes a prompt to console and returns user input as double.
	 * 
	 * @param prompt Prompt to be presented to the user
	 * @return Input received from user
	 */
	public static double readDouble(String prompt) {
		return Double.parseDouble(readString(prompt));
	}

	/**
	 * Writes a prompt to console and returns user input as char.
	 * 
	 * @param Promt to be presented to the user
	 * @return Input received from user
	 */
	public static char readChar(String prompt) {
		return readString(prompt).charAt(0);
	}

	/**
	 * Returns a random int value in [min;max[.
	 * 
	 * @param min Lower boundary (inclusive) of random value
	 * @param max Upper boundary (exclusive) of random value
	 * @return Random int value
	 */
	public static int randomInt(int min, int max) {
		return (int) randomDouble(min, max);
	}

	/**
	 * Returns a random double value in [min;max[.
	 * 
	 * @param min Lower boundary (inclusive) of random value
	 * @param max Upper boundary (exclusive) of random value
	 * @return Random double value
	 */
	public static double randomDouble(double min, double max) {
		return min + Math.random() * (max - min);
	}

	/**
	 * Returns the contents of a text file that is encoded in UTF-8 (Unicode).
	 * 
	 * @param filepath Input file path, e.g. "my/directory/myfile.txt".
	 * @return File contents as String or @null, if file could not be found/read.
	 */
	public static String readFile(String filepath) {
		try {
			// InputStream read binary data from file
			InputStream is = new FileInputStream(filepath);
			// Reader converts binary data into characters
			Reader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));

			StringBuffer contents = new StringBuffer();
			char[] buffer = new char[1024];
			int numCharactersRead = 0;
			do {
				numCharactersRead = reader.read(buffer);
				if (numCharactersRead > -1) {
					contents.append(buffer, 0, numCharactersRead);
				}
			} while (numCharactersRead > -1);

			reader.close();
			return contents.toString();
		} catch (IOException ioe) {
			// An exception occurred during opening/reading the file
			ioe.printStackTrace(System.err);
			return null;
		}
	}

	/**
	 * Writes the contents of String into a text file that is encoded in UTF-8
	 * (Unicode).
	 * 
	 * @param filepath Output file path, e.g. "my/directory/myfile.txt"
	 * @param contents String content to write into file
	 */

	public static void writeFile(String filepath, String contents) {
		try {
			// OutputStream writes binary data into file
			OutputStream os = new FileOutputStream(filepath);
			// Writer converts characters into binary data
			Writer writer = new OutputStreamWriter(os, "utf-8");

			writer.write(contents);
			writer.close();
		} catch (IOException ioe) {
			// An exception occurred during opening/writing the file
			ioe.printStackTrace(System.err);
		}
	}

	/**
	 * Returns a time duration as formatted string (hh:mm:ss.mmm). E.g. 67.2849
	 * seconds will be formatted as "00:01:07.285"
	 * 
	 * @param duration Duration in seconds
	 * @return Duration in a human-friendly format.
	 */
	public static String formatDuration(double duration) {
		int milliseconds = (int) (duration * 1000 + 0.5) % 1000; // round third digit
		int seconds = ((int) duration) % 60;
		int minutes = ((int) duration / 60) % 60;
		int hours = ((int) duration / (60 * 60));
		return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
	}

	/**
	 * Returns (e.g. file) size in bytes in a human-friendly format, e.g. "12
	 * Bytes", "48.2 KiB", "2.37 MiB", "182 GiB". This format uses a total maximum
	 * of three digits, e.g. instead of "1200 Bytes" if will return "1.17 KiB".
	 * Note: 1 KiB contains 1024 Bytes, 1 MiB contains 1024 KiB, and so on.
	 * 
	 * @param size File size in bytes.
	 * @return File size in a human-friendly format.
	 */
	public static String formatSize(long size) {
		String[] magnitudes = { "Bytes", "KiB", "MiB", "GiB", "TiB", "PiB" };

		// Find suitable magnitude
		float compactSize = size;
		int magnitude = 0;
		// Has current formatting more than 3 digits?
		while (compactSize >= 1000) {
			// Increase magnitude
			compactSize /= 1024;
			magnitude++;
		}

		// Format value
		String formattedSize;
		// Number of post-decimal digits depends on pre-decimal digits.
		if ((compactSize >= 100) || (magnitude == 0)) {
			// Three pre-decimal digits, zero post-decimal digits.
			formattedSize = String.format("%3.0f", compactSize);
		} else if (compactSize >= 10) {
			// Two pre-decimal digits, one post-decimal digits.
			formattedSize = String.format("%2.1f", compactSize);
		} else {
			// One pre-decimal digits, two post-decimal digits.
			formattedSize = String.format("%1.2f", compactSize);
		}

		// Append unit
		return formattedSize + " " + magnitudes[magnitude];
	}

	/**
	 * Pauses program execution for given time.
	 * 
	 * @param millisecs Duration of pause (in milliseconds)
	 */
	public static void pause(long millisecs) {
		try {
			Thread.sleep(millisecs);
		} catch (InterruptedException ie) {
			ie.printStackTrace(System.err);
		}
	}

	/**
	 * Downloads data from a given URL and stores it into a local file.
	 * 
	 * @param address  URL to be downloaded
	 * @param filepath Local file to write data into
	 * @return @true, if download was successful. @false otherwise.
	 */
	public static boolean downloadIntoFile(String address, String filepath) {
		try {
			URL url = new URL(address);
			Files.copy(url.openStream(), Paths.get(filepath), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Downloads data from a given URL and stores it into a String. Useful for
	 * textual data, e.g. XML or JSON files.
	 * 
	 * @param address URL to be downloaded
	 * @return Downloaded data as String.
	 */
	public static String downloadIntoString(String adresse) {
		try {
			URL url = new URL(adresse);
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
				StringBuffer buffer = new StringBuffer();
				int read;
				char[] chars = new char[1024];
				while ((read = reader.read(chars)) != -1) {
					buffer.append(chars, 0, read);
				}
				return buffer.toString();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Reads an image file and returns the data as 8-bit grayscale image (0=black,
	 * 255=white) in a two-dimensional array. First dimension of the array are
	 * columns, second are rows. For RGB files, only the green channel is
	 * considered.
	 * 
	 * @param filepath Input image file path, e.g. "my/directory/myimage.jpg".
	 * @return Two-dimensional array with 8-bit grayscale data or <code>null</code>,
	 *         if a problem occurred during opening/reading the image file.
	 */
	public static short[][] loadImage(String filepath) {
		short[][] imageArray = null;
		try {
			BufferedImage image = ImageIO.read(new File(filepath));
			imageArray = new short[image.getWidth()][image.getHeight()];
			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					// Extract green channel and rescale to [0;255].
					imageArray[x][y] = (short) ((image.getRGB(x, y) & 0xFF00) >> 8);
				}
			}
		} catch (IOException e) {
			System.err.println("File '" + filepath + "' could not be read: " + e.getMessage());
		}
		return imageArray;
	}

	/**
	 * Displays data to a two-dimensional array in a window (0=black, 255=white).
	 * First dimension of the array are columns, second are rows.
	 * 
	 * @param imageArray Two-dimensional array with 8-bit grayscale data
	 */
	public static void showImage(short[][] imageArray) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new ImagePanel(imageArray));
		frame.setResizable(false);
		frame.pack();

		frame.setVisible(true);
	}

	@SuppressWarnings("serial")
	static private class ImagePanel extends JPanel {
		BufferedImage image;

		public ImagePanel(short[][] imageArray) {
			int width = imageArray.length;
			int height = imageArray[0].length;
			setPreferredSize(new Dimension(width, height));

			image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					int b = imageArray[x][y];
					image.setRGB(x, y, (b << 16) + (b << 8) + b);
				}
			}

		}

		@Override
		protected void paintComponent(Graphics g) {
			g.drawImage(image, 0, 0, null);
		}
	}

	/**
	 * Plays an audio file asynchronously, i.e. program will <em>not</em> pause,
	 * until the entire file was played. Currently, only WAV files are supported (no
	 * MP3 files).
	 * 
	 * @param filepath path to audio file, either relative to current working
	 *                 directory ("my/directory/boom.wav"), absolute path
	 *                 ("C:/temp/boom.wav") or absolute class path
	 *                 ("/hdm/processing/sound/boom.wav").
	 */
	public static void playAudioFileAsychronously(final String filepath) {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				playAudioFile(filepath);
				return null;
			}
		}.execute();
	}

	/**
	 * Plays an audio file asynchronously and repeats endless, i.e. program will
	 * <em>not</em> pause, until the entire file was played. The file will be loaded
	 * into RAM entirely. There might be short breaks in the audio between
	 * repetitions. Currently, only WAV files are supported (no MP3 files).
	 * 
	 * @param filepath path to audio file, either relative to current working
	 *                 directory ("my/directory/boom.wav"), absolute path
	 *                 ("C:/temp/boom.wav") or absolute class path
	 *                 ("/hdm/processing/sound/boom.wav").
	 */
	public static void playAudioFileAsychronouslyLooped(final String filepath) {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				short[] audioData = loadAudioFile(filepath);
				while (true) {
					playArray(audioData);
				}
			}
		}.execute();
	}

	/**
	 * Plays an audio file synchronously, i.e. program will pause, until the entire
	 * file was played. Currently, only WAV files are supported (no MP3 files).
	 * 
	 * @param filepath path to audio file, either relative to current working
	 *                 directory ("my/directory/boom.wav"), absolute path
	 *                 ("C:/temp/boom.wav") or absolute class path
	 *                 ("/hdm/processing/sound/boom.wav").
	 */
	public static void playAudioFile(String filepath) {

		File soundFile = new File(filepath);

		InputStream is = null;
		try {
			if (soundFile.exists()) {
				// File exists in file system
				is = new BufferedInputStream(new FileInputStream(soundFile));
			} else {
				// Try to load file as resource from class path
				// Path to resource must be absolute (= start with "/").
				is = new BufferedInputStream(Toolbox.class.getResourceAsStream(filepath));
			}

			// Start audio output
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(is);
			AudioFormat audioFormat = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
			SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open(audioFormat);
			sourceLine.start();

			// Read file in chunks and pass chunks to audio output
			final int BUFFER_SIZE = 100 * 1024; // in Bytes
			byte[] audioDataBuffer = new byte[BUFFER_SIZE];
			int numberBytesRead = 0;
			while (numberBytesRead != -1) {
				numberBytesRead = audioStream.read(audioDataBuffer, 0, audioDataBuffer.length);
				if (numberBytesRead > 0) {
					sourceLine.write(audioDataBuffer, 0, numberBytesRead);
				}
			}

			// Wait for audio output to finish, then close audio output lines.
			sourceLine.drain();
			sourceLine.close();

		} catch (UnsupportedAudioFileException uafe) {
			System.err.println("Audio format '" + filepath + "' ist not supported: " + uafe.getMessage());
		} catch (IOException ioe) {
			System.err.println("Problem while reading '" + filepath + "': " + ioe.getMessage());
		} catch (LineUnavailableException lue) {
			System.err.println("Problem during audio output of '" + filepath + ": " + lue.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					System.err.println(e);
				}
			}
		}
	}

	/**
	 * Plays data in an array of shorts asynchronously.
	 * 
	 * @param samples Array of short values (16-bit signed integers, 44.1 kHz
	 *                sampling frequency)
	 */
	public static void playArrayAsynchronuously(final short[] samples) {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				playArray(samples);
				return null;
			}
		}.execute();
	}

	/**
	 * 
	 * Plays data in an array of shorts synchronously. Program pauses until all data
	 * has been played.
	 * 
	 * @param samples Array of short values (16-bit signed integers, 44.1 kHz
	 *                sampling frequency)
	 */
	public static void playArray(final short[] samples) {

		try {
			final int BITS_PER_BYTE = 8;
			final int BITS_PER_SAMPLE = 16;
			final float SAMPLE_RATE = 44100.0f;
			final int NUM_CHANNELS = 1;

			// Start audio output
			AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, BITS_PER_SAMPLE, NUM_CHANNELS, true, true);
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
			SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open(audioFormat);
			sourceLine.start();

			// Convert short values into byte array
			byte[] audioDataBuffer = new byte[samples.length * (BITS_PER_SAMPLE / BITS_PER_BYTE)];
			for (int i = 0; i < samples.length; i++) {
				audioDataBuffer[i * 2] = (byte) ((samples[i] >> 8) & 0xFF);
				audioDataBuffer[i * 2 + 1] = (byte) (samples[i] & 0xFF);
			}

			// Play byte array
			sourceLine.write(audioDataBuffer, 0, audioDataBuffer.length);

			// Wait for audio output to finish, then close audio output lines.
			sourceLine.drain();
			sourceLine.close();
		} catch (LineUnavailableException lue) {
			System.err.println("Problem during audio output of array: " + lue.getMessage());
		}
	}

	/**
	 * Loads an audio file into an array of short values. Only WAV files (16-bit
	 * signed integers, 44.1 kHz, mono or stereo) supported.
	 * 
	 * @param filepath path to audio file, either relative to current working
	 *                 directory ("my/directory/boom.wav"), absolute path
	 *                 ("C:/temp/boom.wav") or absolute class path
	 *                 ("/hdm/processing/sound/boom.wav").
	 * @return Audio data in an array of short values
	 */
	public static short[] loadAudioFile(String filepath) {
		short[] samples = null;
		File soundFile = new File(filepath);

		InputStream is = null;
		try {
			if (soundFile.exists()) {
				// File exists in file system
				is = new BufferedInputStream(new FileInputStream(soundFile));
			} else {
				// Try to load file as resource from class path
				// Path to resource must be absolute (= start with "/").
				is = new BufferedInputStream(Toolbox.class.getResourceAsStream(filepath));
			}

			// Open audio playback
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(is);
			AudioFormat audioFormat = audioStream.getFormat();

			// Check, if audio is mono or stereo (more than 2 channels are not supported)
			int numChannels = audioFormat.getChannels();
			if (numChannels > 2) {
				System.err.println("File '" + filepath + "' contains " + numChannels
						+ " channels. Only up to 2 channels are supported.");
				return samples;
			}

			// Check, if data is 16 bit/sample
			if (audioFormat.getSampleSizeInBits() != 16) {
				System.err.println("File" + filepath + "' uses " + audioFormat.getSampleSizeInBits()
						+ " bits/sample. Only 16 bits/sample are supported.");
				return samples;
			}

			long numSamples = audioStream.getFrameLength();
			samples = new short[(int) numSamples];

			final int BUFFER_SIZE = 100 * 1024; // in Bytes
			byte[] audioDataBuffer = new byte[BUFFER_SIZE];

			int sampleIndex = 0;
			int numberBytesRead = 0;
			while (numberBytesRead != -1) {
				// Read file in chunks and store them in array of short values
				numberBytesRead = audioStream.read(audioDataBuffer, 0, audioDataBuffer.length);
				if (numberBytesRead > 0) {
					for (int i = 0; i < numberBytesRead; i = i + 2 * numChannels) {
						if (numChannels == 1) {
							// Store mono signal in short array
							samples[sampleIndex++] = (short) (audioDataBuffer[i + 1] << 8
									| (audioDataBuffer[i] & 0xff));
						} else {
							// Mix-down stereo signal into short array
							short sampleChannel1 = (short) (audioDataBuffer[i + 1] << 8 | (audioDataBuffer[i] & 0xff));
							short sampleChannel2 = (short) (audioDataBuffer[i + 3] << 8
									| (audioDataBuffer[i + 2] & 0xff));
							samples[sampleIndex++] = (short) ((sampleChannel1 + sampleChannel2) / 2);
						}
					}
				}
			}
		} catch (UnsupportedAudioFileException uafe) {
			System.err.println("Format of audio file '" + filepath + "' is not supported: " + uafe.getMessage());
		} catch (IOException ioe) {
			System.err.println("Problem while reading '" + filepath + "': " + ioe.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					System.err.println(e);
				}
			}
		}

		return samples;
	}
}
