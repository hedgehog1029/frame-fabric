package io.github.hedgehog1029.frame.fabric;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.hedgehog1029.frame.dispatcher.pipeline.ExecutionPlan;
import io.github.hedgehog1029.frame.dispatcher.pipeline.IPipeline;
import io.github.hedgehog1029.frame.util.Namespace;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import org.generalprogramming.fabulous.Fabulous;
import org.generalprogramming.fabulous.spi.PermissionsAPI;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class FabricCommand {
	private final IPipeline pipeline;
	private final List<ExecutionPlan> plans;

	public FabricCommand(IPipeline pipeline) {
		this.pipeline = pipeline;
		this.plans = pipeline.getExecutionPlans().stream()
				.sorted(Comparator.comparingInt(o -> o.arity))
				.collect(Collectors.toList());
	}

	public LiteralArgumentBuilder<ServerCommandSource> buildNode() {
		LiteralArgumentBuilder<ServerCommandSource> builder = literal(pipeline.getPrimaryAlias());

		plans.forEach(plan -> {
			List<RequiredArgumentBuilder<ServerCommandSource, String>> mappedArgs = plan.getParameterNames().stream()
					.map(name -> argument(name, string()).suggests(this::suggest))
					.toList();

			ArgumentBuilder<ServerCommandSource, ?> last = mappedArgs.get(mappedArgs.size() - 1);
			if (last == null) last = builder;

			last.requires(this::checkPermission);
			last.executes(new FabricCommandExecutor(pipeline, plan.getParameterNames()));

			if (!mappedArgs.isEmpty()) {
				builder.then(Util.reduceRight(mappedArgs.stream(), ArgumentBuilder::then));
			}
		});

		if (builder.getCommand() == null) {
			builder.executes(this::defaultExecutor);
		}

		return builder;
	}

	public void yieldAliases(LiteralCommandNode<ServerCommandSource> node,
	                         Consumer<LiteralArgumentBuilder<ServerCommandSource>> consumer) {
		Arrays.stream(pipeline.getAliases()).skip(1).forEach(alias ->
				consumer.accept(literal(alias).requires(this::checkPermission).redirect(node)));
	}

	private int defaultExecutor(CommandContext<ServerCommandSource> context) {
		String message = String.format("Usage: /%s %s", pipeline.getPrimaryAlias(), pipeline.getUsage());
		context.getSource().sendError(new LiteralText(message).formatted(Formatting.RED));

		return -1;
	}

	private boolean checkPermission(ServerCommandSource source) {
		if (pipeline.getPermission().isEmpty() || source.hasPermissionLevel(4)) {
			return true;
		}

		PermissionsAPI api = Fabulous.getFabulous().getPermissionsAPI();
		try {
			ServerPlayerEntity player = source.getPlayer();

			return api.hasPermission(player.getUuid(), pipeline.getPermission());
		} catch (CommandSyntaxException ignored) {
		}

		return false;
	}

	private CompletableFuture<Suggestions> suggest(CommandContext<ServerCommandSource> ctx, SuggestionsBuilder builder) {
		ServerCommandSource source = ctx.getSource();
		Namespace namespace = new Namespace();

		namespace.set("server", source.getServer());
		namespace.set("source", source);

		Iterator<String> parts = Splitter.on(' ').split(builder.getInput()).iterator();
		parts.next(); // skip an argument
		pipeline.getCompletions(Lists.newArrayList(parts), namespace).forEach(builder::suggest);

		return builder.buildFuture();
	}
}
