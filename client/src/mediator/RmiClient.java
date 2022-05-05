package mediator;

import Model.Model;

import java.beans.PropertyChangeSupport;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import Model.*;

public class RmiClient
{

  private RemoteModel server;

  public RmiClient() throws RemoteException
  {
    try
    {
       server = (RemoteModel) Naming.lookup("rmi://localhost:1099/Games");
      // UnicastRemoteObject.exportObject( this, 0);   for callback
      System.out.println("Stub pulled");
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

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
            // todo decouple method
            // Game gameToRent = server.viewGames().getGame(gameName);
            server.rentGame(server.viewGames().getGame(gameName));
            System.out.println("Rented " + gameName);
            // This method is terrible
          } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
          break;
        case 2:
          System.out.println("Game List:");
          try {
            System.out.println(server.viewGames());
          } catch (RemoteException e) {
            // TODO Auto-generated catch block
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
