package xevion.everythingonastick.furnace;

import net.minecraft.container.NameableContainerProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class FurnaceWand extends Item {
    public FurnaceWand(Settings settings) {
        super(settings);
    };

    private WandFurnaceBlockEntity block_entity = new WandFurnaceBlockEntity();

    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!world.isClient()) {
            player.openContainer((NameableContainerProvider)block_entity);
        }
        return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
    }

}
