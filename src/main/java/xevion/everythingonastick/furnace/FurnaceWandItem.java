package xevion.everythingonastick.furnace;

import net.minecraft.container.NameableContainerProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class FurnaceWandItem extends Item {
    public FurnaceWandItem(Settings settings) {super(settings); }

    private FurnaceWandBlockEntity block_entity = new FurnaceWandBlockEntity();

    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!world.isClient()) {
            block_entity.setWorld(player);
            player.openContainer((NameableContainerProvider)block_entity);
        }
        return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
    }

    public void inventoryTick(ItemStack itemStack_1, World world_1, Entity entity_1, int int_1, boolean boolean_1) {
        block_entity.tick();
    }
}
