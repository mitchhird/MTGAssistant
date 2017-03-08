package util;

import models.cardModels.Card;
import models.cardModels.Format;
import models.deckModels.Deck;

public class BaseTest {
  protected final int amountOfEachCard = 20;
  protected final String[] testCardSet = {"Island", "Swamp", "Mountain", "Forest", "Plains"};
  
  protected String[] MODERN_BAN_LIST = { "Ancient Den", "Birthing Pod", "Blazing Shoal", "Bloodbraid Elf", "Chrome Mox", "Cloudpost", "Dark Depths", "Deathrite Shaman", "Dig Through Time", 
    "Dread Return", "Glimpse of Nature", "Great Furnace", "Green Sun's Zenith", "Hypergenesis", 
    "Jace, the Mind Sculptor", "Mental Misstep", "Ponder", "Preordain", "Punishing Fire", "Rite of Flame", "Seat of the Synod", "Second Sunrise", 
    "Seething Song", "Sensei's Divining Top", "Skullclamp", "Splinter Twin", "Stoneforge Mystic", "Summer Bloom", "Treasure Cruise", "Tree of Tales", 
    "Umezawa's Jitte", "Vault of Whispers" };
  
  protected String[] COMMANDER_BAN_LIST = { "Ancestral Recall", "Balance", "Biorhythm", "Black Lotus", "Braids, Cabal Minion", "Chaos Orb", "Coalition Victory", "Channel", "Emrakul, the Aeons Torn",
      "Erayo, Soratami Ascendant", "Falling Star", "Fastbond", "Gifts Ungiven", "Griselbrand", "Karakas", "Library of Alexandria", "Limited Resources", "Mox Emerald",
      "Mox Jet", "Mox Pearl", "Mox Ruby", "Mox Sapphire", "Painter's Servant", "Panoptic Mirror", "Primeval Titan", "Prophet of Kruphix", "Protean Hulk",
      "Recurring Nightmare", "Rofellos, Llanowar Emissary", "Shahrazad", "Sundering Titan", "Sway of the Stars", "Sylvan Primordial", "Time Vault", "Time Walk",
      "Tinker", "Tolarian Academy", "Trade Secrets", "Upheaval", "Worldfire", "Yawgmoth's Bargain" };
  
  protected String[] VINTAGE_BAN_LIST = { "Falling Star", "Chaos Orb", "Amulet of Quoz", "Bronze Tablet", "Contract from Below", "Darkpact", "Demonic Attorney", "Jeweled Bird",
      "Rebirth", "Tempest Efreet", "Timmerian Fiends" };

  protected String[] VINTAGE_RESTRICTED_LIST = { "Ancestral Recall", "Balance", "Black Lotus", "Brainstorm", "Chalice of the Void", "Channel", "Demonic Consultation", "Demonic Tutor", 
    "Dig Through Time", "Fastbond", "Flash", "Imperial Seal", "Library of Alexandria", "Lion's Eye Diamond", "Lodestone Golem", "Lotus Petal", "Mana Crypt", 
    "Mana Vault", "Memory Jar", "Merchant Scroll", "Mind's Desire", "Mox Emerald", "Mox Jet", "Mox Pearl", "Mox Ruby", "Mox Sapphire", "Mystical Tutor", 
    "Necropotence", "Ponder", "Sol Ring", "Strip Mine", "Time Vault", "Time Walk", "Timetwister", "Tinker", "Tolarian Academy", "Treasure Cruise", "Trinisphere", 
    "Vampiric Tutor", "Wheel of Fortune", "Windfall", "Yawgmoth's Bargain", "Yawgmoth's Will" };
 
  protected String[] getBanListForFormat (Format format) {
     switch (format) {
      case COMMANDER: return COMMANDER_BAN_LIST;
      case MODERN: return MODERN_BAN_LIST;
      case VINTAGE: return VINTAGE_BAN_LIST;
      default: return new String[0];
    }
  }

  // Creates And Returns A Deck That Uses The Supplied suffix
  protected Deck createTestDeck(int suffix) {
    Deck testDeck = new Deck();
    testDeck.setCreatingUser("testUser" + suffix);
    testDeck.setDeckDescription("testDescription" + suffix);
    testDeck.setDeckName("testDeckName" + suffix);

    Format[] availableFormats = Format.values();
    testDeck.setDeckFormat(availableFormats[suffix % availableFormats.length]);
    testDeck.setDeckArchetype("testArt" + suffix);
    
    for (String s: testCardSet) {
      Card cardToTest = new Card(s);
      testDeck.addCardToDeck(cardToTest, amountOfEachCard);
    }
   
    return testDeck;
  }

}
