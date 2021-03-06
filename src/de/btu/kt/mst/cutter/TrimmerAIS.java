package de.btu.kt.mst.cutter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class TrimmerAIS extends AudioInputStream {

	private final AudioInputStream stream;
	private long startByte, endByte;
	private long t_bytesRead = 0;

	public TrimmerAIS(AudioFormat audioFormat,
			AudioInputStream audioInputStream) {
		super(new ByteArrayInputStream(new byte[0]), audioFormat,
				AudioSystem.NOT_SPECIFIED);
		stream = audioInputStream;
		
	}
	
	public void startSliceProcess (long startMilli, long endMilli) {
		// calculate where to start and where to end
				startByte = (long) ((startMilli / 1000)
						* stream.getFormat().getFrameRate() * stream.getFormat()
						.getFrameSize());
				endByte = (long) ((endMilli / 1000) * stream.getFormat().getFrameRate() * stream
						.getFormat().getFrameSize());
	}

	@Override
	public int available() throws IOException {
		return (int) (endByte - startByte - t_bytesRead);
	}

	@Override
	public int read(byte[] abData, int nOffset, int nLength) throws IOException {
		int bytesRead = 0;
		if (t_bytesRead < startByte) {
			do {
				bytesRead = (int) skip(startByte - t_bytesRead);
				t_bytesRead += bytesRead;
			} while (t_bytesRead < startByte);
		}
		if (t_bytesRead >= endByte)// end reached. signal EOF
			return -1;

		bytesRead = stream.read(abData, 0, nLength);
		if (bytesRead == -1)
			return -1;
		else if (bytesRead == 0)
			return 0;

		t_bytesRead += bytesRead;
		if (t_bytesRead >= endByte)// "trim" the extra by altering the number of
									// bytes read
			bytesRead = (int) (bytesRead - (t_bytesRead - endByte));

		return bytesRead;
	}

}
