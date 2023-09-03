package ru.aniby.felmoncapes.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ru.aniby.felmoncapes.utils.CapeFunctions;
import ru.aniby.felmoncapes.utils.Capes;

import java.util.Map;

@Mixin(PlayerListEntry.class)
public class PlayerListEntryMixin {
    @Shadow
    @Final
    private GameProfile profile;
    @Shadow
    @Final
    private Map<MinecraftProfileTexture.Type, Identifier> textures;
    private boolean loadedCapeTexture = false;

    @Inject(method = "getCapeTexture", at = @At("HEAD"))
    private void injectedCapeTexture(CallbackInfoReturnable<Identifier> cir) {
        fetchCapeTexture();
    }

    @Inject(method = "getElytraTexture", at = @At("HEAD"))
    private void injectedElytraTexture(CallbackInfoReturnable<Identifier> cir) {
        fetchCapeTexture();
    }

    @Unique
    private void fetchCapeTexture() {
        if (loadedCapeTexture) return;
        loadedCapeTexture = true;
        String cape = Capes.getWithNickname(this.profile.getName());
        if (cape != null && !cape.isEmpty())
            CapeFunctions.loadPlayerCape(this.profile, cape, id -> this.textures.put(MinecraftProfileTexture.Type.CAPE, id));
    }
}