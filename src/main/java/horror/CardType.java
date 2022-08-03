package main.java.horror;

public enum CardType {
    AMERICA (false),
    EUROPE (false),
    ASIA (false),
    GENERAL(false),
    GATE (false),
    //RESEARCH (false),
    EXPEDITION (true),
    //SPECIAL (false),
    MYTH (true),
    MYSTERY(true);

    final boolean special;

    CardType(boolean special) {
        this.special = special;
    }

    public boolean isSpecial() {
        return special;
    }
}
