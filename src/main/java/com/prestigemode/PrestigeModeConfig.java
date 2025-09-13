package com.prestigemode;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("prestigeMode")
public interface PrestigeModeConfig extends Config
{
    @ConfigSection(
            name = "Options",
            description = "Common options for Prestige Mode.",
            position = 0,
            closedByDefault = false
    )
    String SECTION_OPTIONS = "Options";

    @ConfigItem(
            keyName = "combatUsability",
            section = SECTION_OPTIONS,
            name = "Combat Usability",
            description = "Only replace the denominator for combat skills to avoid replacing current HP, prayer points, or stat boots/drains."
    )
    default boolean combatUsability()
    {
        return true;
    }

    @ConfigItem(
            keyName = "showBanners",
            section = SECTION_OPTIONS,
            name = "Show prestige banners",
            description = "Toggle rendering of prestige banners. Disabling will still replace level numbers, but won't show any indication of current prestige level."
    )
    default boolean showBanners()
    {
        return true;
    }
    @ConfigSection(
            name = "Skills",
            description = "Select which skills to enable.",
            position = 1,
            closedByDefault = false
    )
    String SECTION_SKILLS = "Skills";

    @ConfigItem(
            keyName = "attackEnabled",
            section = SECTION_SKILLS,
            name = "Attack",
            description = "Enable prestige mode for the Attack skill"
    )
    default boolean attackEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "strengthEnabled",
            section = SECTION_SKILLS,
            name = "Strength",
            description = "Enable prestige mode for the Strength skill"
    )
    default boolean strengthEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "defenceEnabled",
            section = SECTION_SKILLS,
            name = "Defence",
            description = "Enable prestige mode for the Defence skill"
    )
    default boolean defenceEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "rangedEnabled",
            section = SECTION_SKILLS,
            name = "Ranged",
            description = "Enable prestige mode for the Ranged skill"
    )
    default boolean rangedEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "prayerEnabled",
            section = SECTION_SKILLS,
            name = "Prayer",
            description = "Enable prestige mode for the Prayer skill"
    )
    default boolean prayerEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "magicEnabled",
            section = SECTION_SKILLS,
            name = "Magic",
            description = "Enable prestige mode for the Magic skill"
    )
    default boolean magicEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "runecraftEnabled",
            section = SECTION_SKILLS,
            name = "Runecraft",
            description = "Enable prestige mode for the Runecraft skill"
    )
    default boolean runecraftEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "constructionEnabled",
            section = SECTION_SKILLS,
            name = "Construction",
            description = "Enable prestige mode for the Construction skill"
    )
    default boolean constructionEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "hitpointsEnabled",
            section = SECTION_SKILLS,
            name = "Hitpoints",
            description = "Enable prestige mode for the Hitpoints skill"
    )
    default boolean hitpointsEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "agilityEnabled",
            section = SECTION_SKILLS,
            name = "Agility",
            description = "Enable prestige mode for the Agility skill"
    )
    default boolean agilityEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "herbloreEnabled",
            section = SECTION_SKILLS,
            name = "Herblore",
            description = "Enable prestige mode for the Herblore skill"
    )
    default boolean herbloreEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "thievingEnabled",
            section = SECTION_SKILLS,
            name = "Thieving",
            description = "Enable prestige mode for the Thieving skill"
    )
    default boolean thievingEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "craftingEnabled",
            section = SECTION_SKILLS,
            name = "Crafting",
            description = "Enable prestige mode for the Crafting skill"
    )
    default boolean craftingEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "fletchingEnabled",
            section = SECTION_SKILLS,
            name = "Fletching",
            description = "Enable prestige mode for the Fletching skill"
    )
    default boolean fletchingEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "slayerEnabled",
            section = SECTION_SKILLS,
            name = "Slayer",
            description = "Enable prestige mode for the Slayer skill"
    )
    default boolean slayerEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "hunterEnabled",
            section = SECTION_SKILLS,
            name = "Hunter",
            description = "Enable prestige mode for the Hunter skill"
    )
    default boolean hunterEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "miningEnabled",
            section = SECTION_SKILLS,
            name = "Mining",
            description = "Enable prestige mode for the Mining skill"
    )
    default boolean miningEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "smithingEnabled",
            section = SECTION_SKILLS,
            name = "Smithing",
            description = "Enable prestige mode for the Smithing skill"
    )
    default boolean smithingEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "fishingEnabled",
            section = SECTION_SKILLS,
            name = "Fishing",
            description = "Enable prestige mode for the Fishing skill"
    )
    default boolean fishingEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "cookingEnabled",
            section = SECTION_SKILLS,
            name = "Cooking",
            description = "Enable prestige mode for the Cooking skill"
    )
    default boolean cookingEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "firemakingEnabled",
            section = SECTION_SKILLS,
            name = "Firemaking",
            description = "Enable prestige mode for the Firemaking skill"
    )
    default boolean firemakingEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "woodcuttingEnabled",
            section = SECTION_SKILLS,
            name = "Woodcutting",
            description = "Enable prestige mode for the Woodcutting skill"
    )
    default boolean woodcuttingEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "farmingEnabled",
            section = SECTION_SKILLS,
            name = "Farming",
            description = "Enable prestige mode for the Farming skill"
    )
    default boolean farmingEnabled()
    {
        return true;
    }
}