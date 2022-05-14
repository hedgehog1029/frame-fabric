package io.github.hedgehog1029.frame.fabric.bindings;

import io.github.hedgehog1029.frame.dispatcher.ArgumentTransformer;
import io.github.hedgehog1029.frame.util.IBindingProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class FabricBindingProvider implements IBindingProvider {
	@Override
	public void configure(ArgumentTransformer transformer) {
		transformer.bind(ServerPlayerEntity.class, new PlayerProvider());
		transformer.bind(ServerCommandSource.class, new SourceProvider<>());
		transformer.bind(CommandSource.class, new SourceProvider<>());
		transformer.bind(MinecraftServer.class, new ServerProvider());

		transformer.bind(Item.class, new ItemProvider());
	}
}
