package net.pedroricardo.congratulations;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.gui.hud.AbsoluteLayout;
import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.HudComponents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class Congratulations implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "congratulations";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static int textTimer = 0;
    @Override
    public void onInitialize() {
        LOGGER.info("Congratulations initialized.");
		HudComponents.register(new WinComponent("end_poem", new AbsoluteLayout(0.5f, 0.0f, ComponentAnchor.TOP_CENTER)));
    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}
}
