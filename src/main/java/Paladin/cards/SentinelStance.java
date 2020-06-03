package Paladin.cards;

import Paladin.PaladinMod;
import Paladin.characters.Paladin;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Paladin.PaladinMod.makeCardPath;

public class SentinelStance extends CustomCard {

    /*
     * Sentinel Stance - 1
     * Common
     * Gain 7 block. Valiance - Gain 2 (5) plated armor.
     */

    // TEXT DECLARATION

    public static final String ID = PaladinMod.makeID("SentinelStance");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("SentinelStance.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Paladin.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 7;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC_NUMBER = 3;



    // /STAT DECLARATION/

    public SentinelStance() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = baseMagicNumber = MAGIC;
        this.block = baseBlock = BLOCK;
    }

    @Override
    public void applyPowers() {
        if (!AbstractDungeon.getCurrRoom().eliteTrigger) {
            this.magicNumber = 0;
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block)); //Block + Plated
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new com.megacrit.cardcrawl.powers.PlatedArmorPower(AbstractDungeon.player, magicNumber), magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}
