package Paladin.cards;

import Paladin.PaladinMod;
import Paladin.characters.Paladin;
import Paladin.powers.DevotionPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Paladin.PaladinMod.makeCardPath;

public class BlessedBreastplate extends CustomCard {


    public static final String ID = PaladinMod.makeID("BlessedBreastplate");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("BlessingOfKings.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    //Stat value variable declarations
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = Paladin.Enums.COLOR_GRAY;
    private static final int COST = 1;
    private static final int BLOCK = 8;







    public BlessedBreastplate() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.block = baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom( new GainBlockAction(p,p, this.block));
        AbstractDungeon.actionManager.addToBottom( new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DevotionPower(p, 1), 1));


    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.block = 11;
        }
    }



}
