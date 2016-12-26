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

public class YM2612 {

    public static final int LFO_REG = 0x22;//LFO Register Location
    
    private final int[] registers = new int[1000];//TODO: make correct size
    
    public void writeRegister(int register, int data) {
        data = data & 0xFF;//Truncate to 8 bits
        registers[register] = data;
    }
    
    public int readRegister(int register) {
        return registers[register] & 0xFF;
    }
    
}
