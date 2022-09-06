package io.github.hedgehog1029.frame.fabric.bindings;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.github.hedgehog1029.frame.dispatcher.arguments.ICommandArguments;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.pipeline.ArgumentNode;
import io.github.hedgehog1029.frame.dispatcher.provider.Provider;
import io.github.hedgehog1029.frame.fabric.api.CustomArgumentNode;
import io.github.hedgehog1029.frame.module.wrappers.ParameterWrapper;
import io.github.hedgehog1029.frame.util.Namespace;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class ItemProvider implements Provider<Item> {
	@Override
	public Item provide(ICommandArguments args, ParameterWrapper param) throws DispatcherException {
		return Registry.ITEM.get(new Identifier(args.next()));
	}

	@Override
	public List<String> getSuggestions(int index, String partial, Namespace namespace) {
		return Registry.ITEM.getIds().stream()
				.filter(id -> id.toString().startsWith(partial) || id.getPath().startsWith(partial))
				.map(Identifier::toString)
				.toList();
	}

	@Override
	public int argsWanted(ParameterWrapper param) {
		return 1;
	}

	@Override
	public ArgumentNode makeNode(String name, ParameterWrapper param) {
		return new ItemArgumentNode(name);
	}

	public static class ItemArgumentNode extends CustomArgumentNode {
		public ItemArgumentNode(String name) {
			super(name);
		}

		@Override
		public ArgumentType<?> getArgumentType(CommandRegistryAccess registry) {
			return ItemStackArgumentType.itemStack(registry);
		}

		@Override
		public String getArgument(CommandContext<?> context, String name) {
			var item = ItemStackArgumentType.getItemStackArgument(context, name).getItem();
			return Registry.ITEM.getId(item).toString();
		}
	}
}
