package xevion.everythingonastick.craftingtable;

import net.minecraft.container.BlockContext;
import net.minecraft.container.CraftingTableContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;

public class CraftingWandContainer extends CraftingTableContainer {
    public CraftingWandContainer(int sync_id, PlayerInventory player_inventory, BlockContext block_context) {
        super(sync_id, player_inventory, block_context);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
