package com.classic.project.model.character;

public enum ClassOfCharacter {

    ABYSS_WALKER("Abyss Walker"),
    ADVENTURER("Adventurer"),
    ARCANA_LORD("Arcana Lord"),
    ARCHMAGE("Archmage"),
    ARTISAN("Artisan"),
    ASSASSIN("Assassin"),
    BISHOP("Bishop"),
    BLADEDANCER("Bladedancer"),
    BOUNTY_HUNTER("Bounty Hunter"),
    CARDINAL("Cardinal"),
    CLERIC("Cleric"),
    DARK_AVENGER("Dark Avenger"),
    DARK_FIGHTER("Dark Fighter"),
    DARK_MYSTIC("Dark Mystic"),
    DARK_WIZARD("Dark Wizard"),
    DESTRO("Destroyer"),
    DOMI("Dominator"),
    DOOMCRYER("Doomcryer"),
    DREADNOUGHT("Dreadnought"),
    DUELIST("Duelist"),
    DWARVEN_FIGHTER("Dwarven Fighter"),
    ELEMENTAL_MASTER("Elemental Master"),
    ELEMENTAL_SUMMONER("Elemental Summoner"),
    ELVEN_ELDER("Elven Elder"),
    ELVEN_FIGHTER("Elven Fighter"),
    ELVEN_KNIGHT("Elven Knight"),
    ELVEN_MYSTIC("Elven Mystic"),
    ELVEN_ORACLE("Elven Oracle"),
    ELVEN_SCOUT("Elven Scout"),
    ELVEN_WIZARD("Elven Wizard"),
    EVA_SAINT("Eva'S Saint"),
    EVA_TEMPLAR("Eva's Templar"),
    FORTUNE_SEEKER("Fortune Seeker"),
    GHOST_HUNTER("Ghost Hunter"),
    GHOST_SENTINEL("Ghost Sentinel"),
    GK("Grand Khavatari"),
    GLADIATOR("Gladiator"),
    HAWKEYE("Hawkeye"),
    HELL_KNIGHT("Hell Knight"),
    HIEROPHANT("Hierophant"),
    HUMAN_FIGHTER("Human Fighter"),
    HUMAN_KNIGHT("Human Knight"),
    HUMAN_MYSTIC("Human Mystic"),
    HUMAN_WIZARD("Human Wizard"),
    MAESTRO("Maestro"),
    MONK("Monk"),
    MOONLIGHT_SENTINEL("Moonlight Sentinel"),
    MYSTIC_MUSE("Mystic Muse"),
    NECRO("Necromancer"),
    ORC_FIGHTER("Orc Fighter"),
    ORC_MYSTIC("Orc Mystic"),
    ORC_SHAMAN("Orc Shaman"),
    OVERLORD("Overlord"),
    PALADIN("Paladin"),
    PALUS_KNIGHT("Palus Knight"),
    PHANTOM_RANGER("Phantom Ranger"),
    PHANTOM_SUMMONER("Phantom Summoner"),
    PHOENIX_KNIGHT("Phoenix Knight"),
    PLAINS_WALKER("Plains Walker"),
    PROPHET("Prophet"),
    RAIDER("Raider"),
    ROGUE("Rogue"),
    SAGITTARIUS("Sagittarius"),
    SCAVENGER("Scavenger"),
    SHILLIEN_ELDER("Shillien Elder"),
    SHILLIEN_KNIGHT("Shillien Knight"),
    SHILLIEN_ORACLE("Shillien Oracle"),
    SHILLIEN_SAINT("Shillien Saint"),
    SHILLIEN_TEMPLAR("Shillien Templar"),
    SILVER_RANGER("Silver Ranger"),
    SORCERER("Sorcerer"),
    SOULTAKER("Soultaker"),
    SPECTRAL_DANCER("Spectral Dancer"),
    SPECTRAL_MASTER("Spectral Master"),
    SPELLHOWLER("Spellhowler"),
    SPELLSINGER("Spellsinger"),
    STORM_SCREAMER("Storm Screamer"),
    SWORD_MUSE("Sword Muse"),
    SWORD_SINGER("Sword Singer"),
    TEMPLE_KNIGHT("Temple Knight"),
    TITAN("Titan"),
    TREASURE_HUNTER("Treasure Hunter"),
    TYRANT("Tyrant"),
    WARCRYER("Warcryer"),
    WARLOCK("Warlock"),
    WARLORD("Warlord"),
    WARRIOR("Warrior"),
    WARSMITH("Warsmith"),
    WIND_RIDER("Wind Rider");


    private String className;

    ClassOfCharacter(String className) {
        this.className = className;
    }

    public String getName() {
        return this.className;
    }

    public void setName(String name) {
        this.className = name;
    }
}
