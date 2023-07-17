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

package com.terraforged.mod.noise.combiner;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.terraforged.mod.codec.TFCodecs;
import com.terraforged.mod.noise.Module;

/**
 * @author dags <dags@dags.me>
 */
public class Mul extends Combiner {
	public static final Codec<Mul> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		TFCodecs.forArray(Module.DIRECT_CODEC, Module[]::new).fieldOf("modules").forGetter((c) -> c.sources)
	).apply(instance, Mul::new));

    public Mul(Module... modules) {
        super(modules);
    }

    @Override
    protected float minTotal(float total, Module next) {
        return total * next.minValue();
    }

    @Override
    protected float maxTotal(float total, Module next) {
        return total * next.maxValue();
    }

    @Override
    protected float combine(float total, float value) {
        return total * value;
    }

    @Override
	public Codec<Mul> codec() {
		return CODEC;
	}
}
