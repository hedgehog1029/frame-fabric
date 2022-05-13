package io.github.hedgehog1029.frame.fabric;

import io.github.hedgehog1029.frame.inject.Injector;
import io.github.hedgehog1029.frame.module.LoadedModule;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class ServerLifecycleEventsInjector implements Injector {
	@Override
	public void inject(LoadedModule<?> module) throws Exception {
		var mod = module.getInstance();

		if (mod instanceof ServerLifecycleEvents.ServerStarting inst) {
			ServerLifecycleEvents.SERVER_STARTING.register(inst);
		}
		if (mod instanceof ServerLifecycleEvents.ServerStarted inst) {
			ServerLifecycleEvents.SERVER_STARTED.register(inst);
		}
		if (mod instanceof ServerLifecycleEvents.ServerStopping inst) {
			ServerLifecycleEvents.SERVER_STOPPING.register(inst);
		}
		if (mod instanceof ServerLifecycleEvents.ServerStopped inst) {
			ServerLifecycleEvents.SERVER_STOPPED.register(inst);
		}
	}
}
