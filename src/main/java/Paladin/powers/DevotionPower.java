package Paladin.powers;

import Paladin.PaladinMod;
import Paladin.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Paladin.PaladinMod.makePowerPath;

public class DevotionPower extends AbstractPower {
    public static final String POWER_ID = PaladinMod.makeID("DevotionPower");
    private static final PowerStrings powerStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int maximumdevotion = 5;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("devotionPower84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("devotionPower32.png"));

    public DevotionPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.priority = 0;
        this.canGoNegative = false;
        updateDescription();

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

    }

    @Override
    public void stackPower(int stackAmount) {
        {
            this.fontScale = 8.0F;
            this.amount += stackAmount;
            if(this.amount >= maximumdevotion){
                this.amount = maximumdevotion;
            }
        }
    }

    @Override
        public void updateDescription(){
            {
                this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
            }
    }
}