package main.java.horror.ancient;

public class Azathoth extends AncientOne {

    public Azathoth() {
        super(
                1, 2, 1,
                2, 3, 1,
                2, 4, 0
        );
    }

    @Override
    public void win() {

    }

    @Override
    public boolean inFirstPhase() {
        return false;
    }

    @Override
    public void lose() {

    }
}
