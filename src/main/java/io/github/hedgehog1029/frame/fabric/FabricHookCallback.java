package io.github.hedgehog1029.frame.fabric;

import io.github.hedgehog1029.frame.hooks.IHookCallback;
import net.fabricmc.loader.api.FabricLoader;

public class FabricHookCallback implements IHookCallback {
	@Override
	public boolean shouldHookLoad(String key) {
		return FabricLoader.getInstance().isModLoaded(key);
	}
}
