package glasstime;

import java.util.ArrayList;

/**
 * Skapar och organiserar en struktur av glas
 */
public class Pyramid {
    private final ArrayList<Glass> glasses = new ArrayList<>();

    public Pyramid(int rows) {

        // Skapa struktur och glas
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= row; col++) {
                Glass glass = new Glass(row, col, getParents(row, col));
                glasses.add(glass);
            }
        }

    }

    /**
     * Hämta glaset på en given position
     * @param row position: rad uppifrån
     * @param col position: glas nummer från vänster
     * @return glas-objektet på positionen
     */
    protected Glass getGlass(int row, int col) {

        for (Glass glass : glasses) {
            if (glass.getRow() == row && glass.getCol() == col) return glass;
        }
        return null;
    }

    /**
     * Hämta glasen som fyller ett glas på en given position.
     * @param row position: rad uppifrån
     * @param col position: glas nummer från vänster
     * @return vektor av de två glas som rinner över till glaset på given position
     */
    protected Glass[] getParents(int row, int col) {

        Glass[] parents = new Glass[2];
        parents[0] = null;
        parents[1] = null;

        for (Glass glass : glasses) {
            if (glass.getRow() == row - 1) {
                if (glass.getCol() == col - 1) parents[0] = glass;
                if (glass.getCol() == col) parents[1] = glass;
            }
        }

        return parents;
    }

}
