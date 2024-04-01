package net.pedroricardo.congratulations;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.hud.HudComponent;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.core.net.command.TextFormatting;
import org.lwjgl.opengl.GL11;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WinComponent extends HudComponent {
	private static final String OBFUSCATE_TOKEN = "§f§k§a§b";
	private List<String> lines = new ArrayList<>();

	public WinComponent(String key, Layout layout) {
        super(key, 256, 8, layout);
        this.addText("/end.txt");
        this.addText("/postcredits.txt");
	}

	private void addText(String path) {
		InputStream stream = null;
		try {
			int i;
			String string;
			stream = WinComponent.class.getResourceAsStream(path);
			Random randomSource = new Random(8124371L);
			BufferedReader br = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
			while ((string = br.readLine()) != null) {
				while ((i = string.indexOf(OBFUSCATE_TOKEN)) != -1) {
					String string2 = string.substring(0, i);
					String string3 = string.substring(i + OBFUSCATE_TOKEN.length());
					string = string2 + TextFormatting.WHITE + TextFormatting.OBFUSCATED + "XXXXXXXX".substring(0, randomSource.nextInt(4) + 3) + string3;
				}
				this.addPoemLines(string);
				this.addEmptyLine();
			}
		} catch (Exception ignored) {
		} finally {
			try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception ignored) {}
		}
	}

	private void addEmptyLine() {
		this.lines.add("");
	}

	private void addPoemLines(String string) {
		this.lines.add(formatDescription(string, 40));
	}

	private static String formatDescription(String description, int preferredLineLength) {
		StringBuilder string = new StringBuilder();
		boolean bl = description.length() >= 2 && description.startsWith("§");
		if (bl) string.append(description, 0, 2);
		else string.append(TextFormatting.LIGHT_GRAY);
		int lineLength = 0;
		for (int i = bl ? 2 : 0; i < description.length(); ++i) {
			char c = description.charAt(i);
			if (c == ' ') {
				if (lineLength > preferredLineLength) {
					lineLength = 0;
					string.append("\n");
					if (bl) string.append(description, 0, 2);
					else string.append(TextFormatting.LIGHT_GRAY);
					continue;
				}
				string.append(c);
				continue;
			}
			++lineLength;
			string.append(c);
		}
		return string.toString();
	}

	@Override
	public boolean isVisible(Minecraft minecraft) {
		return minecraft.thePlayer != null;
	}

	@Override
	public void render(Minecraft minecraft, GuiIngame guiIngame, int xSizeScreen, int ySizeScreen, float partialTick) {
		int x = this.getLayout().getComponentX(minecraft, this, xSizeScreen);
		int y = this.getLayout().getComponentY(minecraft, this, ySizeScreen) + (int)Math.ceil(minecraft.resolution.scaledHeightExact);
		String str = String.join("\n", this.lines).replaceAll("PLAYERNAME", minecraft.thePlayer == null ? "Player" : minecraft.thePlayer.username);
		String[] split = str.split("\n");
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, -(Congratulations.textTimer / 4.0f) * 16.0f, -50.0f);
		for (int line = 0; line < str.split("\n").length; line++) {
			if (split[line].startsWith("§2")) {
				minecraft.fontRenderer.drawStringWithShadow(split[line].replaceAll("§2", "§r"), x, y + 9 * line, 0xFF007800);
			} else if (split[line].startsWith("§3")) {
				minecraft.fontRenderer.drawStringWithShadow(split[line].replaceAll("§3", "§r"), x, y + 9 * line, 0xFF007878);
			} else if (split[line].startsWith("§f")) {
				minecraft.fontRenderer.drawStringWithShadow(split[line].replaceAll("§f", "§r"), x, y + 9 * line, 0xFFFFFFFF);
			}
		}
		GL11.glPopMatrix();
	}

	@Override
	public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int xSizeScreen, int ySizeScreen) {
		int x = this.getLayout().getComponentX(minecraft, this, xSizeScreen);
		int y = this.getLayout().getComponentY(minecraft, this, ySizeScreen);
		minecraft.fontRenderer.drawString("I see the player you mean.", x, y, 0xFFFFFFFF);
	}
}
