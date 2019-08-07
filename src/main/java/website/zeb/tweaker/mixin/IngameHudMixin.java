package website.zeb.tweaker.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.Window;

@Mixin(InGameHud.class)
public class IngameHudMixin {

    @Shadow
    private MinecraftClient client;

    @Inject(method = "render", at = @At("RETURN"))
    public void render(float partialTicks, CallbackInfo callbackInfo) {
        MinecraftClient minecraft = MinecraftClient.getInstance();
        Window window = minecraft.window;
        TextRenderer textRenderer = minecraft.textRenderer;

        textRenderer.drawWithShadow("\247oTweaker", window.getScaledWidth() - textRenderer.getStringWidth("Tweaker") - 2, 2, 0xFFFFFFFF);
    }
    
}