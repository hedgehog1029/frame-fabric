package io.github.hedgehog1029.frame.fabric.bindings;

import io.github.hedgehog1029.frame.annotation.Sender;
import io.github.hedgehog1029.frame.dispatcher.arguments.ICommandArguments;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.provider.Provider;
import io.github.hedgehog1029.frame.module.wrappers.ParameterWrapper;
import io.github.hedgehog1029.frame.util.Namespace;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerProvider implements Provider<ServerPlayerEntity> {
	@Override
	public ServerPlayerEntity provide(ICommandArguments args, ParameterWrapper param) throws DispatcherException {
		if (param.isAnnotationPresent(Sender.class)) {
			return (ServerPlayerEntity) args.getNamespace().get("sender");
		}

		String name = args.next();
		MinecraftServer server = (MinecraftServer) args.getNamespace().get("server");

		return server.getPlayerManager().getPlayer(name);
	}

	@Override
	public List<String> getSuggestions(int index, String partial, Namespace namespace) {
		MinecraftServer server = (MinecraftServer) namespace.get("server");

		return Arrays.stream(server.getPlayerManager().getPlayerNames())
				.filter(s -> s.startsWith(partial))
				.collect(Collectors.toList());
	}

	@Override
	public int argsWanted(ParameterWrapper param) {
		if (param.isAnnotationPresent(Sender.class))
			return 0;

		return 1;
	}
}
