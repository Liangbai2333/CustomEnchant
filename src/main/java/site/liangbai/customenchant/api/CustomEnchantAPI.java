package site.liangbai.customenchant.api;

import site.liangbai.customenchant.enchant.CustomEnchantment;

import java.util.HashMap;
import java.util.Map;

public final class CustomEnchantAPI {
    public static Object getCustomEnchant(String name) {
        return CustomEnchantment.registeredEnchantments.get(name);
    }
    
    public static Map<String, Object> getCustomEnchants() {
        return new HashMap<>(CustomEnchantment.registeredEnchantments);
    }
}
