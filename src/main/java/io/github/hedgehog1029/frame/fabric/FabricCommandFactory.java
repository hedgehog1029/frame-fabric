package io.github.hedgehog1029.frame.fabric;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.hedgehog1029.frame.dispatcher.mapping.ICommandFactory;
import io.github.hedgehog1029.frame.dispatcher.pipeline.IPipeline;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;

public class FabricCommandFactory implements ICommandFactory {
	private CommandDispatcher<ServerCommandSource> dispatcher;
	private CommandRegistryAccess registry;

	public void setDispatcher(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registry) {
		this.dispatcher = dispatcher;
		this.registry = registry;
	}

	@Override
	public void registerCommand(IPipeline pipeline) {
		FabricCommand command = new FabricCommand(pipeline);
		LiteralCommandNode<ServerCommandSource> node = dispatcher.register(command.buildNode(registry));

		command.yieldAliases(node, dispatcher::register);
	}
}
