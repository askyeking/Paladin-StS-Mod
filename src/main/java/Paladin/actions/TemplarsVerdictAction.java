
package Paladin.actions;
 
 import Paladin.powers.DevotionPower;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.GameActionManager;
 import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
 import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 
 public class TemplarsVerdictAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
 {
   public TemplarsVerdictAction(AbstractCreature target)
   {
     this.duration = Settings.ACTION_DUR_XFAST;
     this.actionType = AbstractGameAction.ActionType.BLOCK;
     this.target = target;
   }
   
   public void update()
   {
     if ((this.target != null) && (this.target.hasPower("DevotionPower"))) {
       AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.DrawCardAction(AbstractDungeon.player, 1));
       AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, DevotionPower.POWER_ID, 3));
     }
     this.isDone = true;
   }
 }