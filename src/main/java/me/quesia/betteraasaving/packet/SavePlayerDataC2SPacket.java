package me.quesia.betteraasaving.packet;

import me.quesia.betteraasaving.BetterAASaving;
import me.quesia.betteraasaving.mixin.access.MinecraftServerAccessor;
import me.quesia.betteraasaving.mixin.access.ServerPlayNetworkHandlerAccessor;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;

public class SavePlayerDataC2SPacket implements Packet<ServerPlayPacketListener> {
    @Override
    public void read(PacketByteBuf buf) {}

    @Override
    public void write(PacketByteBuf buf) {}

    @Override
    public void apply(ServerPlayPacketListener listener) {
        if (listener instanceof ServerPlayNetworkHandler) {
            MinecraftServer server = ((ServerPlayNetworkHandlerAccessor) listener).getServer();
            NetworkThreadUtils.forceMainThread(this, listener, server);
            BetterAASaving.log("Saving player data... (Server)");
            server.getPlayerManager().saveAllPlayerData();
        }
    }
}
