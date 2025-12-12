package com.prestigemode;

import lombok.extern.slf4j.Slf4j;
import com.google.inject.Provides;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.util.ImageUtil;

import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

@Slf4j
@PluginDescriptor(
	name = "Prestige Mode",
    description = "Visual only! Adds a 'Prestige Mode' to the skills tab. Resets skills to 1 once you reach 99, and each time you reach 99 a higher tier prestige banner is displayed.",
    tags = {"skills", "stats", "levels", "goals", "prestige", "max"}
)
public class PrestigeModePlugin extends Plugin
{
    private static final int MAX_PRESTIGE = 15;
    private static final int PRESTIGE_SPRITE_BASE = -5000;

    private final Map<Skill, PrestigeSkill> prestigeSkills = new HashMap<>();
    private boolean isSkillsVisible = false;

    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    @Inject
    private PrestigeModeConfig config;

    @Provides
    PrestigeModeConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(PrestigeModeConfig.class);
    }

    @Override
    protected void startUp()
    {
        prestigeSkills.put(Skill.ATTACK, new PrestigeSkill(client, InterfaceID.Stats.ATTACK, Skill.ATTACK, this.isSkillEnabled(Skill.ATTACK), true, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.STRENGTH, new PrestigeSkill(client, InterfaceID.Stats.STRENGTH, Skill.STRENGTH, this.isSkillEnabled(Skill.STRENGTH), true, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.DEFENCE, new PrestigeSkill(client, InterfaceID.Stats.DEFENCE, Skill.DEFENCE, this.isSkillEnabled(Skill.DEFENCE), true, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.RANGED, new PrestigeSkill(client, InterfaceID.Stats.RANGED, Skill.RANGED, this.isSkillEnabled(Skill.RANGED), true, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.PRAYER, new PrestigeSkill(client, InterfaceID.Stats.PRAYER, Skill.PRAYER, this.isSkillEnabled(Skill.PRAYER), true, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.MAGIC, new PrestigeSkill(client, InterfaceID.Stats.MAGIC, Skill.MAGIC, this.isSkillEnabled(Skill.MAGIC), true, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.RUNECRAFT, new PrestigeSkill(client, InterfaceID.Stats.RUNECRAFT, Skill.RUNECRAFT, this.isSkillEnabled(Skill.RUNECRAFT), false, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.CONSTRUCTION, new PrestigeSkill(client, InterfaceID.Stats.CONSTRUCTION, Skill.CONSTRUCTION, this.isSkillEnabled(Skill.CONSTRUCTION), false, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.HITPOINTS, new PrestigeSkill(client, InterfaceID.Stats.HITPOINTS, Skill.HITPOINTS, this.isSkillEnabled(Skill.HITPOINTS), true, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.AGILITY, new PrestigeSkill(client, InterfaceID.Stats.AGILITY, Skill.AGILITY, this.isSkillEnabled(Skill.AGILITY), false, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.HERBLORE, new PrestigeSkill(client, InterfaceID.Stats.HERBLORE, Skill.HERBLORE, this.isSkillEnabled(Skill.HERBLORE), false, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.THIEVING, new PrestigeSkill(client, InterfaceID.Stats.THIEVING, Skill.THIEVING, this.isSkillEnabled(Skill.THIEVING), false, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.CRAFTING, new PrestigeSkill(client, InterfaceID.Stats.CRAFTING, Skill.CRAFTING, this.isSkillEnabled(Skill.CRAFTING), false, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.FLETCHING, new PrestigeSkill(client, InterfaceID.Stats.FLETCHING, Skill.FLETCHING, this.isSkillEnabled(Skill.FLETCHING), false, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.SLAYER, new PrestigeSkill(client, InterfaceID.Stats.SLAYER, Skill.SLAYER, this.isSkillEnabled(Skill.SLAYER), false, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.HUNTER, new PrestigeSkill(client, InterfaceID.Stats.HUNTER, Skill.HUNTER, this.isSkillEnabled(Skill.HUNTER), false, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.MINING, new PrestigeSkill(client, InterfaceID.Stats.MINING, Skill.MINING, this.isSkillEnabled(Skill.MINING), false, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.SMITHING, new PrestigeSkill(client, InterfaceID.Stats.SMITHING, Skill.SMITHING, this.isSkillEnabled(Skill.SMITHING), false, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.FISHING, new PrestigeSkill(client, InterfaceID.Stats.FISHING, Skill.FISHING, this.isSkillEnabled(Skill.FISHING), false, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.COOKING, new PrestigeSkill(client, InterfaceID.Stats.COOKING, Skill.COOKING, this.isSkillEnabled(Skill.COOKING), false, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.FIREMAKING, new PrestigeSkill(client, InterfaceID.Stats.FIREMAKING, Skill.FIREMAKING, this.isSkillEnabled(Skill.FIREMAKING), false, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.WOODCUTTING, new PrestigeSkill(client, InterfaceID.Stats.WOODCUTTING, Skill.WOODCUTTING, this.isSkillEnabled(Skill.WOODCUTTING), false, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.FARMING, new PrestigeSkill(client, InterfaceID.Stats.FARMING, Skill.FARMING, this.isSkillEnabled(Skill.FARMING), false, this.config.showBanners(), this.config.combatUsability()));
        prestigeSkills.put(Skill.SAILING, new PrestigeSkill(client, InterfaceID.Stats.SAILING, Skill.SAILING, this.isSkillEnabled(Skill.SAILING), false, this.config.showBanners(), this.config.combatUsability()));

        for (int i = 1; i <= MAX_PRESTIGE; i++)
        {
            String path = "/borders/prestige-" + i + ".png";
            try
            {
                InputStream iStream = getClass().getResourceAsStream(path);
                if (iStream == null) {
                    continue;
                }

                BufferedImage image = ImageIO.read(iStream);
                SpritePixels sprite = ImageUtil.getImageSpritePixels(image, client);
                int spriteId = PRESTIGE_SPRITE_BASE + i;
                client.getSpriteOverrides().put(spriteId, sprite);
            }
            catch (IOException e)
            {
                String err = "Error loading prestige image " + path;
                log.error(err);
            }
        }

        clientThread.invoke(() ->
        {
            renderPrestigeOverlays();
            Widget skillsPage = client.getWidget(InterfaceID.Stats.UNIVERSE);
            if (skillsPage != null) {
                skillsPage.revalidate();
            }
        });
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event) {
        clientThread.invoke(() ->
        {
            for (PrestigeSkill prestigeSkill : prestigeSkills.values()) {
                boolean isEnabled = this.isSkillEnabled(prestigeSkill.skill);
                if (isEnabled) {
                    prestigeSkill.enable();
                } else {
                    prestigeSkill.disable();
                }
                prestigeSkill.configChanged(this.config.showBanners(), this.config.combatUsability());
            }
        });
    }

    @Override
    protected void shutDown()
    {
        for (PrestigeSkill prestigeSkill : prestigeSkills.values()) {
            prestigeSkill.destroy();
        }
        prestigeSkills.clear();


        for (int i = 1; i <= MAX_PRESTIGE; i++)
        {
            int spriteId = PRESTIGE_SPRITE_BASE + i;
            client.getSpriteOverrides().remove(spriteId);
        }
    }

    private boolean isSkillEnabled(Skill skill) {
        String skillName = skill.getName();
        boolean isEnabled = false;
        switch (skillName) {
            case("Attack"):
                isEnabled = this.config.attackEnabled();
                break;
            case("Defence"):
                isEnabled = this.config.defenceEnabled();
                break;
            case("Strength"):
                isEnabled = this.config.strengthEnabled();
                break;
            case("Hitpoints"):
                isEnabled = this.config.hitpointsEnabled();
                break;
            case("Ranged"):
                isEnabled = this.config.rangedEnabled();
                break;
            case("Prayer"):
                isEnabled = this.config.prayerEnabled();
                break;
            case("Magic"):
                isEnabled = this.config.magicEnabled();
                break;
            case("Cooking"):
                isEnabled = this.config.cookingEnabled();
                break;
            case("Woodcutting"):
                isEnabled = this.config.woodcuttingEnabled();
                break;
            case("Fletching"):
                isEnabled = this.config.fletchingEnabled();
                break;
            case("Fishing"):
                isEnabled = this.config.fishingEnabled();
                break;
            case("Firemaking"):
                isEnabled = this.config.firemakingEnabled();
                break;
            case("Crafting"):
                isEnabled = this.config.craftingEnabled();
                break;
            case("Smithing"):
                isEnabled = this.config.smithingEnabled();
                break;
            case("Mining"):
                isEnabled = this.config.miningEnabled();
                break;
            case("Herblore"):
                isEnabled = this.config.herbloreEnabled();
                break;
            case("Agility"):
                isEnabled = this.config.agilityEnabled();
                break;
            case("Thieving"):
                isEnabled = this.config.thievingEnabled();
                break;
            case("Slayer"):
                isEnabled = this.config.slayerEnabled();
                break;
            case("Farming"):
                isEnabled = this.config.farmingEnabled();
                break;
            case("Runecraft"):
                isEnabled = this.config.runecraftEnabled();
                break;
            case("Hunter"):
                isEnabled = this.config.hunterEnabled();
                break;
            case("Construction"):
                isEnabled = this.config.constructionEnabled();
                break;
            case("Sailing"):
                isEnabled = this.config.sailingEnabled();
                break;
        }

        return isEnabled;
    }

    private void renderPrestigeOverlays() {
        for (PrestigeSkill prestigeSkill : prestigeSkills.values()) {
            prestigeSkill.update();
        }
    }

    @Subscribe
    public void onPostClientTick(PostClientTick event)
    {
        Widget skillsPage = client.getWidget(InterfaceID.Stats.UNIVERSE);
        if (skillsPage == null) {
            isSkillsVisible = false;
            return;
        }

        if (skillsPage.isHidden()) {
            isSkillsVisible = false;
            return;
        }

        boolean isVisibilityChanged = !isSkillsVisible;
        isSkillsVisible = true;
        if (isVisibilityChanged) {
            renderPrestigeOverlays();
        }
    }

    @Subscribe
    public void onGameTick(GameTick event)
    {
        if (isSkillsVisible) {
            renderPrestigeOverlays();
        }
    }
}

