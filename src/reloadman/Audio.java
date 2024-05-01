package reloadman;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class Audio {
    private Clip clip;
    private FloatControl volumeControl;

    public Audio(String filePath, float volume) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playSound() {
        if (clip != null && !clip.isRunning()) {
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start();
        }
    }

    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {
            volumeControl.setValue(volume);
        }
    }

    public void close() {
        if (clip != null) {
            clip.close();
        }
    }
}
