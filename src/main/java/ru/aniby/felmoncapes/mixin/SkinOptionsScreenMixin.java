package ru.aniby.felmoncapes.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.SkinOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkinOptionsScreen.class)
public abstract class SkinOptionsScreenMixin extends GameOptionsScreen {

    public SkinOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    @Unique
    private final ButtonWidget widget = ButtonWidget.builder(
            Text.of("Открыть профиль Felmon"), button -> {
                Util.getOperatingSystem().open("https://felmon.xyz/profile");
            }
    ).dimensions(this.width - 155, this.height - 25, 150, 20).build();

    @Inject(
            at = @At("TAIL"),
            method = "init()V"
    )
    public void iTInject(CallbackInfo info) {
        this.addDrawableChild(widget);
    }
}