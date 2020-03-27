package com.github.wmarkow.klondiklon.sound;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class SoundPlayer
{
    public void play(Sound sound, float soundDurationInSeconds, int playCount)
    {
        Timer timer = new Timer();
        timer.scheduleTask(new PlaySoundTask(sound, playCount, null), 0, soundDurationInSeconds, playCount);
        timer.start();
    }
    
    public void play(Sound sound, float soundDurationInSeconds, int playCount, SoundPlayerListener listener)
    {
        Timer timer = new Timer();
        timer.scheduleTask(new PlaySoundTask(sound, playCount, listener), 0, soundDurationInSeconds, playCount);
        timer.start();
    }

    private class PlaySoundTask extends Task
    {
        private Sound sound;
        private int requiredPlayCount;
        private int currentPlayCount;
        private SoundPlayerListener listener;

        public PlaySoundTask(Sound sound, int playCount, SoundPlayerListener listener) {
            this.sound = sound;
            this.requiredPlayCount = playCount;
            this.listener = listener;
        }

        @Override
        public void run()
        {
            currentPlayCount++;

            if (currentPlayCount > requiredPlayCount)
            {
                if (listener != null)
                {
                    listener.playSoundFinished();
                }
                listener = null;
            } else
            {
                sound.play();
            }
        }
    }
}
