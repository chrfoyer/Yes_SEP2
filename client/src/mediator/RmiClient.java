package mediator;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Used to connect to the RmiServer class to establish the Client - Server relationship
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.2 5/5/22
 */
public class RmiClient {

  private RemoteModel server;
  private String username;
  private static Scanner input = new Scanner(System.in);

  /**
   * Constructor to create a new RmiClient and connect to the server
   *
   * @throws RemoteException if it encounters any problems connecting or talking to the server
   */
  public RmiClient() throws RemoteException {
    try {
      server = (RemoteModel) Naming.lookup("rmi://localhost:1099/Games");
      System.out.println("Stub pulled");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Starting the Client class functionality. Presents a list of options for the user using console commans. Once logged
   * in, the user can choose to rent a game, view the games, or log out.
   */
  public void start() {
    boolean isLoggedIn = true;
    while (isLoggedIn) {
      System.out.println("Choose action by inputting the appropriate number!");
      System.out.println("1) RENT GAME");
      System.out.println("2) VIEW GAMES");
      System.out.println("0) LOG OUT");
      int select = input.nextInt();
      input.nextLine();
      switch (select) {
        case 1:
          rentGame();
          break;
        case 2:
          System.out.println("Game List:");
          try {
            System.out.println(server.viewGames());
          } catch (RemoteException e) {
            e.printStackTrace();
          }
          break;
        case 0:
          isLoggedIn = false;
          System.out.println("Goodbye " + username);
          break;
        default:
          System.out.println("UNKNOWN INPUT! TRY AGAIN");
          break;
      }
    }
  }

  /**
   * Method to rent a given name
   *
   * @throws RemoteException          if server connection doesn't exist
   * @throws IllegalArgumentException if the game doesn't exist on the server
   */
  public void rentGame() {
    System.out.println("Enter the name of the game");
    String gameName = input.nextLine();
    try {
      if (!server.containsGame(gameName))
        throw new IllegalArgumentException("The given game does not exist on the server");
      server.rentGame(gameName);
      System.out.println("Rented " + gameName);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Allows the user to log in using the scanner object to gather input from the keyboard. Currently, only a username is
   * needed.
   */
  public void login() {
    System.out.println("What is your username?");
    this.username = input.nextLine();
    this.start();
  }

}
