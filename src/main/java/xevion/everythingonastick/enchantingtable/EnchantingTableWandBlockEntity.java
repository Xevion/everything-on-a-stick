package xevion.everythingonastick.enderchest;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.client.network.ClientDummyContainerProvider;
import net.minecraft.container.BlockContext;
import net.minecraft.container.EnchantingTableContainer;
import net.minecraft.container.NameableContainerProvider;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnderchestWandBlockEntity extends EnchantingTableBlockEntity {
    public EnderchestWandBlockEntity() {}

    @Override
    public void tick() {}


    public NameableContainerProvider createContainerProvider(BlockState blockState_1, World world_1, BlockPos blockPos_1) {
        if (blockEntity_1 instanceof EnchantingTableBlockEntity) {
            Text text_1 = ((Nameable)blockEntity_1).getDisplayName();
            return new ClientDummyContainerProvider((int_1, playerInventory_1, playerEntity_1) -> {
                return new EnchantingTableContainer(int_1, playerInventory_1, BlockContext.create(world_1, blockPos_1));
            }, text_1);
        } else {
            return null;
        }
    }
}
