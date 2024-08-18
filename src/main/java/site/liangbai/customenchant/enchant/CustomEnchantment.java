package site.liangbai.customenchant.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import site.liangbai.customenchant.CustomEnchant;

import java.util.HashMap;
import java.util.Map;

public class CustomEnchantment extends Enchantment {
    public static final Map<String, CustomEnchantment> registeredEnchantments = new HashMap<>();

    public CustomEnchantment(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots, String name) {
        super(rarityIn, typeIn, slots);
        this.setName(CustomEnchant.MODID + "." + name);
        this.setRegistryName(CustomEnchant.MODID, name);
        registeredEnchantments.put(name, this);
    }
}
