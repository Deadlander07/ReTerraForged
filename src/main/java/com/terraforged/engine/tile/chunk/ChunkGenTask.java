/*
 * Decompiled with CFR 0.150.
 */
package com.terraforged.engine.tile.chunk;

import com.terraforged.engine.cell.Cell;
import com.terraforged.engine.concurrent.batch.BatchTask;
import com.terraforged.engine.world.heightmap.Heightmap;
import com.terraforged.engine.world.rivermap.Rivermap;

public class ChunkGenTask implements BatchTask {
	private final int seed;
    private final ChunkWriter chunk;
    private final Heightmap heightmap;
    private BatchTask.Notifier notifier = BatchTask.NONE;

    public ChunkGenTask(int seed, ChunkWriter chunk, Heightmap heightmap) {
    	this.seed = seed;
    	this.chunk = chunk;
        this.heightmap = heightmap;
    }

    @Override
    public void setNotifier(BatchTask.Notifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void run() {
        try {
            this.driveOne(this.seed, this.chunk, this.heightmap);
        }
        finally {
            this.notifier.markDone();
        }
    }

    protected void driveOne(int seed, ChunkWriter chunk, Heightmap heightmap) {
        Rivermap rivers = null;
        for (int dz = 0; dz < 16; ++dz) {
            for (int dx = 0; dx < 16; ++dx) {
                Cell cell = chunk.genCell(dx, dz);
                float x = chunk.getBlockX() + dx;
                float z = chunk.getBlockZ() + dz;
                heightmap.applyBase(seed, cell, x, z);
                rivers = Rivermap.get(seed, cell, rivers, heightmap);
                heightmap.applyRivers(seed, cell, x, z, rivers);
                heightmap.applyClimate(seed, cell, x, z);
            }
        }
    }

    public static class Zoom
    extends ChunkGenTask {
        private final float translateX;
        private final float translateZ;
        private final float zoom;

        public Zoom(int seed, ChunkWriter chunk, Heightmap heightmap, float translateX, float translateZ, float zoom) {
            super(seed, chunk, heightmap);
            this.translateX = translateX;
            this.translateZ = translateZ;
            this.zoom = zoom;
        }

        @Override
        protected void driveOne(int seed, ChunkWriter chunk, Heightmap heightmap) {
            Rivermap rivers = null;
            for (int dz = 0; dz < 16; ++dz) {
                for (int dx = 0; dx < 16; ++dx) {
                    Cell cell = chunk.genCell(dx, dz);
                    float x = (float)(chunk.getBlockX() + dx) * this.zoom + this.translateX;
                    float z = (float)(chunk.getBlockZ() + dz) * this.zoom + this.translateZ;
                    heightmap.applyBase(seed, cell, x, z);
                    rivers = Rivermap.get(seed, cell, rivers, heightmap);
                    heightmap.applyRivers(seed, cell, x, z, rivers);
                    heightmap.applyClimate(seed, cell, x, z);
                }
            }
        }
    }
}

