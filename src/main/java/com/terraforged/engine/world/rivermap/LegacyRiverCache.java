/*
 * Decompiled with CFR 0.150.
 */
package com.terraforged.engine.world.rivermap;

import com.terraforged.engine.cell.Cell;
import com.terraforged.noise.util.NoiseUtil;

public class LegacyRiverCache extends RiverCache {
    public LegacyRiverCache(RiverGenerator generator) {
        super(generator);
    }

    @Override
    public Rivermap getRivers(Cell cell) {
        return this.getRivers(cell.continentX, cell.continentZ);
    }

    @Override
    public Rivermap getRivers(int x, int z) {
        return this.cache.computeIfAbsent(NoiseUtil.seed(x, z), id -> this.generator.generateRivers(x, z, id));
    }
}

