package io.github.hedgehog1029.frame.fabric;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.exception.UsageException;
import io.github.hedgehog1029.frame.dispatcher.pipeline.ArgumentNode;
import io.github.hedgehog1029.frame.dispatcher.pipeline.IPipeline;
import io.github.hedgehog1029.frame.fabric.api.CustomArgumentNode;
import io.github.hedgehog1029.frame.util.Namespace;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayDeque;
import java.util.List;

public class FabricCommandExecutor implements Command<ServerCommandSource> {
	private final IPipeline pipeline;
	private final List<ArgumentNode> nodes;

	public FabricCommandExecutor(IPipeline pipeline, List<ArgumentNode> nodes) {
		this.pipeline = pipeline;
		this.nodes = nodes;
	}

	@Override
	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerCommandSource source = context.getSource();
		Namespace namespace = new Namespace();

		namespace.set("server", source.getServer());
		namespace.set("sender", source.getEntity());
		namespace.set("source", context.getSource());

		try {
			ArrayDeque<String> arguments = new ArrayDeque<>();

			for (ArgumentNode param : nodes) {
				if (param instanceof ArgumentNode.Literal l) {
					arguments.add(l.getLiteral());
				} else if (param instanceof CustomArgumentNode cn) {
					arguments.add(cn.getArgument(context, param.getName()));
				} else {
					arguments.add(context.getArgument(param.getName(), String.class));
				}
			}

			pipeline.call(arguments, namespace);

			return 1;
		} catch (UsageException e) {
			String message = String.format("Usage: /%s %s", pipeline.getPrimaryAlias(), pipeline.getUsage());
			source.sendError(Text.literal(message).formatted(Formatting.RED));

			return -1;
		} catch (DispatcherException e) {
			String message = e.getMessage();
			if (message == null) message = "An unknown error occurred!";

			source.sendError(Text.literal(message).formatted(Formatting.RED));

			if (e.getMessage() == null) {
				e.printStackTrace();
			}
			return -1;
		}
	}
}
