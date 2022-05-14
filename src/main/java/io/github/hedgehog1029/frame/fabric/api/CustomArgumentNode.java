package io.github.hedgehog1029.frame.fabric.api;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.github.hedgehog1029.frame.dispatcher.pipeline.ArgumentNode;

public abstract class CustomArgumentNode extends ArgumentNode {
	public CustomArgumentNode(String name) {
		super(name);
	}

	public abstract ArgumentType<?> getArgumentType();

	public abstract String getArgument(CommandContext<?> context, String name);
}
