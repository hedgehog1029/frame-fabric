package io.github.hedgehog1029.frame.fabric.bindings;

import io.github.hedgehog1029.frame.dispatcher.arguments.ICommandArguments;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.provider.Provider;
import io.github.hedgehog1029.frame.module.wrappers.ParameterWrapper;
import io.github.hedgehog1029.frame.util.Namespace;
import net.minecraft.server.MinecraftServer;

import java.util.Collections;
import java.util.List;

public class ServerProvider implements Provider<MinecraftServer> {
	@Override
	public MinecraftServer provide(ICommandArguments args, ParameterWrapper param) throws DispatcherException {
		return (MinecraftServer) args.getNamespace().get("server");
	}

	@Override
	public List<String> getSuggestions(int index, String partial, Namespace namespace) {
		return Collections.emptyList();
	}

	@Override
	public int argsWanted(ParameterWrapper param) {
		return 0;
	}
}
