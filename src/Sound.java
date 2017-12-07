import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    private boolean released = false;
    private Clip[] clip = new Clip[2];
    private FloatControl volumeC = null;
    private boolean playing = false;

    public Sound(String name1,String name2) {
        try {
            File f = new File(name1);
            File f2 = new File(name2);

            AudioInputStream stream = AudioSystem.getAudioInputStream(f);
            clip[0] = AudioSystem.getClip();
            clip[0].open(stream);
            clip[0].addLineListener(new Listener());
            volumeC = (FloatControl) clip[0].getControl(FloatControl.Type.MASTER_GAIN);
            stream = AudioSystem.getAudioInputStream(f2);
            clip[1] = AudioSystem.getClip();
            clip[1].open(stream);
            clip[1].addLineListener(new Listener());
            volumeC = (FloatControl) clip[1].getControl(FloatControl.Type.MASTER_GAIN);
            released = true;
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
            exc.printStackTrace();
            released = false;
        }
    }

    //true если звук успешно загружен, false если произошла ошибка
    public boolean isReleased() {
        return released;
    }

    //проигрывается ли звук в данный момент
    public boolean isPlaying() {
        return playing;
    }

    //Запуск
	/*
	  breakOld определяет поведение, если звук уже играется
	  Если reakOld==true, о звук будет прерван и запущен заново
	  Иначе ничего не произойдёт
	*/
    public void play(boolean breakOld, int i) {
        if (released) {
            if (breakOld) {
                clip[i].stop();
                clip[i].setFramePosition(0);
                clip[i].start();
                playing = true;
            } else if (!isPlaying()) {
                clip[i].setFramePosition(0);
                clip[i].start();
                playing = true;
            }
        }
    }

    //То же самое, что и play(true)
    public void play(int i) {
        play(true,i);
    }

    //Останавливает воспроизведение
    public void stop(int i) {
        if (playing) {
            clip[i].stop();
        }
    }

    //Установка громкости
	/*
	  x долже быть в пределах от 0 до 1 (от самого тихого к самому громкому)
	*/
    public void setVolume(float x) {
        if (x<0) x = 0;
        if (x>1) x = 1;
        float min = volumeC.getMinimum();
        float max = volumeC.getMaximum();
        volumeC.setValue((max-min)*x+min);
    }

    //Возвращает текущую громкость (число от 0 до 1)
    public float getVolume() {
        float v = volumeC.getValue();
        float min = volumeC.getMinimum();
        float max = volumeC.getMaximum();
        return (v-min)/(max-min);
    }

    //Дожидается окончания проигрывания звука
    public void join() {
        if (!released) return;
        synchronized(clip) {
            try {
                while (playing) clip.wait();
            } catch (InterruptedException exc) {}
        }
    }

    private class Listener implements LineListener {
        public void update(LineEvent ev) {
            if (ev.getType() == LineEvent.Type.STOP) {
                playing = false;
                synchronized(clip) {
                    clip.notify();
                }
            }
        }
    }
}