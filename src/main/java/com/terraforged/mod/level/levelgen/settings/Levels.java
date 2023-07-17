/*
 * Decompiled with CFR 0.150.
 */
package com.terraforged.mod.level.levelgen.settings;

import com.terraforged.mod.noise.util.NoiseUtil;

public class Levels {
    public final int worldHeight;
    public final float unit;
    public final int waterY;
    private final int groundY;
    public final int groundLevel;
    public final int waterLevel;
    public final float ground;
    public final float water;
    private final float elevationRange;

    public Levels(int height, int seaLevel) {
        this.worldHeight = Math.max(1, height);
        this.unit = NoiseUtil.div(1, this.worldHeight);
        this.waterLevel = seaLevel;
        this.groundLevel = this.waterLevel + 1;
        this.waterY = Math.min(this.waterLevel - 1, this.worldHeight);
        this.groundY = Math.min(this.groundLevel - 1, this.worldHeight);
        this.ground = NoiseUtil.div(this.groundY, this.worldHeight);
        this.water = NoiseUtil.div(this.waterY, this.worldHeight);
        this.elevationRange = 1.0f - this.water;
    }

    public int scale(float value) {
        if (value >= 1.0f) {
            return this.worldHeight - 1;
        }
        return (int)(value * (float)this.worldHeight);
    }

    public float elevation(float value) {
        if (value <= this.water) {
            return 0.0f;
        }
        return (value - this.water) / this.elevationRange;
    }

    public float elevation(int y) {
        if (y <= this.waterY) {
            return 0.0f;
        }
        return this.scale(y - this.waterY) / this.elevationRange;
    }

    public float scale(int level) {
        return NoiseUtil.div(level, this.worldHeight);
    }

    public float water(int amount) {
        return NoiseUtil.div(this.waterY + amount, this.worldHeight);
    }

    public float ground(int amount) {
        return NoiseUtil.div(this.groundY + amount, this.worldHeight);
    }
}

