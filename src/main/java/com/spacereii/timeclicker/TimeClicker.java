package com.spacereii.timeclicker;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class TimeClicker implements ClientModInitializer {

	private long startTime; // HELP WHEN DO I START
	private boolean trackingTime = false; // to track or not to track?

	@Override
	public void onInitializeClient() {
		// registering event listener
		ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
		HudRenderCallback.EVENT.register(this::onHudRender);
	}

	private void onClientTick(MinecraftClient client) {
		// check if in a world (if yes, clap cheeks)
		if (client.world != null) {
			if (!trackingTime) {
				startTime = System.currentTimeMillis();
				trackingTime = true;
			}
		} else {
			if (trackingTime) {
				trackingTime = false;
			}
		}
	}

	private void onHudRender(DrawContext drawContext, float tickDelta) {
		if (!trackingTime) return; // if vro isnt tracking time, tell vro to do jackshit

		MinecraftClient client = MinecraftClient.getInstance();
		long currentTime = System.currentTimeMillis();
		long timePlayedMillis = currentTime - startTime;

		long seconds = (timePlayedMillis / 1000) % 60;
		long minutes = (timePlayedMillis / (1000 * 60)) % 60;
		long hours = (timePlayedMillis / (1000 * 60 * 60)) % 24;

		String timePlayed = String.format("Time Played: %02d:%02d:%02d", hours, minutes, seconds);

		// Draw text with shadow
		drawContext.drawTextWithShadow(client.textRenderer, Text.of(timePlayed), 10, 10, 0xFFFFFF);
	}
}
