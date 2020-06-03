package Paladin;

import Paladin.relics.Ashbringer;
import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Paladin.cards.*;
import Paladin.characters.Paladin;
import Paladin.events.IdentityCrisisEvent;
import Paladin.potions.PlaceholderPotion;
import Paladin.relics.BottledPlaceholderRelic;
import Paladin.util.TextureLoader;
import Paladin.variables.PaladinCustomVariable;
import Paladin.variables.PaladinSecondMagicNumber;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class PaladinMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(PaladinMod.class.getName());
    private static String modID;

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Paladin Mod";
    private static final String AUTHOR = "Pudgypoultry";
    private static final String DESCRIPTION = "The fallen paladin awakens at the foot of a tall, looming spire...";

    // =============== INPUT TEXTURE LOCATION =================

    // Colors (RGB)
    // Character Color
    public static final Color PALADIN_GRAY = CardHelper.getColor(255.0f, 230.0f, 5.0f);

    // Potion Colors in RGB
    // Do I want potions?
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown

    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_PALADIN_GRAY = "PaladinModResources/images/512/bg_attack_paladin_gray.png";
    private static final String SKILL_PALADIN_GRAY = "PaladinModResources/images/512/bg_skill_paladin_gray.png";
    private static final String POWER_PALADIN_GRAY = "PaladinModResources/images/512/bg_power_paladin_gray.png";

    private static final String ENERGY_ORB_PALADIN_GRAY = "PaladinModResources/images/512/card_paladin_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "PaladinModResources/images/512/card_small_orb.png";

    private static final String ATTACK_PALADIN_GRAY_PORTRAIT = "PaladinModResources/images/1024/bg_attack_paladin_gray.png";
    private static final String SKILL_PALADIN_GRAY_PORTRAIT = "PaladinModResources/images/1024/bg_skill_paladin_gray.png";
    private static final String POWER_PALADIN_GRAY_PORTRAIT = "PaladinModResources/images/1024/bg_power_paladin_gray.png";
    private static final String ENERGY_ORB_PALADIN_GRAY_PORTRAIT = "PaladinModResources/images/1024/card_paladin_gray_orb.png";

    // Character assets
    private static final String PALADIN_BUTTON = "PaladinModResources/images/charSelect/PaladinCharacterButton.png";
    private static final String PALADIN_PORTRAIT = "PaladinModResources/images/charSelect/PaladinCharacterPortraitBG.png";
    public static final String PALADIN_SHOULDER_1 = "PaladinModResources/images/char/PaladinCharacter/shoulder.png";
    public static final String PALADIN_SHOULDER_2 = "PaladinModResources/images/char/PaladinCharacter/shoulder2.png";
    public static final String PALADIN_CORPSE = "PaladinModResources/images/char/PaladinCharacter/corpse.png";

    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "PaladinModResources/images/Badge.png";

    // Atlas and JSON files for the Animations
    public static final String PALADIN_SKELETON_ATLAS = "PaladinModResources/images/char/PaladinCharacter/skeleton.atlas";
    public static final String PALADIN_SKELETON_JSON = "PaladinModResources/images/char/PaladinCharacter/skeleton.json";

    // =============== MAKE IMAGE PATHS =================

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    // =============== /MAKE IMAGE PATHS/ =================

    // =============== /INPUT TEXTURE LOCATION/ =================


    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================

    public PaladinMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);

        //THIS SETS THE ID OF THE MOD
        //THIS IS REALLY IMPORTANT
        setModID("PaladinMod");

        logger.info("Done subscribing");

        logger.info("Creating the color " + Paladin.Enums.COLOR_GRAY.toString());

        BaseMod.addColor(Paladin.Enums.COLOR_GRAY, PALADIN_GRAY, PALADIN_GRAY, PALADIN_GRAY,
                PALADIN_GRAY, PALADIN_GRAY, PALADIN_GRAY, PALADIN_GRAY,
                ATTACK_PALADIN_GRAY, SKILL_PALADIN_GRAY, POWER_PALADIN_GRAY, ENERGY_ORB_PALADIN_GRAY,
                ATTACK_PALADIN_GRAY_PORTRAIT, SKILL_PALADIN_GRAY_PORTRAIT, POWER_PALADIN_GRAY_PORTRAIT,
                ENERGY_ORB_PALADIN_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        logger.info("Done creating the color");
    }
    //Pretty sure this could have been one line but fuck it I'm new and this was the structure I was handed.
    public static void setModID(String ID) {

        modID = ID;
    }

    public static String getModID() {
        return modID;

    }
    public static void initialize() {
        logger.info("========================= Initializing Paladin Mod. Hi. =========================");
        PaladinMod paladinmod = new PaladinMod();
        logger.info("========================= /Paladin Mod Initialized. Hello World./ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================

    // =============== LOAD THE CHARACTER =================

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + Paladin.Enums.Paladin.toString());

        BaseMod.addCharacter(new Paladin("Paladin", Paladin.Enums.Paladin),
                PALADIN_BUTTON, PALADIN_PORTRAIT, Paladin.Enums.Paladin);

        receiveEditPotions();
        logger.info("Added " + Paladin.Enums.Paladin.toString());
    }

    // =============== /LOAD THE CHARACTER/ =================

    // =============== POST-INITIALIZE =================


    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addUIElement(new ModLabel("PaladinMod doesn't have any settings!", 400.0f, 700.0f,
                settingsPanel, (me) -> {
        }));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        // =============== EVENTS =================

        // This event will be exclusive to the City (act 2). If you want an event that's present at any
        // part of the game, simply don't include the dungeon ID
        // If you want to have a character-specific event, look at slimebound (CityRemoveEventPatch).
        // Essentially, you need to patch the game and say "if a player is not playing my character class, remove the event from the pool"
        BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }

    // =============== / POST-INITIALIZE/ =================

    // ================ ADD POTIONS ===================


    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, Paladin.Enums.Paladin);

        logger.info("Done editing potions");
    }

    // ================ /ADD POTIONS/ ===================

    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new Ashbringer(), Paladin.Enums.COLOR_GRAY);

        // This adds a relic to the Shared pool. Every character can find this relic.
        //BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);

        // Mark relics as seen (the others are all starters so they're marked as seen in the character file
        UnlockTracker.markRelicAsSeen(BottledPlaceholderRelic.ID);
        logger.info("Done adding relics!");
    }

    // ================ /ADD RELICS/ ===================


    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new PaladinCustomVariable());
        BaseMod.addDynamicVariable(new PaladinSecondMagicNumber());

        logger.info("Adding cards");
        // Add the cards
        // Don't comment out/delete these cards (yet). You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        BaseMod.addCard(new OrbSkill());
        BaseMod.addCard(new PaladinSecondMagicNumberSkill());
        BaseMod.addCard(new StrikePaladin());
        BaseMod.addCard(new PaladinAttackWithVariable());
        BaseMod.addCard(new DefendPaladin());
        BaseMod.addCard(new PaladinCommonPower());
        BaseMod.addCard(new PaladinUncommonSkill());
        BaseMod.addCard(new PaladinUncommonAttack());
        BaseMod.addCard(new PaladinUncommonPower());
        BaseMod.addCard(new PaladinRareAttack());
        BaseMod.addCard(new PaladinRareSkill());
        BaseMod.addCard(new PaladinRarePower());
        BaseMod.addCard(new RighteousDuty());
        BaseMod.addCard(new FaithInTheLight());
        BaseMod.addCard(new JusticeForAll());
        BaseMod.addCard(new WakeOfAshes());
        BaseMod.addCard(new HolySmite());
        BaseMod.addCard(new TemplarsVerdict());
        BaseMod.addCard(new Prayer());
        BaseMod.addCard(new RighteousAnger());
        BaseMod.addCard(new Rebuke());
        BaseMod.addCard(new Judgement());
        BaseMod.addCard(new LayOnHands());
        BaseMod.addCard(new SentinelStance());
        BaseMod.addCard(new BlindingLight());
        BaseMod.addCard(new BlessingOfWisdom());
        BaseMod.addCard(new BlessingOfKings());
        BaseMod.addCard(new FlashOfLight());
        BaseMod.addCard(new CrusadersJudgement());
        BaseMod.addCard(new BlessedBreastplate());
        BaseMod.addCard(new DivineStorm());
        BaseMod.addCard(new ContemplativeMedicine());

        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        // This is so that they are all "seen" in the library, for people who like to look at the card list
        // before playing your mod.
        UnlockTracker.unlockCard(OrbSkill.ID);
        UnlockTracker.unlockCard(PaladinSecondMagicNumberSkill.ID);
        UnlockTracker.unlockCard(StrikePaladin.ID);
        UnlockTracker.unlockCard(PaladinAttackWithVariable.ID);
        UnlockTracker.unlockCard(DefendPaladin.ID);
        UnlockTracker.unlockCard(PaladinCommonPower.ID);
        UnlockTracker.unlockCard(PaladinUncommonSkill.ID);
        UnlockTracker.unlockCard(PaladinUncommonAttack.ID);
        UnlockTracker.unlockCard(PaladinUncommonPower.ID);
        UnlockTracker.unlockCard(PaladinRareAttack.ID);
        UnlockTracker.unlockCard(PaladinRareSkill.ID);
        UnlockTracker.unlockCard(PaladinRarePower.ID);
        UnlockTracker.unlockCard(RighteousDuty.ID);
        UnlockTracker.unlockCard(FaithInTheLight.ID);
        UnlockTracker.unlockCard(JusticeForAll.ID);
        UnlockTracker.unlockCard(WakeOfAshes.ID);
        UnlockTracker.unlockCard(HolySmite.ID);
        UnlockTracker.unlockCard(TemplarsVerdict.ID);
        UnlockTracker.unlockCard(Prayer.ID);
        UnlockTracker.unlockCard(RighteousAnger.ID);
        UnlockTracker.unlockCard(Rebuke.ID);
        UnlockTracker.unlockCard(Judgement.ID);
        UnlockTracker.unlockCard(LayOnHands.ID);
        UnlockTracker.unlockCard(SentinelStance.ID);
        UnlockTracker.unlockCard(BlindingLight.ID);
        UnlockTracker.unlockCard(BlessingOfWisdom.ID);
        UnlockTracker.unlockCard(BlessingOfKings.ID);
        UnlockTracker.unlockCard(FlashOfLight.ID);
        UnlockTracker.unlockCard(CrusadersJudgement.ID);
        UnlockTracker.unlockCard(BlessedBreastplate.ID);
        UnlockTracker.unlockCard(DivineStorm.ID);
        UnlockTracker.unlockCard(ContemplativeMedicine.ID);

        logger.info("Done adding cards!");
    }

    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such as Hubris.

    // ================ /ADD CARDS/ ===================


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings");

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/PaladinMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/PaladinMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/PaladinMod-Relic-Strings.json");

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/PaladinMod-Event-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/PaladinMod-Potion-Strings.json");

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/PaladinMod-Character-Strings.json");

        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/PaladinMod-Orb-Strings.json");

        logger.info("Done edittting strings");
    }

    // ================ /LOAD THE TEXT/ ===================


    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/PaladinMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }

    // ================ /LOAD THE KEYWORDS/ ===================

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}