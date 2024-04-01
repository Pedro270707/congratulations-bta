package net.pedroricardo.congratulations.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.achievement.Achievement;
import net.minecraft.core.achievement.AchievementList;
import net.minecraft.core.achievement.stat.Stat;
import net.minecraft.core.entity.player.EntityPlayer;
import net.pedroricardo.congratulations.Congratulations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = EntityPlayer.class, remap = false)
public abstract class PlayerMixin {
	@Shadow
	public abstract int getStat(Stat statbase);

	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo ci) {
		if (!this.hasWon()) {
			Congratulations.textTimer = 0;
		} else {
			++Congratulations.textTimer;
		}
	}

	private boolean hasWon() {
		for (Achievement achievement : AchievementList.achievementList) {
			if (this.getStat(achievement) == 0) {
				return false;
			}
		}
		return true;
	}
}
