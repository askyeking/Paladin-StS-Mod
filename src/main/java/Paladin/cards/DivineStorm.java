package Paladin.cards;

import Paladin.PaladinMod;
import Paladin.characters.Paladin;
import Paladin.powers.DevotionPower;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnReceivePowerPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;


import static Paladin.PaladinMod.makeCardPath;

/*
Blessing of Kings - 2
Uncommon Skill
Gain 2(4) dexterity and Strength
*/
public class DivineStorm extends CustomCard {

    public static final String ID = PaladinMod.makeID("DivineStorm");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("DivineStorm.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    //Stat value variable declarations
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Paladin.Enums.COLOR_GRAY;
    private static final int COST = 3;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 5;






    public DivineStorm() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;

    }

    public DivineStorm(boolean exhaust, boolean upgraded) {
        super(ID, NAME, IMG, 0, DESCRIPTION.substring(0,95) + " Exhaust.", TYPE, COLOR, RARITY, TARGET);
        this.exhaust = exhaust;
        this.baseDamage = DAMAGE;
        if (upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                // a list of existing actions can be found at com.megacrit.cardcrawl.actions but
                // Chances are you'd instead look at "hey my card is similar to this basegame card"
                // Let's find out what action *it* uses.
                // I.e. i want energy gain or card draw, lemme check out Adrenaline
                // P.s. if you want to damage ALL enemies OUTSIDE of a card, check out the custom orb.
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if(p.hasPower(DevotionPower.POWER_ID)) {
            int devo = p.getPower(DevotionPower.POWER_ID).amount;

            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, DevotionPower.POWER_ID));

            for (int i = 0; i < devo; i++) {
                p.hand.addToHand(new DivineStorm(true, this.upgraded));
            }
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }




}
