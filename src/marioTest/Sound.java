package marioTest;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	private Clip clip;
	public Sound(String path) {

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource(path));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getFrameRate(), 16, baseFormat.getChannels(), baseFormat.getChannels()*2,baseFormat.getSampleRate(),false);

			AudioInputStream dois = AudioSystem .getAudioInputStream(decodeFormat,ais);
			clip = AudioSystem.getClip();
			clip.open(dois);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	public void play() {
		if (clip == null) {
			return;
		}
		stop();
		clip.setFramePosition(0);
	}
	public void close() {
		stop();
		clip.close();
	}
	public void stop() {
		if (clip.isRunning()) {
			clip.stop();
		}
	}
}
