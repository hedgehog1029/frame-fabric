package io.github.hedgehog1029.frame.fabric.bindings;

import io.github.hedgehog1029.frame.dispatcher.arguments.ICommandArguments;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.provider.Provider;
import io.github.hedgehog1029.frame.module.wrappers.ParameterWrapper;
import io.github.hedgehog1029.frame.util.Namespace;
import net.minecraft.command.CommandSource;

import java.util.Collections;
import java.util.List;

public class SourceProvider<S extends CommandSource> implements Provider<S> {
	@Override
	public S provide(ICommandArguments args, ParameterWrapper param) throws DispatcherException {
		return (S) args.getNamespace().get("source");
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
