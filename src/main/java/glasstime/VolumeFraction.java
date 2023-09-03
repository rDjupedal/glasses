package glasstime;

/**
 * Lagrar en volym och en proportion;
 * Efter total fylld volym X i toppglaset rinner aktuellt glas över med X * Y per tidsenhet
 */
public class VolumeFraction {
    private final double volume;
    private final double fraction;

    public VolumeFraction(double volume, double fraction) {
        this.volume = volume;
        this.fraction = fraction;
    }

    public double getVolume() {
        return volume;
    }

    public double getFraction() {
        return fraction;
    }

    @Override
    public String toString() {
        return "Volym:\t" + volume + "\tproportion\t" + fraction;
    }

}
