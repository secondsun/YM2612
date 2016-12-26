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
package net.saga.console.music.sound_learnings;

import static java.lang.Thread.sleep;
import net.saga.console.music.sound_learnings.chip.YM2612;

/**
 *
 * @author summers
 */
public final class Main {

    static boolean running = true;

    public static void main(String args[]) throws InterruptedException {
//        final Audio a = new Audio.AudioBuilder().build();
//        Thread t = new Thread(() -> {
//            while (running) {
//                a.play();
//            }
//        });
//        t.start();
//
//        Thread.sleep(5000);
//        running = false;
//        a.stop();

        YM2612 chip = new YM2612();
        /* YM2612 Test code */
        /* Hat tip to https://github.com/skywodd/avr_vgm_player/blob/master/ym2612_test/ym2612_test.c*/
        chip.writeRegister(0x22, 0x00); // LFO off
        chip.writeRegister(0x27, 0x00); // Note off (channel 0)
        chip.writeRegister(0x28, 0x01); // Note off (channel 1)
        chip.writeRegister(0x28, 0x02); // Note off (channel 2)
        chip.writeRegister(0x28, 0x04); // Note off (channel 3)
        chip.writeRegister(0x28, 0x05); // Note off (channel 4)
        chip.writeRegister(0x28, 0x06); // Note off (channel 5)
        chip.writeRegister(0x2B, 0x00); // DAC off
        chip.writeRegister(0x30, 0x71); //
        chip.writeRegister(0x34, 0x0D); //
        chip.writeRegister(0x38, 0x33); //
        chip.writeRegister(0x3C, 0x01); // DT1/MUL
        chip.writeRegister(0x40, 0x23); //
        chip.writeRegister(0x44, 0x2D); //
        chip.writeRegister(0x48, 0x26); //
        chip.writeRegister(0x4C, 0x00); // Total level
        chip.writeRegister(0x50, 0x5F); //
        chip.writeRegister(0x54, 0x99); //
        chip.writeRegister(0x58, 0x5F); //
        chip.writeRegister(0x5C, 0x94); // RS/AR 
        chip.writeRegister(0x60, 0x05); //
        chip.writeRegister(0x64, 0x05); //
        chip.writeRegister(0x68, 0x05); //
        chip.writeRegister(0x6C, 0x07); // AM/D1R
        chip.writeRegister(0x70, 0x02); //
        chip.writeRegister(0x74, 0x02); //
        chip.writeRegister(0x78, 0x02); //
        chip.writeRegister(0x7C, 0x02); // D2R
        chip.writeRegister(0x80, 0x11); //
        chip.writeRegister(0x84, 0x11); //
        chip.writeRegister(0x88, 0x11); //
        chip.writeRegister(0x8C, 0xA6); // D1L/RR
        chip.writeRegister(0x90, 0x00); //
        chip.writeRegister(0x94, 0x00); //
        chip.writeRegister(0x98, 0x00); //
        chip.writeRegister(0x9C, 0x00); // Proprietary
        chip.writeRegister(0xB0, 0x32); // Feedback/algorithm
        chip.writeRegister(0xB4, 0xC0); // Both speakers on
        chip.writeRegister(0x28, 0x00); // Key off
        chip.writeRegister(0xA4, 0x22);	// 
        chip.writeRegister(0xA0, 0x69); // Set frequency

        /* Program loop */
        for (;;) {
            sleep(1000);
            chip.writeRegister(0x28, 0xF0); // Key on
            sleep(1000);
            chip.writeRegister(0x28, 0x00); // Key off
        }

    }
}
