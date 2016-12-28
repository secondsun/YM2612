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

import net.saga.console.music.sound_learnings.chip.YM2612;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author summers
 */
public class E02_LFOTest {

    
    /**
     * The Low Frequency Oscillator makes can be configured to run at 8
     * different frequencies.
     * 
     * The 4th bit is the enable bit and the 3 LSBs configure the LFO as follows
     * 
     * <pre>
     * 0: 3.98 Hz
     * 1: 5.56 Hz
     * 2: 6.02 Hz
     * 3: 6.37 Hz
     * 4: 6.88 Hz
     * 5: 9.63 Hz
     * 6: 48.1 Hz
     * 7: 72.2 Hz
     * </pre>
     * 
     * IE 0x1000 will enable the LFO and run it at 3.98 hz
     * 
     */
    @Test
    public void testFrequencies() {
        YM2612 chip = new YM2612();
        chip.writeRegister(YM2612.LFO_REG, 0b1000);
        int passZeroCount = 0;
        int result = -1;
        for (int i = 0; i < 44100; i++) {
            chip.nextSample();//Simulate one pulse of the clock;  The clock runs at 7.67 mHz?
            int newResult = chip.readLFO();
            if (result != newResult) {
                result = newResult;
                if (result == 0) {
                    passZeroCount++;
                }
                
            }
        }
        
        Assert.assertEquals(8, passZeroCount); // Should pass 0 8 times because you get less than 4 full waves. (The first wave starts at 0 which gives it an extra)
        
        
    }

}
