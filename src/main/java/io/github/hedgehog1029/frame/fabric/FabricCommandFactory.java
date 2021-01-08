package io.github.hedgehog1029.frame.fabric;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.hedgehog1029.frame.dispatcher.mapping.ICommandFactory;
import io.github.hedgehog1029.frame.dispatcher.pipeline.IPipeline;
import net.minecraft.server.command.ServerCommandSource;

public class FabricCommandFactory implements ICommandFactory {
	private CommandDispatcher<ServerCommandSource> dispatcher;

	public void setDispatcher(CommandDispatcher<ServerCommandSource> dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	public void registerCommand(IPipeline pipeline) {
		FabricCommand command = new FabricCommand(pipeline);
		LiteralCommandNode<ServerCommandSource> node = dispatcher.register(command.buildNode());

		command.yieldAliases(node, dispatcher::register);
	}
}
