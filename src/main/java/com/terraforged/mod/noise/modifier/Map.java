/*
 *
 * MIT License
 *
 * Copyright (c) 2020 TerraForged
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.terraforged.mod.noise.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.terraforged.mod.noise.Module;

/**
 * @author dags <dags@dags.me>
 */
public class Map extends Modifier {
	public static final Codec<Map> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Module.CODEC.fieldOf("source").forGetter((m) -> m.source),
		Module.CODEC.fieldOf("min").forGetter((m) -> m.min),
		Module.CODEC.fieldOf("max").forGetter((m) -> m.max)
	).apply(instance, Map::new));
	
	private final Module min;
    private final Module max;
    private final float sourceRange;

    public Map(Module source, Module min, Module max) {
        super(source);
        this.min = min;
        this.max = max;
        this.sourceRange = source.maxValue() - source.minValue();
    }
    
    @Override
    public float minValue() {
        return min.minValue();
    }

    @Override
    public float maxValue() {
        return max.maxValue();
    }

    @Override
    public float modify(float x, float y, float value) {
        float alpha = (value - source.minValue()) / sourceRange;
        float min = this.min.getValue(x, y);
        float max = this.max.getValue(x, y);
        return min + (alpha * (max - min));

//        if (source.maxValue() != source.minValue()) {
//            value = Math.min(source.maxValue(), Math.max(source.minValue(), value));
//            value = (value - source.minValue()) / sourceRange;
//            return (value - min) / (max - min);
//        } else {
//            return Math.min(max, Math.max(min, value));
//        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Map map = (Map) o;

        if (Float.compare(map.sourceRange, sourceRange) != 0) return false;
        if (!min.equals(map.min)) return false;
        return max.equals(map.max);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + min.hashCode();
        result = 31 * result + max.hashCode();
        result = 31 * result + (sourceRange != +0.0f ? Float.floatToIntBits(sourceRange) : 0);
        return result;
    }
    
    @Override
	public Codec<Map> codec() {
		return CODEC;
	}
}