package xevion.everythingonastick.furnace;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jdk.internal.jline.internal.Nullable;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.*;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class CustomAbstractFurnaceBlockEntity extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider, Tickable {
    private static final int[] TOP_SLOTS = new int[]{0};
    private static final int[] BOTTOM_SLOTS = new int[]{2, 1};
    private static final int[] SIDE_SLOTS = new int[]{1};
    protected DefaultedList<ItemStack> inventory;
    private int burnTime;
    private int fuelTime;
    private int cookTime;
    private int cookTimeTotal;
    protected final PropertyDelegate propertyDelegate;
    private final Map<Identifier, Integer> recipesUsed;
    protected final RecipeType<? extends AbstractCookingRecipe> recipeType;

    protected CustomAbstractFurnaceBlockEntity(BlockEntityType<?> blockEntityType_1, RecipeType<? extends AbstractCookingRecipe> recipeType_1) {
        super(blockEntityType_1);
        this.inventory = DefaultedList.create(3, ItemStack.EMPTY);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int int_1) {
                switch(int_1) {
                    case 0:
                        return CustomAbstractFurnaceBlockEntity.this.burnTime;
                    case 1:
                        return CustomAbstractFurnaceBlockEntity.this.fuelTime;
                    case 2:
                        return CustomAbstractFurnaceBlockEntity.this.cookTime;
                    case 3:
                        return CustomAbstractFurnaceBlockEntity.this.cookTimeTotal;
                    default:
                        return 0;
                }
            }

            public void set(int int_1, int int_2) {
                switch(int_1) {
                    case 0:
                        CustomAbstractFurnaceBlockEntity.this.burnTime = int_2;
                        break;
                    case 1:
                        CustomAbstractFurnaceBlockEntity.this.fuelTime = int_2;
                        break;
                    case 2:
                        CustomAbstractFurnaceBlockEntity.this.cookTime = int_2;
                        break;
                    case 3:
                        CustomAbstractFurnaceBlockEntity.this.cookTimeTotal = int_2;
                }

            }

            public int size() {
                return 4;
            }
        };
        this.recipesUsed = Maps.newHashMap();
        this.recipeType = recipeType_1;
    }

    public static Map<Item, Integer> createFuelTimeMap() {
        Map<Item, Integer> map_1 = Maps.newLinkedHashMap();
        addFuel(map_1, (ItemConvertible)Items.LAVA_BUCKET, 20000);
        addFuel(map_1, (ItemConvertible)Blocks.COAL_BLOCK, 16000);
        addFuel(map_1, (ItemConvertible)Items.BLAZE_ROD, 2400);
        addFuel(map_1, (ItemConvertible)Items.COAL, 1600);
        addFuel(map_1, (ItemConvertible)Items.CHARCOAL, 1600);
        addFuel(map_1, (Tag)ItemTags.LOGS, 300);
        addFuel(map_1, (Tag)ItemTags.PLANKS, 300);
        addFuel(map_1, (Tag)ItemTags.WOODEN_STAIRS, 300);
        addFuel(map_1, (Tag)ItemTags.WOODEN_SLABS, 150);
        addFuel(map_1, (Tag)ItemTags.WOODEN_TRAPDOORS, 300);
        addFuel(map_1, (Tag)ItemTags.WOODEN_PRESSURE_PLATES, 300);
        addFuel(map_1, (ItemConvertible)Blocks.OAK_FENCE, 300);
        addFuel(map_1, (ItemConvertible)Blocks.BIRCH_FENCE, 300);
        addFuel(map_1, (ItemConvertible)Blocks.SPRUCE_FENCE, 300);
        addFuel(map_1, (ItemConvertible)Blocks.JUNGLE_FENCE, 300);
        addFuel(map_1, (ItemConvertible)Blocks.DARK_OAK_FENCE, 300);
        addFuel(map_1, (ItemConvertible)Blocks.ACACIA_FENCE, 300);
        addFuel(map_1, (ItemConvertible)Blocks.OAK_FENCE_GATE, 300);
        addFuel(map_1, (ItemConvertible)Blocks.BIRCH_FENCE_GATE, 300);
        addFuel(map_1, (ItemConvertible)Blocks.SPRUCE_FENCE_GATE, 300);
        addFuel(map_1, (ItemConvertible)Blocks.JUNGLE_FENCE_GATE, 300);
        addFuel(map_1, (ItemConvertible)Blocks.DARK_OAK_FENCE_GATE, 300);
        addFuel(map_1, (ItemConvertible)Blocks.ACACIA_FENCE_GATE, 300);
        addFuel(map_1, (ItemConvertible)Blocks.NOTE_BLOCK, 300);
        addFuel(map_1, (ItemConvertible)Blocks.BOOKSHELF, 300);
        addFuel(map_1, (ItemConvertible)Blocks.LECTERN, 300);
        addFuel(map_1, (ItemConvertible)Blocks.JUKEBOX, 300);
        addFuel(map_1, (ItemConvertible)Blocks.CHEST, 300);
        addFuel(map_1, (ItemConvertible)Blocks.TRAPPED_CHEST, 300);
        addFuel(map_1, (ItemConvertible)Blocks.CRAFTING_TABLE, 300);
        addFuel(map_1, (ItemConvertible)Blocks.DAYLIGHT_DETECTOR, 300);
        addFuel(map_1, (Tag)ItemTags.BANNERS, 300);
        addFuel(map_1, (ItemConvertible)Items.BOW, 300);
        addFuel(map_1, (ItemConvertible)Items.FISHING_ROD, 300);
        addFuel(map_1, (ItemConvertible)Blocks.LADDER, 300);
        addFuel(map_1, (Tag)ItemTags.SIGNS, 200);
        addFuel(map_1, (ItemConvertible)Items.WOODEN_SHOVEL, 200);
        addFuel(map_1, (ItemConvertible)Items.WOODEN_SWORD, 200);
        addFuel(map_1, (ItemConvertible)Items.WOODEN_HOE, 200);
        addFuel(map_1, (ItemConvertible)Items.WOODEN_AXE, 200);
        addFuel(map_1, (ItemConvertible)Items.WOODEN_PICKAXE, 200);
        addFuel(map_1, (Tag)ItemTags.WOODEN_DOORS, 200);
        addFuel(map_1, (Tag)ItemTags.BOATS, 200);
        addFuel(map_1, (Tag)ItemTags.WOOL, 100);
        addFuel(map_1, (Tag)ItemTags.WOODEN_BUTTONS, 100);
        addFuel(map_1, (ItemConvertible)Items.STICK, 100);
        addFuel(map_1, (Tag)ItemTags.SAPLINGS, 100);
        addFuel(map_1, (ItemConvertible)Items.BOWL, 100);
        addFuel(map_1, (Tag)ItemTags.CARPETS, 67);
        addFuel(map_1, (ItemConvertible)Blocks.DRIED_KELP_BLOCK, 4001);
        addFuel(map_1, (ItemConvertible)Items.CROSSBOW, 300);
        addFuel(map_1, (ItemConvertible)Blocks.BAMBOO, 50);
        addFuel(map_1, (ItemConvertible)Blocks.DEAD_BUSH, 100);
        addFuel(map_1, (ItemConvertible)Blocks.SCAFFOLDING, 50);
        addFuel(map_1, (ItemConvertible)Blocks.LOOM, 300);
        addFuel(map_1, (ItemConvertible)Blocks.BARREL, 300);
        addFuel(map_1, (ItemConvertible)Blocks.CARTOGRAPHY_TABLE, 300);
        addFuel(map_1, (ItemConvertible)Blocks.FLETCHING_TABLE, 300);
        addFuel(map_1, (ItemConvertible)Blocks.SMITHING_TABLE, 300);
        addFuel(map_1, (ItemConvertible)Blocks.COMPOSTER, 300);
        return map_1;
    }

    private static void addFuel(Map<Item, Integer> map_1, Tag<Item> tag_1, int int_1) {
        Iterator var3 = tag_1.values().iterator();

        while(var3.hasNext()) {
            Item item_1 = (Item)var3.next();
            map_1.put(item_1, int_1);
        }

    }

    private static void addFuel(Map<Item, Integer> map_1, ItemConvertible itemConvertible_1, int int_1) {
        map_1.put(itemConvertible_1.asItem(), int_1);
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    public void fromTag(CompoundTag compoundTag_1) {
        super.fromTag(compoundTag_1);
        this.inventory = DefaultedList.create(this.getInvSize(), ItemStack.EMPTY);
        Inventories.fromTag(compoundTag_1, this.inventory);
        this.burnTime = compoundTag_1.getShort("BurnTime");
        this.cookTime = compoundTag_1.getShort("CookTime");
        this.cookTimeTotal = compoundTag_1.getShort("CookTimeTotal");
        this.fuelTime = this.getFuelTime((ItemStack)this.inventory.get(1));
        int int_1 = compoundTag_1.getShort("RecipesUsedSize");

        for(int int_2 = 0; int_2 < int_1; ++int_2) {
            Identifier identifier_1 = new Identifier(compoundTag_1.getString("RecipeLocation" + int_2));
            int int_3 = compoundTag_1.getInt("RecipeAmount" + int_2);
            this.recipesUsed.put(identifier_1, int_3);
        }

    }

    public CompoundTag toTag(CompoundTag compoundTag_1) {
        super.toTag(compoundTag_1);
        compoundTag_1.putShort("BurnTime", (short)this.burnTime);
        compoundTag_1.putShort("CookTime", (short)this.cookTime);
        compoundTag_1.putShort("CookTimeTotal", (short)this.cookTimeTotal);
        Inventories.toTag(compoundTag_1, this.inventory);
        compoundTag_1.putShort("RecipesUsedSize", (short)this.recipesUsed.size());
        int int_1 = 0;

        for(Iterator var3 = this.recipesUsed.entrySet().iterator(); var3.hasNext(); ++int_1) {
            Entry<Identifier, Integer> map$Entry_1 = (Entry)var3.next();
            compoundTag_1.putString("RecipeLocation" + int_1, ((Identifier)map$Entry_1.getKey()).toString());
            compoundTag_1.putInt("RecipeAmount" + int_1, (Integer)map$Entry_1.getValue());
        }

        return compoundTag_1;
    }

    public void tick() {
        boolean boolean_1 = this.isBurning();
        boolean boolean_2 = false;
        if (this.isBurning()) {
            --this.burnTime;
        }

        if (!this.world.isClient) {
            ItemStack itemStack_1 = (ItemStack)this.inventory.get(1);
            if (!this.isBurning() && (itemStack_1.isEmpty() || ((ItemStack)this.inventory.get(0)).isEmpty())) {
                if (!this.isBurning() && this.cookTime > 0) {
                    this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
                }
            } else {
                Recipe<?> recipe_1 = (Recipe)this.world.getRecipeManager().getFirstMatch(this.recipeType, this, this.world).orElse(null);
                if (!this.isBurning() && this.canAcceptRecipeOutput(recipe_1)) {
                    this.burnTime = this.getFuelTime(itemStack_1);
                    this.fuelTime = this.burnTime;
                    if (this.isBurning()) {
                        boolean_2 = true;
                        if (!itemStack_1.isEmpty()) {
                            Item item_1 = itemStack_1.getItem();
                            itemStack_1.decrement(1);
                            if (itemStack_1.isEmpty()) {
                                Item item_2 = item_1.getRecipeRemainder();
                                this.inventory.set(1, item_2 == null ? ItemStack.EMPTY : new ItemStack(item_2));
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canAcceptRecipeOutput(recipe_1)) {
                    ++this.cookTime;
                    if (this.cookTime == this.cookTimeTotal) {
                        this.cookTime = 0;
                        this.cookTimeTotal = this.getCookTime();
                        this.craftRecipe(recipe_1);
                        boolean_2 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            }

//            if (boolean_1 != this.isBurning()) {
//                boolean_2 = true;
//                this.world.setBlockState(this.pos, (BlockState)this.world.getBlockState(this.pos).with(AbstractFurnaceBlock.LIT, this.isBurning()), 3);
//            }
        }

        if (boolean_2) {
            this.markDirty();
        }

    }

    protected boolean canAcceptRecipeOutput(@Nullable Recipe<?> recipe_1) {
        if (!((ItemStack)this.inventory.get(0)).isEmpty() && recipe_1 != null) {
            ItemStack itemStack_1 = recipe_1.getOutput();
            if (itemStack_1.isEmpty()) {
                return false;
            } else {
                ItemStack itemStack_2 = (ItemStack)this.inventory.get(2);
                if (itemStack_2.isEmpty()) {
                    return true;
                } else if (!itemStack_2.isItemEqualIgnoreDamage(itemStack_1)) {
                    return false;
                } else if (itemStack_2.getCount() < this.getInvMaxStackAmount() && itemStack_2.getCount() < itemStack_2.getMaxCount()) {
                    return true;
                } else {
                    return itemStack_2.getCount() < itemStack_1.getMaxCount();
                }
            }
        } else {
            return false;
        }
    }

    private void craftRecipe(@Nullable Recipe<?> recipe_1) {
        if (recipe_1 != null && this.canAcceptRecipeOutput(recipe_1)) {
            ItemStack itemStack_1 = (ItemStack)this.inventory.get(0);
            ItemStack itemStack_2 = recipe_1.getOutput();
            ItemStack itemStack_3 = (ItemStack)this.inventory.get(2);
            if (itemStack_3.isEmpty()) {
                this.inventory.set(2, itemStack_2.copy());
            } else if (itemStack_3.getItem() == itemStack_2.getItem()) {
                itemStack_3.increment(1);
            }

            if (!this.world.isClient) {
                this.setLastRecipe(recipe_1);
            }

            if (itemStack_1.getItem() == Blocks.WET_SPONGE.asItem() && !((ItemStack)this.inventory.get(1)).isEmpty() && ((ItemStack)this.inventory.get(1)).getItem() == Items.BUCKET) {
                this.inventory.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemStack_1.decrement(1);
        }
    }

    protected int getFuelTime(ItemStack itemStack_1) {
        if (itemStack_1.isEmpty()) {
            return 0;
        } else {
            Item item_1 = itemStack_1.getItem();
            return (Integer)createFuelTimeMap().getOrDefault(item_1, 0);
        }
    }

    protected int getCookTime() {
        return (Integer)this.world.getRecipeManager().getFirstMatch(this.recipeType, this, this.world).map(AbstractCookingRecipe::getCookTime).orElse(200);
    }

    public static boolean canUseAsFuel(ItemStack itemStack_1) {
        return createFuelTimeMap().containsKey(itemStack_1.getItem());
    }

    public int[] getInvAvailableSlots(Direction direction_1) {
        if (direction_1 == Direction.DOWN) {
            return BOTTOM_SLOTS;
        } else {
            return direction_1 == Direction.UP ? TOP_SLOTS : SIDE_SLOTS;
        }
    }

    public boolean canInsertInvStack(int int_1, ItemStack itemStack_1, @Nullable Direction direction_1) {
        return this.isValidInvStack(int_1, itemStack_1);
    }

    public boolean canExtractInvStack(int int_1, ItemStack itemStack_1, Direction direction_1) {
        if (direction_1 == Direction.DOWN && int_1 == 1) {
            Item item_1 = itemStack_1.getItem();
            if (item_1 != Items.WATER_BUCKET && item_1 != Items.BUCKET) {
                return false;
            }
        }

        return true;
    }

    public int getInvSize() {
        return this.inventory.size();
    }

    public boolean isInvEmpty() {
        Iterator var1 = this.inventory.iterator();

        ItemStack itemStack_1;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            itemStack_1 = (ItemStack)var1.next();
        } while(itemStack_1.isEmpty());

        return false;
    }

    public ItemStack getInvStack(int int_1) {
        return (ItemStack)this.inventory.get(int_1);
    }

    public ItemStack takeInvStack(int int_1, int int_2) {
        return Inventories.splitStack(this.inventory, int_1, int_2);
    }

    public ItemStack removeInvStack(int int_1) {
        return Inventories.removeStack(this.inventory, int_1);
    }

    public void setInvStack(int int_1, ItemStack itemStack_1) {
        ItemStack itemStack_2 = (ItemStack)this.inventory.get(int_1);
        boolean boolean_1 = !itemStack_1.isEmpty() && itemStack_1.isItemEqualIgnoreDamage(itemStack_2) && ItemStack.areTagsEqual(itemStack_1, itemStack_2);
        this.inventory.set(int_1, itemStack_1);
        if (itemStack_1.getCount() > this.getInvMaxStackAmount()) {
            itemStack_1.setCount(this.getInvMaxStackAmount());
        }

        if (int_1 == 0 && !boolean_1) {
            this.cookTimeTotal = this.getCookTime();
            this.cookTime = 0;
            this.markDirty();
        }

    }

    public boolean canPlayerUseInv(PlayerEntity playerEntity_1) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return playerEntity_1.squaredDistanceTo((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    public boolean isValidInvStack(int int_1, ItemStack itemStack_1) {
        if (int_1 == 2) {
            return false;
        } else if (int_1 != 1) {
            return true;
        } else {
            ItemStack itemStack_2 = (ItemStack)this.inventory.get(1);
            return canUseAsFuel(itemStack_1) || itemStack_1.getItem() == Items.BUCKET && itemStack_2.getItem() != Items.BUCKET;
        }
    }

    public void clear() {
        this.inventory.clear();
    }

    public void setLastRecipe(@Nullable Recipe<?> recipe_1) {
        if (recipe_1 != null) {
            this.recipesUsed.compute(recipe_1.getId(), (identifier_1, integer_1) -> {
                return 1 + (integer_1 == null ? 0 : integer_1);
            });
        }

    }

    @Nullable
    public Recipe<?> getLastRecipe() {
        return null;
    }

    public void unlockLastRecipe(PlayerEntity playerEntity_1) {
    }

    public void dropExperience(PlayerEntity playerEntity_1) {
        List<Recipe<?>> list_1 = Lists.newArrayList();
        Iterator var3 = this.recipesUsed.entrySet().iterator();

        while(var3.hasNext()) {
            Entry<Identifier, Integer> map$Entry_1 = (Entry)var3.next();
            playerEntity_1.world.getRecipeManager().get((Identifier)map$Entry_1.getKey()).ifPresent((recipe_1) -> {
                list_1.add(recipe_1);
                dropExperience(playerEntity_1, (Integer)map$Entry_1.getValue(), ((AbstractCookingRecipe)recipe_1).getExperience());
            });
        }

        playerEntity_1.unlockRecipes((Collection)list_1);
        this.recipesUsed.clear();
    }

    private static void dropExperience(PlayerEntity playerEntity_1, int int_1, float float_1) {
        int int_2;
        if (float_1 == 0.0F) {
            int_1 = 0;
        } else if (float_1 < 1.0F) {
            int_2 = MathHelper.floor((float)int_1 * float_1);
            if (int_2 < MathHelper.ceil((float)int_1 * float_1) && Math.random() < (double)((float)int_1 * float_1 - (float)int_2)) {
                ++int_2;
            }

            int_1 = int_2;
        }

        while(int_1 > 0) {
            int_2 = ExperienceOrbEntity.roundToOrbSize(int_1);
            int_1 -= int_2;
            playerEntity_1.world.spawnEntity(new ExperienceOrbEntity(playerEntity_1.world, playerEntity_1.x, playerEntity_1.y + 0.5D, playerEntity_1.z + 0.5D, int_2));
        }

    }

    public void provideRecipeInputs(RecipeFinder recipeFinder_1) {
        Iterator var2 = this.inventory.iterator();

        while(var2.hasNext()) {
            ItemStack itemStack_1 = (ItemStack)var2.next();
            recipeFinder_1.addItem(itemStack_1);
        }

    }
}
