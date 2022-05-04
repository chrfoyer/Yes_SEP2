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
            System.out.println("Press [3] to view the games");
            System.out.println("Press [4] to decrement the day");
            System.out.println("Press [0] to fuck off");

            int given = input.nextInt();
            input.nextLine();
            switch (given) {
                case 1:
                    System.out.println("Enter the name of the game!");
                    String name=input.nextLine();
                    
                    server.addGame(new Game(name));
                    break;

                case 2:
                    System.out.println("Please enter the name of the game to remove");
                    String removeName = input.nextLine();
                    Game gameToRemove = server.getGame(removeName);
                    System.out.println("Are you sure you want to remove this game?");
                    System.out.println(gameToRemove.toString());
                    System.out.println("Press [1] to remove");
                    System.out.println("Press [2] to cancel");
                    if (input.nextInt() == 1) {
                        System.out.println("Removing " + gameToRemove.getName());
                        server.removeGame(gameToRemove);
                    }
                    else {
                        System.out.println("Cancelling removal of " + gameToRemove.getName());
                    }
                    break;

                    case 3:
                    for (Game game : server.viewGames().getGames()
                    ) {
                        System.out.println("Game -> " + game.getName() + " : " + game.getDaysLeft() + " days left");
                        System.out.println("Rented: " + game.isRented());
                    }
                    break;
                   
                case 0:
                    running = false;
                    break;

            }
        }
        System.out.println("See you soon!");
    }
}