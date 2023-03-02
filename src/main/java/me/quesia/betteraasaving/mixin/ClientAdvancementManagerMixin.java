package me.quesia.betteraasaving.mixin;

import me.quesia.betteraasaving.BetterAASaving;
import me.quesia.betteraasaving.packet.SavePlayerDataC2SPacket;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientAdvancementManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Mixin(ClientAdvancementManager.class)
public class ClientAdvancementManagerMixin {
    @Shadow @Final private MinecraftClient client;
    private boolean hasStartedThread = false;

    @ModifyVariable(method = "onAdvancements", at = @At(value = "INVOKE", target = "Ljava/util/Map$Entry;getValue()Ljava/lang/Object;"))
    public Map.Entry<Identifier, AdvancementProgress> advancement(Map.Entry<Identifier, AdvancementProgress> value) {
        if (!value.getKey().getPath().contains("recipes")) {
            if (!this.hasStartedThread) {
                new Thread(() -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                        if (this.client.getNetworkHandler() != null) {
                            BetterAASaving.log("Saving player data... (Client)");
                            this.client.getNetworkHandler().sendPacket(new SavePlayerDataC2SPacket());
                        }
                        this.hasStartedThread = false;
                    } catch (InterruptedException e) {
                        BetterAASaving.log("Interrupted while sleeping.");
                    }
                }, "SavePlayerDataThread").start();
            }
            this.hasStartedThread = true;
        }
        return value;
    }
}
