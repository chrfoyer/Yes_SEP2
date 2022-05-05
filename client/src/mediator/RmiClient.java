package mediator;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Used to connect to the RmiServer class to establish the Client - Server relationship
 */
public class RmiClient
{

  private RemoteModel server;

  /**
   * Constructor to create a new RmiClient and connect to the server
   *
   * @throws RemoteException if it encounters any problems connecting or talking to the server
   */
  public RmiClient() throws RemoteException
  {
    try
    {
      server = (RemoteModel) Naming.lookup("rmi://localhost:1099/Games");
      System.out.println("Stub pulled");
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  /**
   * Starting the Client class functionality
   */
  public void start() {
    Scanner input = new Scanner(System.in);
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
          System.out.println("Enter the name of the game");
          String gameName = input.nextLine();
          try {
            server.rentGame(gameName);
            System.out.println("Rented " + gameName);
          } catch (RemoteException e1) {
            e1.printStackTrace();
          }
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
          System.out.println("Goodbye");
          break;
        default:
          System.out.println("UNKNOWN INPUT! TRY AGAIN");
          break;
      }
    }
  }
}
