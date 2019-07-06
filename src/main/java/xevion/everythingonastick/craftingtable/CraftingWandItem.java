package xevion.everythingonastick.craftingtable;

import net.minecraft.client.network.ClientDummyContainerProvider;
import net.minecraft.container.BlockContext;
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

public class CraftingWandItem extends Item {
    public CraftingWandItem(Settings settings) {
        super(settings);
    };

    private static final Text container_title = new TranslatableText("container.crafting", new Object[0]);

    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!world.isClient()) {
            player.openContainer(getProvider(world, player.getBlockPos()));
        }
        return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
    }

    private NameableContainerProvider getProvider(World world, BlockPos blockpos) {
        return new ClientDummyContainerProvider((sync_id, player_inventory, player) -> new CraftingWandContainer(sync_id, player_inventory, BlockContext.create(world, blockpos)), container_title);
    }
}
