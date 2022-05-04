import Model.Game;
import mediator.RmiServer;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ServerTest {
    public static void main(String[] args) {
        boolean running = true;

        RmiServer server = null;
        try {
            server = new RmiServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Scanner input = new Scanner(System.in);
        while (running) {
            System.out.println("Please choose a command");
            System.out.println("Press [1] to add a new game");
            System.out.println("Press [2] to remove a game");
            System.out.println("Press [0] to fuck off");

            int given = input.nextInt();
            input.nextLine();
            switch (given) {
                case 1:
                    System.out.println("yeet");
                    server.addGame(new Game("Cod"));
                    break;

                case 2:
                    System.out.println("Yoot");
                    break;

                default:
                    System.out.println("See you soon!");
                    break;

            }


        }
    }
}