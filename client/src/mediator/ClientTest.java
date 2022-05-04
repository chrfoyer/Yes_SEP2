package mediator;

import java.rmi.RemoteException;
import java.util.Scanner;

import Model.Game;

/**
* Kill me please
 */
public class ClientTest {
    public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    RmiClient client = null;
    try {
        client = new RmiClient();
    } catch (RemoteException e2) {
        // TODO Auto-generated catch block
        e2.printStackTrace();
    }

    boolean isLoggedIn = true;
    while (isLoggedIn)
    {
      System.out.println("Choose action by inputting the appropriate number!");
      System.out.println("1) RENT GAME");
      System.out.println("2) VIEW GAMES");
      System.out.println("0) LOG OUT");
      int select = input.nextInt();
      input.nextLine();
      switch (select)
      {
        case 1:
          System.out.println("Renting the first game in the list because fuck you");
            try {
                Game gameToRent = client.viewGames().getGames().get(0);
                client.rentGame(gameToRent);
                System.out.println("Rented " + gameToRent.getName());
                // This method is terrible
            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
          break;
        case 2:
          System.out.println("Game List:");
			try {
				System.out.println(client.viewGames());
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
