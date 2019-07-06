package xevion.everythingonastick.enchantingtable;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.ClientDummyContainerProvider;
import net.minecraft.container.BlockContext;
import net.minecraft.container.EnchantingTableContainer;
import net.minecraft.container.GenericContainer;
import net.minecraft.container.NameableContainerProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Nameable;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class EnchantingTableWandItem extends Item {
    public EnchantingTableWandItem(Settings settings) {
        super(settings);
    }

    BlockEntity enchanting_table_block_entity = new EnchantingTableWandBlockEntity();

    private NameableContainerProvider createContainerProvider(World world) {
        Text text = ((Nameable)enchanting_table_block_entity).getDisplayName();
        return new ClientDummyContainerProvider((int_1, player_inventory, player) -> {
            return new EnchantingTableContainer(int_1, player_inventory, BlockContext.EMPTY);
        }, text);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!world.isClient()) {
            player.openContainer(createContainerProvider(world));
        }
        return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
    }
}
