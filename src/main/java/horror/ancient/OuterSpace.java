package main.java.horror.ancient;

import main.java.dealer.SourceLoader;

public class OuterSpace {

    public static AncientOne callTheAncientOne(AncientName name, SourceLoader dealer) {
        if (name.equals(AncientName.AZATHOTH)) {
            return new Azathoth(dealer);
        }
        throw new RuntimeException("The Ancient One " + name + " too busy now");
    }
}
