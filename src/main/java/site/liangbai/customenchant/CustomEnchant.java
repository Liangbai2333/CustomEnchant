package site.liangbai.customenchant;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import site.liangbai.customenchant.command.CustomEnchantCommand;
import site.liangbai.customenchant.enchant.CustomEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = CustomEnchant.MODID, name = CustomEnchant.NAME, version = CustomEnchant.VERSION)
public class CustomEnchant
{
    public static final String MODID = "customenchant";
    public static final String NAME = "Custom Enchant";
    public static final String VERSION = "1.0.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CustomEnchantCommand());
    }

    @SubscribeEvent
    public void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
        logger.info("Registering custom enchantments.");
        event.getRegistry().registerAll(
                new CustomEnchantment(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND}
                        , "behead"),
                new CustomEnchantment(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND}
                        , "chain_collection"),
                new CustomEnchantment(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND}
                        , "farm"),
                new CustomEnchantment(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND}
                        , "farmer"),
                new CustomEnchantment(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ALL, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND}
                        , "damage_protection"),
                new CustomEnchantment(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND}
                        , "smelt"),
                new CustomEnchantment(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ALL, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND}
                        , "moving_light_source"),
                new CustomEnchantment(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND}
                        , "gold_digger"),
                new CustomEnchantment(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ALL, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND}
                        , "eternal")

        );
        logger.info("Registered custom enchantments.");
    }
}
