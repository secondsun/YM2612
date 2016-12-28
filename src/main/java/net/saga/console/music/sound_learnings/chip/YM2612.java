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
package net.saga.console.music.sound_learnings.chip;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class YM2612 {

    public static final int LFO_REG = 0x22;//LFO Register Location

    private final int[] registers = new int[1000];//TODO: make correct size
    private int data = 0; // Output of chip

    private final int clockRate = 7670000; //7.67 MhZ
    private final int sampleRate = 44100; // 44.1khz
    private LFO lfo = new LFO();

    public void writeRegister(int register, int data) {
        data = data & 0xFF;//Truncate to 8 bits
        registers[register] = data;
    }

    public int readRegister(int register) {
        return registers[register] & 0xFF;
    }

    public void cycle() {
        lfo.advance();
    }

    public int read() {
        return 0;
    }

    public int readLFO() {
        return lfo.out;
    }


    private final class LFO {

        final BigDecimal radians = BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(Math.PI));

        int masterClockCounter = 0;
        BigDecimal radiansPerClock = BigDecimal.ZERO;

        BigDecimal timerInterval = BigDecimal.ZERO;
        boolean enabled = false;

        int out = 0;
        int oldRegister = 0;

        final double[] frequencyValues = {3.98d, 5.56d, 6.02d, 6.37d, 6.88d, 9.63d, 48.1d, 72.2d};

        void init() {
            updateFromRegister();

        }

        int advance() {
            updateFromRegister();
            masterClockCounter++;
            if (masterClockCounter > clockRate) { // Just handling overflows
                masterClockCounter-=clockRate;    // -= instead of 0 because I think there may be weird off by ones
            }
            out = (int)(Math.sin(masterClockCounter * radiansPerClock.doubleValue()) * 127);
            return out;
        }

        private void updateFromRegister() {
            int newReg = readRegister(LFO_REG);
            if (newReg != oldRegister) { //Register has changed, update
                oldRegister = newReg;
                enabled = (oldRegister & 0x1000) > 0;
                masterClockCounter = 0;
                radiansPerClock = radians.multiply(BigDecimal.valueOf(frequencyValues[oldRegister & 0b0111]).divide(BigDecimal.valueOf(clockRate), 8, RoundingMode.HALF_UP));
            }
        }

    }

}
