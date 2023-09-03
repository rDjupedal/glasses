package glasstime;

import java.util.Scanner;

/**
 * I en pyramid av glas, där topp-glaset fylls på med en konstant hastighet, beräknar programmet efter vilken
 * tid glaset på en given position i pyramiden börjar rinna över.
 *
 * @author Rasmus Djupedal
 */
public class GlassTime {
    private static final Double SPEED = 0.1d;       // Hastighet (glas per sekund)

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int row = 0;
        int col = 0;

        // Validera in-data från användaren
        boolean inputOk = false;
        while (!inputOk) {
            System.out.print("Ange rad (2-50): ");
            row = scanner.nextInt();
            if (row < 2 || row > 50) System.out.println("Ogiltigt val!");
            else inputOk = true;
        }

        inputOk = false;
        while (!inputOk) {
            System.out.print("Ange glas nummer från vänster (1-" + row + "): ");
            col = scanner.nextInt();
            if (col < 1 || col > row) System.out.println("Ogiltigt val!");
            else inputOk = true;
        }

        // Skapa glas-pyramid
        Pyramid pyramid = new Pyramid(row);

        // Beräkna tiden det tar att fylla angivet glas.
        double time = pyramid.getGlass(row,col).getFilled() / SPEED;

        // Avrunda utskriften till 3 decimaler
        System.out.printf("Glaset börjar rinna över efter %.3f sekunder.\n",  time);

    }

}
