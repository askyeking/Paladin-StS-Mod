package Paladin.cards;

import Paladin.PaladinMod;
import Paladin.orbs.SealOfRighteousness;
import Paladin.powers.DevotionPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.RemoveAllOrbsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import Paladin.characters.Paladin;

import static Paladin.PaladinMod.makeCardPath;

public class RighteousDuty extends CustomCard {

    /*
     * Righteous Duty - 1
     * Uncommon Skill
     * Remove your seal. Channel 1 Seal of Righteousness. Exhaust.
     * (Upgrade: Do not remove your seal).
     */

    // TEXT DECLARATION

    public static final String ID = PaladinMod.makeID("RighteousDuty");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("RighteousDuty.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Paladin.Enums.COLOR_GRAY;

    private static final int COST = 1;

    // /STAT DECLARATION/

    public RighteousDuty() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new RemoveAllOrbsAction());
        // Remove all orbs without evoking
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new SealOfRighteousness()));
        // Channel a Seal of Righteousness.
        AbstractDungeon.actionManager.addToBottom(
                new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(
                        AbstractDungeon.player, AbstractDungeon.player, new DevotionPower(p, 1), 1)
        );
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}