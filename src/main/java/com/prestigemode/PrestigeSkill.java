package com.prestigemode;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetPositionMode;
import net.runelite.api.widgets.WidgetType;

import javax.inject.Inject;

@Slf4j
class PrestigeSkill {
    private static final int XP_FOR_99 = 13034431;
    private static final int MAX_PRESTIGE = 15;
    private static final int PRESTIGE_SPRITE_BASE = -5000;
    private final Client client;
    private boolean enabled;
    private final boolean isCombatSkill;
    private boolean showBanners;
    private boolean enableCombatUsability;

    public final Skill skill;
    private Widget borderWidget;
    private final int skillWidgetId;

    @Inject
    public PrestigeSkill(
            Client client,
            int skillWidgetId,
            Skill skill,
            Boolean enabled,
            Boolean isCombatSkill,
            Boolean showBanners,
            Boolean enableCombatUsability
        )
    {
        this.client = client;
        this.skill = skill;
        this.skillWidgetId = skillWidgetId;
        this.enabled = enabled;
        this.isCombatSkill = isCombatSkill;
        this.showBanners = showBanners;
        this.enableCombatUsability = enableCombatUsability;
    }

    public void disable() {
        if (!this.enabled) {
            return;
        }

        this.enabled = false;
        this.destroy();
    }

    public void enable() {
        if (this.enabled) {
            return;
        }

        this.enabled = true;
        this.update();
    }

    public void configChanged(Boolean showBanners, Boolean enableCombatUsability) {
        this.showBanners = showBanners;
        if (!showBanners && this.borderWidget != null) {
            borderWidget.setSpriteId(0);
            borderWidget.setHidden(true);
            borderWidget = null;
        }

        this.enableCombatUsability = enableCombatUsability;
        client.queueChangedSkill(skill);

        this.update();
    }

    public void destroy() {
        if (borderWidget != null) {
            borderWidget.setSpriteId(0);
            borderWidget.setHidden(true);
            borderWidget = null;
        }

        client.queueChangedSkill(skill);
    }

    public void update() {
        if (!this.enabled) {
            return;
        }

        Widget skillWidget = client.getWidget(this.skillWidgetId);
        if (skillWidget == null) {
            return;
        }

        int totalSkillXp = client.getSkillExperience(this.skill);
        int prestige = getPrestige(totalSkillXp);
        int level = getLevel(totalSkillXp, prestige);

        Widget activeLevel = skillWidget.getChild(3);
        boolean shouldReplaceNumerator = !this.isCombatSkill || !this.enableCombatUsability;
        if (activeLevel != null && shouldReplaceNumerator) {
            activeLevel.setText(String.valueOf(level));
        }

        Widget actualLevel = skillWidget.getChild(4);
        if (actualLevel != null) {
            actualLevel.setText(String.valueOf(level));
        }

        if (this.showBanners) {
            int spriteId = PRESTIGE_SPRITE_BASE + prestige;
            if (this.borderWidget != null) {
                this.borderWidget.setSpriteId(spriteId);
            } else {
                this.borderWidget = skillWidget.createChild(WidgetType.GRAPHIC);
                borderWidget.getId();
                borderWidget.setSpriteId(spriteId);
                borderWidget.setOriginalWidth(skillWidget.getWidth());
                borderWidget.setOriginalHeight(skillWidget.getHeight());
                borderWidget.setXPositionMode(WidgetPositionMode.ABSOLUTE_LEFT);
                borderWidget.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
                borderWidget.setOriginalX(0);
                borderWidget.setOriginalY(0);
            }
        }

        Widget skillsPage = client.getWidget(InterfaceID.Stats.UNIVERSE);
        if (skillsPage != null) {
            skillsPage.revalidate();
            skillWidget.revalidate();
            if (borderWidget != null) {
                borderWidget.revalidate();
            }
        }
    }

    private int getPrestige(int totalSkillXp) {
        return Math.min((int) Math.floor((double) totalSkillXp / XP_FOR_99), MAX_PRESTIGE);
    }

    private int getLevel(int totalSkillXp, int prestige) {
        int adjustedXp = totalSkillXp - prestige * XP_FOR_99;
        if (prestige == MAX_PRESTIGE) {
            adjustedXp = totalSkillXp;
        }

        return net.runelite.api.Experience.getLevelForXp(adjustedXp);
    }
}
