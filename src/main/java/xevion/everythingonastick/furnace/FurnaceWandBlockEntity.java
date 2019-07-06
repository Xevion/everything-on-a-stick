package xevion.everythingonastick.furnace;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.container.Container;
import net.minecraft.container.FurnaceContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class WandFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    public WandFurnaceBlockEntity() {super(BlockEntityType.FURNACE, RecipeType.SMELTING); }

    public Text getContainerName() {
        return new TranslatableText("container.furnacewand", new Object[0]);
    }

    protected Container createContainer(int int_1, PlayerInventory playerInventory_1) {
        return new FurnaceContainer(int_1, playerInventory_1, this, this.propertyDelegate);
    }

    public boolean canPlayerUseInv(PlayerEntity player) {
        return true;
    }
//
//    public int[] getInvAvailableSlots(Direction direction_1) {
//        return new int[]{};
//    }
//
//    public boolean canInsertInvStack(int int_1, ItemStack itemStack_1, @Nullable Direction direction_1) {
//        return false;
//    }
//
//    public boolean canExtractInvStack(int int_1, ItemStack itemStack_1, Direction direction_1) {
//        return false;
//    }
}
