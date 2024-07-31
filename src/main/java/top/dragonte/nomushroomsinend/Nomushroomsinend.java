package top.dragonte.nomushroomsinend;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class Nomushroomsinend implements ModInitializer {
    @Override
    public void onInitialize() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            var itemStack = player.getStackInHand(hand);
            if (itemStack.getItem() == Blocks.BROWN_MUSHROOM.asItem() || itemStack.getItem() == Blocks.RED_MUSHROOM.asItem()) {
                var blockPos = hitResult.getBlockPos().offset(hitResult.getSide());
                if (world.getBlockState(blockPos).isAir()&&world.getRegistryKey()== World.END) {
                    sendTitle((ServerPlayerEntity) player);
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        });
    }
    private void sendTitle(ServerPlayerEntity player) {
        Text titleText = Text.translatable("这里是末地种你妈的蘑菇");
        TitleS2CPacket packet = new TitleS2CPacket(titleText);
        player.networkHandler.sendPacket(packet);
    }
}
