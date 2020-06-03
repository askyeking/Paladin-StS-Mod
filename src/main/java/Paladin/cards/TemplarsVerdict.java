package Paladin.cards;

import Paladin.PaladinMod;
        import Paladin.characters.Paladin;
        import Paladin.powers.DevotionPower;
        import basemod.abstracts.CustomCard;
        import basemod.helpers.BaseModCardTags;
        import com.megacrit.cardcrawl.actions.AbstractGameAction;
        import com.megacrit.cardcrawl.actions.common.DamageAction;
        import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
        import com.megacrit.cardcrawl.cards.DamageInfo;
        import com.megacrit.cardcrawl.cards.red.Whirlwind;
        import com.megacrit.cardcrawl.characters.AbstractPlayer;
        import com.megacrit.cardcrawl.core.CardCrawlGame;
        import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
        import com.megacrit.cardcrawl.localization.CardStrings;
        import com.megacrit.cardcrawl.monsters.AbstractMonster;

        import static Paladin.PaladinMod.makeCardPath;

public class TemplarsVerdict extends CustomCard {

    /*
     * Templar's Verdict - 2
     * Starter Attack
     * Strike Deal 10 damage. If you have 3(2) or more Devotion, spend it and deal 25 instead.
     */

    // TEXT DECLARATION

    public static final String ID = PaladinMod.makeID("TemplarsVerdict");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("TemplarsVerdict.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Paladin.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 10;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC_NUMBER = -1;
    private static final int ACTIVATED_DAMAGE = 25;


    // /STAT DECLARATION/

    public TemplarsVerdict() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.tags.add(BaseModCardTags.BASIC_STRIKE);
        this.tags.add(CardTags.STRIKE);
        this.magicNumber = baseMagicNumber = MAGIC;
        new Whirlwind();
    }



    @Override
    public void applyPowers() {
        int originalBaseDamage = this.baseDamage;
        this.baseDamage = ACTIVATED_DAMAGE;
        super.applyPowers();
        int activeDamage = this.damage;
        this.baseDamage = originalBaseDamage;
        super.applyPowers();
        this.baseBlock = ACTIVATED_DAMAGE;
        this.block = activeDamage;
        this.isBlockModified = activeDamage != ACTIVATED_DAMAGE;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int originalBaseDamage = this.baseDamage;
        this.baseDamage = ACTIVATED_DAMAGE;
        super.calculateCardDamage(mo);
        int activeDamage = this.damage;
        this.baseDamage = originalBaseDamage;
        super.calculateCardDamage(mo);
        this.baseBlock = ACTIVATED_DAMAGE;
        this.block = activeDamage;
        this.isBlockModified = activeDamage != ACTIVATED_DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if ((AbstractDungeon.player.hasPower(DevotionPower.POWER_ID)) && AbstractDungeon.player.getPower(DevotionPower.POWER_ID).amount >= this.magicNumber) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, DevotionPower.POWER_ID, this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, block, damageTypeForTurn), // Use block variable, because that's where the activated damage was stored.
                            AbstractGameAction.AttackEffect.FIRE));
        }
        else {
            AbstractDungeon.actionManager.addToBottom( // Normal damage.
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                            AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        }
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}
