package Paladin.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import Paladin.cards.AbstractPaladinCard;

import static Paladin.PaladinMod.makeID;

public class PaladinSecondMagicNumber extends DynamicVariable {

    //For in-depth comments, check the other variable(PaladinCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return makeID("SecondMagic");
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "Paladin:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractPaladinCard) card).isPaladinSecondMagicNumberModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractPaladinCard) card).PaladinSecondMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractPaladinCard) card).PaladinBaseSecondMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractPaladinCard) card).upgradedPaladinSecondMagicNumber;
    }
}