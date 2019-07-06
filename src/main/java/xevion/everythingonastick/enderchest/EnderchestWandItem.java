package xevion.everythingonastick.enderchest;

import net.minecraft.client.network.ClientDummyContainerProvider;
import net.minecraft.container.BlockContext;
import net.minecraft.container.GenericContainer;
import net.minecraft.container.NameableContainerProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xevion.everythingonastick.craftingtable.WandCraftingTableContainer;

import static net.minecraft.block.EnderChestBlock.CONTAINER_NAME;

public class EnderchestWand extends Item {
    public EnderchestWand(Settings settings) {
        super(settings);
    };

    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!world.isClient()) {
            player.openContainer(new ClientDummyContainerProvider((int_1, playerInventory_1, playerEntity_1x) -> {
                return GenericContainer.createGeneric9x3(int_1, playerInventory_1, player.getEnderChestInventory());
            }, CONTAINER_NAME));
        }
        return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
    }
}
