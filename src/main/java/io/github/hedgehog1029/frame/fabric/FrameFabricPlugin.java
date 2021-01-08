package io.github.hedgehog1029.frame.fabric;

import io.github.hedgehog1029.frame.Frame;
import io.github.hedgehog1029.frame.fabric.api.FrameInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

import java.util.ArrayList;
import java.util.List;

public class FrameFabricPlugin implements ModInitializer {
	private ArrayList<Frame> frameInstances = new ArrayList<>();

	public FrameFabricPlugin() {}

	@Override
	public void onInitialize() {
		List<EntrypointContainer<FrameInitializer>> containers = FabricLoader.getInstance()
				.getEntrypointContainers("frame", FrameInitializer.class);

		containers.forEach(container -> {
			Frame frame = new Frame.Builder()
					.setLogReceiver(new FabricLogReceiver(container.getProvider().getMetadata().getId()))
					.setCommandFactory(new FabricCommandFactory())
					.setHookCallback(new FabricHookCallback())
					.build();

			frameInstances.add(frame);

			container.getEntrypoint().initFrame(frame);
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> frameInstances.forEach(frame -> {
			if (frame.getCommandFactory() instanceof FabricCommandFactory) {
				((FabricCommandFactory) frame.getCommandFactory()).setDispatcher(dispatcher);
			}

			frame.go();
		}));
	}
}
