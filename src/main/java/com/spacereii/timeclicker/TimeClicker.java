package com.spacereii.timeclicker;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class TimeClicker implements ClientModInitializer {

	private long startTime;

	@Override
	public void onInitializeClient() {
		startTime = System.currentTimeMillis();

		HudRenderCallback.EVENT.register(this::onHudRender);
	}

	private void onHudRender(DrawContext drawContext, float tickDelta) {
		MinecraftClient client = MinecraftClient.getInstance();
		long currentTime = System.currentTimeMillis();
		long timePlayedMillis = currentTime - startTime;
		long seconds = (timePlayedMillis / 1000) % 60;
		long minutes = (timePlayedMillis / (1000 * 60)) % 60;
		long hours = (timePlayedMillis / (1000 * 60 * 60)) % 24;

		String timePlayed = String.format("Time Played: %02d:%02d:%02d", hours, minutes, seconds);
		drawContext.drawTextWithShadow(client.textRenderer, Text.of(timePlayed), 10, 10, 0xFFFFFF);
	}
}
