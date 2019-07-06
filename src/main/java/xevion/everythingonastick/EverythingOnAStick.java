package xevion.everythingonastick;

import net.fabricmc.api.ModInitializer;

public class EverythingOnAStick implements ModInitializer {
	@Override
	public void onInitialize() {
		RegisterWands.register();
		System.out.println("Wand Items Registered");
	}
}
