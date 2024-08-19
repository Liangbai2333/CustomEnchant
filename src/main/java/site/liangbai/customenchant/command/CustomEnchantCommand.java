package site.liangbai.customenchant.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;
import site.liangbai.customenchant.enchant.CustomEnchantment;

public class CustomEnchantCommand extends CommandBase {
    public static final String PERMISSION_USE = "customenchant.command.use";

    public CustomEnchantCommand() {
        PermissionAPI.registerNode(PERMISSION_USE, DefaultPermissionLevel.OP, "Custom enchant");
    }

    @Override
    public String getName() {
        return "customenchant";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/customenchant add/remove/reset/book <name> [level]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) sender;
        if (!PermissionAPI.hasPermission(player, PERMISSION_USE)) {
            player.sendMessage(new TextComponentString("You don't have permission to use this command!"));
            return;
        }
        if (args.length < 2) {
            player.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
            return;
        }
        String subCommand = args[0].toLowerCase();
        String name = args[1];

        CustomEnchantment enchantment = CustomEnchantment.registeredEnchantments.get(name);
        if (enchantment == null) {
            player.sendMessage(new TextComponentString("Unknown enchantment: " + name));
            return;
        }
        if (subCommand.equals("book")) {
            if (args.length != 3) {
                player.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
                return;
            }
            short level = Short.parseShort(args[2]);

            ItemStack enchantedBook = ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(enchantment, level));

            player.inventory.placeItemBackInInventory(player.world, enchantedBook);
            player.sendMessage(new TextComponentString("Successfully receiving enchantment book: " + name + " " + level + "."));
            return;
        }
        ItemStack item = player.getHeldItem(EnumHand.MAIN_HAND);
        if (item.isEmpty()) {
            player.sendMessage(new TextComponentString("You don't have item!"));
            return;
        }
        if (subCommand.equals("add")) {
            if (args.length != 3) {
                player.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
                return;
            }
            short level = Short.parseShort(args[2]);
            item.addEnchantment(enchantment, level);
            player.sendMessage(new TextComponentString("Successfully adding enchantment: " + name + " " + level + " to your item in hand."));
            return;
        }

        // 较为复杂的非add
        if (!subCommand.equals("remove") && !subCommand.equals("reset")) {
            player.sendMessage(new TextComponentString("Unknown subcommand: " + subCommand));
            return;
        }

        NBTTagCompound tagCompound = item.getTagCompound() != null ? item.getTagCompound() : new NBTTagCompound();
        if (!tagCompound.hasKey("ench", 9))
        {
            tagCompound.setTag("ench", new NBTTagList());
        }
        NBTTagList nbttaglist = tagCompound.getTagList("ench", 10);

        int index = -1;
        for (int i = 0; i < nbttaglist.tagCount(); i++) {
            NBTTagCompound tag = nbttaglist.getCompoundTagAt(i);
            if (tag.getShort("id") == Enchantment.getEnchantmentID(enchantment)) {
                index = i;
            }
        }
        if (index == -1) {
            player.sendMessage(new TextComponentString("Your item don't have the enchantment: " + name));
            return;
        }

        if (subCommand.equals("remove")) {
            if (args.length != 2) {
                player.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
                return;
            }
            nbttaglist.removeTag(index);
            player.sendMessage(new TextComponentString("Successfully removing enchantment: " + name + " attaching your item in hand."));
        }
        if (subCommand.equals("reset")) {
            if (args.length != 3) {
                player.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
                return;
            }
            NBTTagCompound tag = nbttaglist.getCompoundTagAt(index);
            short level = Short.parseShort(args[2]);
            tag.setShort("lvl", level);
            player.sendMessage(new TextComponentString("Successfully resetting enchantment: " + name + " attaching your item in hand."));
        }

        item.setTagCompound(tagCompound);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
