/**
 * sound_learnings - TDD YM2612 implementation
 * Copyright © 2016 SecondSun (Summers Pittman) (secondsun@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.saga.console.music.sound_learnings.util;

import java.util.function.IntSupplier;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import net.saga.console.music.sound_learnings.chip.YM2612;

/**
 * This class is a helper class for emitting, playing, and viewing audio data.
 *
 * Audio is a instrument with configurable sample size, sample rate, and carrier
 * 
 * It outputs signed little endian mono sound.
 * 
 * @author summers
 */
public final class Audio {

    private final AudioFormat af;
    private SourceDataLine sdl;
    private byte[] buf = new byte[1];
    private int carrier;
    
    private final IntSupplier STREAM = (new IntSupplier() {

        int i = 0;

        @Override
        public int getAsInt() {
            double angle = i / ((float) sampleRate / carrier) * 2.0 * Math.PI;
            i++;
            chip.nextSample();
            return (byte) (((Math.sin(angle)) / 2d + ((double)chip.readLFO()/127d) / 2d ) * 120);
            //return (byte) ((Math.sin(angle) * 120));
        }
    });

    private boolean playing = false;
    private final int sampleRate;
    private final int bitsPerSample;
    private final YM2612 chip;
    
    private Audio(int carrier, int sampleRate, int bitsPerSample, YM2612 chip) {
        af = new AudioFormat((float) sampleRate, bitsPerSample, 1, true, false);
        try {
            sdl = AudioSystem.getSourceDataLine(af);
            sdl.open();

        } catch (LineUnavailableException ex) {
            sdl = null;
        }
        this.carrier = carrier;
        this.sampleRate = sampleRate;
        this.bitsPerSample = bitsPerSample;
        this.chip = chip;
                
    }


    /**
     * Plays 1/60 second of C.
     */
    public void play() {
        if (!playing) {
            sdl.start();
        }

        for (int i = 0; i < (sampleRate / 60); i++) {
            int number = STREAM.getAsInt();
            System.out.println(number);
            buf[0] = (byte) number;
            sdl.write(buf, 0, 1);
        }

    }

    /**
     * Stops and cleans up sound.
     */
    public void stop() {
        if (playing) {
            sdl.stop();
            playing = false;
        }
    }

    public void setCarrier(int carrier) {
        this.carrier = carrier;
    }
    
    public static final class AudioBuilder {

        private int carrier = 261;
        private int sampleRate = 44100;
        private int bitsPerSample = 8;
        private YM2612 chip;
        
        public AudioBuilder() {
        }

        public int getCarrier() {
            return carrier;
        }

        public void setCarrier(int carrier) {
            this.carrier = carrier;
        }

        public int getSampleRate() {
            return sampleRate;
        }

        public void setSampleRate(int sampleRate) {
            this.sampleRate = sampleRate;
        }

        public int getBitsPerSample() {
            return bitsPerSample;
        }

        public void setBitsPerSample(int bitsPerSample) {
            this.bitsPerSample = bitsPerSample;
        }
        
        public Audio build() {
            return new Audio(carrier, sampleRate, bitsPerSample, chip);
        }

        public AudioBuilder setChip(YM2612 chip) {
            this.chip = chip;
            return this;
        }
        
    }
    
}
