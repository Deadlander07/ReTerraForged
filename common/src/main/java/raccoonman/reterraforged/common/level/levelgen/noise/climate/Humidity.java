package raccoonman.reterraforged.common.level.levelgen.noise.climate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import raccoonman.reterraforged.common.level.levelgen.noise.Noise;
import raccoonman.reterraforged.common.level.levelgen.noise.util.NoiseUtil;

public record Humidity(Noise source, int power) implements Noise {
	public static final Codec<Humidity> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Noise.HOLDER_HELPER_CODEC.fieldOf("source").forGetter(Humidity::source),
		Codec.INT.fieldOf("power").forGetter(Humidity::power)		
	).apply(instance, Humidity::new));
        
    @Override
    public float compute(float x, float y, int seed) {
        float noise = this.source.compute(x, y, seed);
        if (this.power < 2) {
            return noise;
        }
        noise = (noise - 0.5F) * 2.0F;
        float value = NoiseUtil.pow(noise, this.power);
        value = NoiseUtil.copySign(value, noise);
        return NoiseUtil.map(value, -1.0F, 1.0F, 2.0F);
    }

	@Override
	public Codec<Humidity> codec() {
		return CODEC;
	}
}