package Paladin.relics;

import Paladin.PaladinMod;
import Paladin.orbs.SealOfRighteousness;
import Paladin.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Paladin.PaladinMod.makeRelicOutlinePath;
import static Paladin.PaladinMod.makeRelicPath;

public class Ashbringer extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = PaladinMod.makeID("Ashbringer");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Ashbringer.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Ashbringer.png"));

    public Ashbringer() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    public void atPreBattle()
    {
        com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.channelOrb(new SealOfRighteousness());
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
