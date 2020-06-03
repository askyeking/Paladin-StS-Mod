package Paladin.cards;

import Paladin.PaladinMod;
import Paladin.characters.Paladin;
import Paladin.powers.DevotionPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Barrage;
import com.megacrit.cardcrawl.cards.red.Immolate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import static Paladin.PaladinMod.makeCardPath;


public class RighteousAnger extends CustomCard {

    /*
    Righteous Anger - 0
    Common Attack
    Deal 2(3) damage per devotion. Put a copy of this into your discard pile.
    */

    //Card text variable declarations
    public static final String ID = PaladinMod.makeID("RighteousAnger");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("RighteousAnger.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //Stat value variable declarations
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = Paladin.Enums.COLOR_GRAY;
    private static final int COST = 0;
    private static final int DAMAGE = 0;


    public RighteousAnger() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = 2;


    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractDungeon.actionManager.addToBottom( new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
            AbstractDungeon.actionManager.addToBottom( new MakeTempCardInDiscardAction(new RighteousAnger(), 1));
    }

    @Override
    public void applyPowers() {
        if (AbstractDungeon.player.getPower(DevotionPower.POWER_ID) != null) {
            int devotion = AbstractDungeon.player.getPower(DevotionPower.POWER_ID).amount;
            this.baseDamage = this.magicNumber * devotion;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new RighteousAnger();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

}
