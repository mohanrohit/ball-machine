// Problem1.java
import java.util.Scanner;

class Coordinates
{
    public double x;
    public double y;
}

public class Problem1
{
    private Scanner _scanner = new Scanner(System.in);

    private Coordinates readCoordinates()
    {
        Coordinates coordinates = new Coordinates();
        coordinates.x = _scanner.nextDouble();
        coordinates.y = _scanner.nextDouble();

        return coordinates;
    }

    private double getHypotenuse(double x, double y)
    {
        return Math.sqrt(x*x + y*y);
    }

    public void run()
    {
        while (true)
        {
            Coordinates coordinates = readCoordinates();

            double x = coordinates.x;
            double y = coordinates.y;

            if (x == 0 || y == 0)
            {
                break;
            }

            double l = Math.round((getHypotenuse(x, y) * 100.0) / 100.0);

            System.out.println("x=" + x + " y=" + y + " l=" + l);
        }
     }

    public static void main(String[] args)
    {
        Problem1 problem1 = new Problem1();
        problem1.run();
    }
}
