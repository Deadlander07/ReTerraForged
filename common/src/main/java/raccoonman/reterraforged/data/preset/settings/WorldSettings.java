package raccoonman.reterraforged.data.preset.settings;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.ExtraCodecs.EitherCodec;
import raccoonman.reterraforged.world.worldgen.biome.spawn.SpawnType;
import raccoonman.reterraforged.world.worldgen.continent.ContinentType;
import raccoonman.reterraforged.world.worldgen.continent.IslandPopulator;
import raccoonman.reterraforged.world.worldgen.noise.function.DistanceFunction;

public class WorldSettings {
	public static final Codec<WorldSettings> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Continent.CODEC.fieldOf("continent").forGetter((o) -> o.continent),
		ControlPoints.CODEC.fieldOf("controlPoints").forGetter((o) -> o.controlPoints),
		Properties.CODEC.fieldOf("properties").forGetter((o) -> o.properties)
	).apply(instance, WorldSettings::new));
	
    public Continent continent;
    public ControlPoints controlPoints;
    public Properties properties;
    
    public WorldSettings(Continent continent, ControlPoints controlPoints, Properties properties) {
        this.continent = continent;
        this.controlPoints = controlPoints;
        this.properties = properties;
    }
    
    public WorldSettings copy() {
    	return new WorldSettings(this.continent.copy(), this.controlPoints.copy(), this.properties.copy());
    }
    
    public static class Continent {
    	public static final Codec<Continent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
    		ContinentType.CODEC.fieldOf("continentType").forGetter((o) -> o.continentType),
    		DistanceFunction.CODEC.optionalFieldOf("continentShape", DistanceFunction.EUCLIDEAN).forGetter((o) -> o.continentShape),
    		Codec.INT.fieldOf("continentScale").forGetter((o) -> o.continentScale),
    		Codec.FLOAT.fieldOf("continentJitter").forGetter((o) -> o.continentJitter),
    		Codec.FLOAT.optionalFieldOf("continentSkipping", 0.25F).forGetter((o) -> o.continentSkipping),
    		Codec.FLOAT.optionalFieldOf("continentSizeVariance", 0.25F).forGetter((o) -> o.continentSizeVariance),
    		Codec.INT.optionalFieldOf("continentNoiseOctaves", 5).forGetter((o) -> o.continentNoiseOctaves),
    		Codec.FLOAT.optionalFieldOf("continentNoiseGain", 0.26F).forGetter((o) -> o.continentNoiseGain),
    		Codec.FLOAT.optionalFieldOf("continentNoiseLacunarity", 4.33F).forGetter((o) -> o.continentNoiseLacunarity)
    	).apply(instance, Continent::new));
    	
        public ContinentType continentType;
        public DistanceFunction continentShape;
        public int continentScale;
        public float continentJitter;
        public float continentSkipping;
        public float continentSizeVariance;
        public int continentNoiseOctaves;
        public float continentNoiseGain;
        public float continentNoiseLacunarity;
        
        public Continent(ContinentType continentType, DistanceFunction continentShape, int continentScale, float continentJitter, float continentSkipping, float continentSizeVariance, int continentNoiseOctaves, float continentNoiseGain, float continentNoiseLacunarity) {
            this.continentType = continentType;
            this.continentShape = continentShape;
            this.continentScale = continentScale;
            this.continentJitter = continentJitter;
            this.continentSkipping = continentSkipping;
            this.continentSizeVariance = continentSizeVariance;
            this.continentNoiseOctaves = continentNoiseOctaves;
            this.continentNoiseGain = continentNoiseGain;
            this.continentNoiseLacunarity = continentNoiseLacunarity;
        }
        
        public Continent copy() {
        	return new Continent(this.continentType, this.continentShape, this.continentScale, this.continentJitter, this.continentSkipping, this.continentSizeVariance, this.continentNoiseOctaves, this.continentNoiseGain, this.continentNoiseLacunarity);
        }
    }
    
    public static class ControlPoints {
    	public static final Codec<ControlPoints> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        	Codec.FLOAT.optionalFieldOf("islandInland", IslandPopulator.DEFAULT_INLAND_POINT).forGetter((o) -> o.islandInland),
        	Codec.FLOAT.optionalFieldOf("islandCoast", IslandPopulator.DEFAULT_COAST_POINT).forGetter((o) -> o.islandCoast),
    		Codec.FLOAT.fieldOf("deepOcean").forGetter((o) -> o.deepOcean),
    		Codec.FLOAT.fieldOf("shallowOcean").forGetter((o) -> o.shallowOcean),
    		Codec.FLOAT.fieldOf("beach").forGetter((o) -> o.beach),
    		Codec.FLOAT.fieldOf("coast").forGetter((o) -> o.coast),
    		Codec.FLOAT.fieldOf("inland").forGetter((o) -> o.nearInland),
    		Codec.FLOAT.optionalFieldOf("midInland", 0.7F).forGetter((o) -> o.midInland),
    		Codec.FLOAT.optionalFieldOf("farInland", 1.0F).forGetter((o) -> o.farInland)
        ).apply(instance, ControlPoints::new));

    	public float islandInland;
    	public float islandCoast;
        public float deepOcean;
        public float shallowOcean;
        public float beach;
        public float coast;
        public float nearInland;
        public float midInland;
        public float farInland;
        
        //TODO
        public float farInlandModifier;
        
        public ControlPoints(float islandInland, float islandCoast, float deepOcean, float shallowOcean, float beach, float coast, float nearInland, float midInland, float farInland) {
        	this.islandInland = islandInland;
        	this.islandCoast = islandCoast;
            this.deepOcean = deepOcean;
            this.shallowOcean = shallowOcean;
            this.beach = beach;
            this.coast = coast;
            this.nearInland = nearInland;
            this.midInland = midInland;
            this.farInland = farInland;
        }
        
        public float coastMarker() {
        	return this.coast + (this.nearInland - this.coast) / 2.0F;
        }
        
        public ControlPoints copy() {
        	return new ControlPoints(this.islandInland, this.islandCoast, this.deepOcean, this.shallowOcean, this.beach, this.coast, this.nearInland, this.midInland, this.farInland);
        }
    }
    
    public static class Properties {
    	public static final Codec<Properties> CODEC = RecordCodecBuilder.create(instance -> instance.group(
    		SpawnType.CODEC.fieldOf("spawnType").forGetter((o) -> o.spawnType),
    		Codec.INT.fieldOf("worldHeight").forGetter((o) -> o.worldHeight),
    		Codec.INT.optionalFieldOf("worldDepth", 64).forGetter((o) -> o.worldDepth),
    		Codec.INT.fieldOf("seaLevel").forGetter((o) -> o.seaLevel),
    		Codec.INT.optionalFieldOf("lavaLevel", -54).forGetter((o) -> o.lavaLevel)
    	).apply(instance, Properties::new));
    	
        public SpawnType spawnType;
        public int worldHeight;
        public int worldDepth;
        public int seaLevel;
        public int lavaLevel;
        
        public Properties(SpawnType spawnType, int worldHeight, int worldDepth, int seaLevel, int lavaLevel) {
        	this.spawnType = spawnType;
        	this.worldHeight = worldHeight;
        	this.worldDepth = worldDepth;
        	this.seaLevel = seaLevel;
        	this.lavaLevel = lavaLevel;
        }
        
        public Properties copy() {
        	return new Properties(this.spawnType, this.worldHeight, this.worldDepth, this.seaLevel, this.lavaLevel);
        }
        
        @Deprecated
        public int terrainScaler() {
        	return Math.min(this.worldHeight, 256);
        }
    }
}
