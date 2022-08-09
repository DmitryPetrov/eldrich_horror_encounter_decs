package main.java.horror.ancient;

import main.java.dealer.SourceLoader;

public class OuterSpace {

    public static AncientOne callTheAncientOne(AncientName name, SourceLoader dealer) {
        if (name.equals(AncientName.AZATHOTH)) {
            return new Azathoth(dealer);
        } else if (name.equals(AncientName.YOG_SOTHOTH)) {
            return new YogSothoth(dealer);
        } else if (name.equals(AncientName.CTHULHU)) {
            return new Cthulhu(dealer);
        } else if (name.equals(AncientName.SHUB_NIGGURATH)) {
            return new ShubNiggurath(dealer);
        } else if (name.equals(AncientName.YIG)) {
            return new Yig(dealer);
        }
        throw new RuntimeException("The Ancient One " + name + " too busy now");
    }
}
