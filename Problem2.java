// Problem2.java
import java.util.Scanner;

class Coordinates
{
    public double x;
    public double y;
}

public class Problem2
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

    private double getAngle(double x, double y)
    {
        double l = getHypotenuse(x, y);

        return Math.toDegrees(Math.atan(y/x));
        // or Math.asin(x/l)
        // or Math.acos(y/l)
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

            double angle = getAngle(x, y);

            System.out.println("x=" + x + " y=" + y + " angle=" + getAngle(x, y));
        }
     }

    public static void main(String[] args)
    {
        Problem2 problem2 = new Problem2();
        problem2.run();
    }
}
