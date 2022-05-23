import mediator.RmiServer;

import java.util.Scanner;

/**
 * The class the initializes the server and allows the user to close the server using the scanner.
 *
 * @author Chris, Martin, Levente, Kruno
 * @version v0.3 23/5/22
 */
public class Server
{

    /**
     * The main method that allows the server to be started
     *
     * @param args The array of arguments within the main method
     */
    public static void main(String[] args)
    {
        boolean running = true;

        RmiServer server = null;
        try
        {
            server = new RmiServer();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        Scanner input = new Scanner(System.in);
        while (running)
        {
            System.out.println("Press [0] to close the server");
            int given = input.nextInt();
            if (given == 0)
            {
                running = false;
            } else
            {
                System.out.println("Input not recognized. Please try again");
            }
        }
        System.out.println("See you soon!");
        System.exit(0);
    }
}