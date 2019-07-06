package xevion.everythingonastick;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xevion.everythingonastick.craftingtable.CraftingWandItem;
import xevion.everythingonastick.enchantingtable.EnchantingTableWandItem;
import xevion.everythingonastick.enderchest.EnderchestWandItem;
import xevion.everythingonastick.furnace.FurnaceWandItem;

public class RegisterWands {
    static final CraftingWandItem CRAFTING_WAND = new CraftingWandItem(new Item.Settings().group(ItemGroup.TOOLS));
    static final FurnaceWandItem FURNACE_WAND = new FurnaceWandItem(new Item.Settings().group(ItemGroup.TOOLS));
    static final EnderchestWandItem ENDERCHEST_WAND = new EnderchestWandItem(new Item.Settings().group(ItemGroup.TOOLS));
    static final EnchantingTableWandItem ENCHANTING_TABLE_WAND = new EnchantingTableWandItem(new Item.Settings().group(ItemGroup.TOOLS));

    public static void register() {
        register("crafting-table-on-a-stick", CRAFTING_WAND);
        register("furnace-on-a-stick", FURNACE_WAND);
        register("enderchest-on-a-stick", ENDERCHEST_WAND);
        register("enchanting-table-on-a-stick", ENCHANTING_TABLE_WAND);
    }

    public static void register(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier("everything-on-a-stick", name), item);
    }
}
