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
import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class YM2612RegisterTest {

    /**
     * Test writing to registers.
     */
    @Test
    public void testWriteRegister() {
        YM2612 chip = new YM2612();
        chip.writeRegister(0x22, 0b1000);//LFO On;
        assertEquals(8, chip.readRegister(0x22));
    }
    
    
}
