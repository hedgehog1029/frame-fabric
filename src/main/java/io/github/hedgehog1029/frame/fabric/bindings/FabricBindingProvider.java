package io.github.hedgehog1029.frame.fabric.bindings;

import io.github.hedgehog1029.frame.dispatcher.ArgumentTransformer;
import io.github.hedgehog1029.frame.util.IBindingProvider;
import net.minecraft.server.network.ServerPlayerEntity;

public class FabricBindingProvider implements IBindingProvider {
	@Override
	public void configure(ArgumentTransformer transformer) {
		transformer.bind(ServerPlayerEntity.class, new PlayerProvider());
	}
}
