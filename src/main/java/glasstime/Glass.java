package glasstime;

import java.util.ArrayList;

/**
 * Representerar ett glas
 */
public class Glass {
    private final int row, col;
    private final Glass[] parents;
    private final ArrayList<VolumeFraction> flowTable = new ArrayList<>();
    private VolumeFraction filled;

    /**
     * Skapar ett glas-objekt
     * @param row position: rad
     * @param col position: från vänster
     * @param parents ovanstående glas som rinner över till detta glas
     */
    public Glass(int row, int col, Glass[] parents) {
        this.row = row;
        this.col = col;
        this.parents = parents;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    @Override
    public String toString() {
        return ("Rad " + row + ", på nummer " + col + " från vänster");
    }
    
    /**
     * Beräkna efter vilken totalvolym som detta glas är fullt
     * @return Total volym
     */
    public double getFilled() {
    	if (flowTable.isEmpty()) createFlowTable();
    	return filled.getVolume();
    }

    /**
     * Kontrollerar om glaset är fullt
     * @return sant eller falskt
     */
    private boolean isFilled() {
        return (filled != null);
    }

    /**
     * Returnera lista över vid vilka totalvolymer som vätska rinner över från detta
     * glas samt med vilken proportion i förhållande till toppglaset.
     * @return en lista.
     */
    public ArrayList<VolumeFraction> getFlowTable() {
        if (flowTable.isEmpty()) createFlowTable();
        return flowTable;
    }

    /**
     * Skapa en lista innehållande X, Y; Vid tillförd volym X i topp-glaset rinner volymen X * Y över från aktuellt glas.
     */
    private void createFlowTable() {

        // TOPP-GLAS
        if (parents[0] == null && parents[1] == null) {
            filled = new VolumeFraction(1d, 1d);
            flowTable.add(filled);
            return;
        }

        // FYLLS FRÅN TVÅ GLAS
        if (parents[0] != null && parents[1] != null) {

            // Hämta fyll-händelser från båda överstående glas
        	ArrayList<VolumeFraction> left = parents[0].getFlowTable();
        	ArrayList<VolumeFraction> right = parents[1].getFlowTable();

            double thisVolume = 0d;             // Nuvarande volym i glaset
            double lastVolume = 0d;             // Föregående volym i glaset
            double totalVolume = 0d;            // Total volym som tillförts i toppglaset
            double elapsedTotalVolume = 0d;     // Tillförd volym i toppglaset sedan förra händelsen
            double lProp = 0d;                  // Proportion från vänster överstående glas
            double rProp = 0d;                  // Proportion från höger ovanstående glas
            double prop = 0d;                   // Proportionen hur mycket detta glas fylls i förhållande till toppglaset


            int leftIndex = 0;
            int rightIndex = 0;

            // Gå igenom samtliga händelser från båda föräldrar
            while (leftIndex < left.size() || rightIndex < right.size()) {

                VolumeFraction leftCurrent;
                VolumeFraction rightCurrent;

                // Slut på händelser från vänster förälder
                if (leftIndex >= left.size()) {
                    rightCurrent = right.get(rightIndex);
                    rightIndex++;
                    rProp = rightCurrent.getFraction() / 2d;
                    totalVolume = rightCurrent.getVolume();

                // Slut på händelser från höger förälder
                } else if (rightIndex >= right.size()) {
                    leftCurrent = left.get(leftIndex);
                    leftIndex++;
                    lProp = leftCurrent.getFraction() / 2d;
                    totalVolume = leftCurrent.getVolume();

                // Välj näst-kommande händelse
                } else {
                    leftCurrent = left.get(leftIndex);
                    rightCurrent = right.get(rightIndex);

                    if (leftCurrent.getVolume() <= rightCurrent.getVolume()) {
                        lProp = leftCurrent.getFraction() / 2d;
                        totalVolume = leftCurrent.getVolume();
                        leftIndex++;
                    } else {
                        rProp = rightCurrent.getFraction() / 2d;
                        totalVolume = rightCurrent.getVolume();
                        rightIndex++;
                    }

                    // Vid första händelsen, för att elapsedTotalVolume ska bli noll
                    if (leftIndex + rightIndex == 1) lastVolume = totalVolume;
                }

                // Hur mycket vätska har tillförts i toppglaset sedan den förra händelsen
                elapsedTotalVolume = totalVolume - lastVolume;

                // Om glaset är / blir fyllt under pågående händelse så notera vid vilken total volym / proportion detta sker
                if (!isFilled() && thisVolume + (elapsedTotalVolume * prop) >= 1) {
                    double moreNeeded = (1d - thisVolume) / prop;
                    thisVolume = 1d;
                    filled = new VolumeFraction(lastVolume + moreNeeded, prop);
                    flowTable.add(filled);
                    //System.out.println(this + " fylldes vid totalvolymen: " + (lastVolume + moreNeeded) + " och har proportionen " + prop);

                } else {
                    thisVolume += elapsedTotalVolume * prop;
                }

                // Proportionen detta glas fylls i relation till topp-glaset är summan av de två ovanstående glasen
                prop = lProp + rProp;

                // Spara aktuell volym för nästa iteration
                lastVolume = totalVolume;

            }

            if (!isFilled()) {
                // Inga händelser kvar och glaset fortfarande inte fyllt. Beräkna totalvolym som krävs för
                // att glaset ska fyllas med nuvarande proportion.

                double moreNeeded = (1d - thisVolume) / prop;
                filled = new VolumeFraction(lastVolume + moreNeeded, prop);
                flowTable.add(filled);
                //System.out.println(this + " fylldes vid totalvolymen: " + (lastVolume + moreNeeded) + " och har proportionen " + prop);
            } else {
                flowTable.add(new VolumeFraction(lastVolume, prop));
            }

            return;
        }

        // FYLLS FRÅN ETT GLAS
        {
            Glass parent = (parents[0] != null) ? parents[0] : parents[1];

            // Total volym fylld i toppglas innan detta glas börjar fyllas?
            double totalVolume = parent.getFlowTable().get(0).getVolume();

            // Beräkna proportion som detta glas fylls med i förhållande till toppglaset
            double prop = Math.pow(0.5, row - 1);

            // Hur mycket mer behöver fyllas i toppglaset innan detta glas är fyllt?
            double moreNeeded = 1d / prop;

            // Lägg till i listan
            filled = new VolumeFraction(totalVolume + moreNeeded, prop);
            flowTable.add(filled);
        }
    }

}
