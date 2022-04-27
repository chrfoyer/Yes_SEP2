import Model.Model;
import Model.ModelManager;
import Model.Game;

import java.util.Scanner;
import java.util.SimpleTimeZone;

public class main
{
  private static Scanner input = new Scanner(System.in);
  public static Model model = new ModelManager(); // for persistence

  public static void main(String[] args)
  {
    System.out.println("Welcome to the system");

    login();
    System.out.println("See you soon!");

  }

  public static void renterLoop(String username)
  {
    // TODO: 20/04/2022  
  }

  public static void adminLoop(String username)
  {
    System.out.println("Welcome " + username + "!");
    boolean isLoggedIn = true;
    while (isLoggedIn)
    {
      System.out.println("Choose action by inputting the appropriate number!");
      System.out.println("1) ADD GAME");
      System.out.println("2) VIEW GAMES");
      System.out.println("3) DECREMENT DAY");
      System.out.println("0) LOG OUT");
      int select = input.nextInt();
      input.nextLine();
      switch (select)
      {
        case 1:
        {
          System.out.println("GAME NAME:");
          Game game = new Game(input.nextLine());
          model.addGame(game);
          break;
        }
        case 2:
        {
          System.out.println("Game List:");
          for (Game game : model.getAllGames())
          {
            System.out.println(game.toString());
          }
          break;
        }
        case 3:
        {
          System.out.println("Manually advancing the day");
          model.decrementDay();
          break;
        }
        case 0:
          isLoggedIn = false;
          break;
      }
    }
  }

  public static void clientLoop(String username)
  {
    System.out.println("Welcome " + username + "!");
    boolean isLoggedIn = true;
    while (isLoggedIn)
    {
      System.out.println("Choose action by inputing the appropriate number!");
      System.out.println("1) RENT GAME");
      System.out.println("2) VIEW GAMES");
      System.out.println("3) LOG OUT");
      int select = input.nextInt();
      input.nextLine();
      switch (select)
      {
        case 1:
          System.out.println("SELECT GAME: ");
          Game game = new Game(input.nextLine());
          model.removeGame(game);
        case 2:
          System.out.println("Game List:");
          System.out.println(model.getAllGames());
          break;
        case 3:
          isLoggedIn = false;
          break;
        default:
          System.out.println("UNKNOWN");
          break;
      }
    }
  }

  public static void login()
  {

    System.out.println("Please Enter your username!");

    String userName = input.nextLine();

    System.out.println("Press [1] to login as an Admin");
    System.out.println("Press [2] to login as a Renter");

    int select = input.nextInt();
    input.nextLine();

    switch (select)
    {
      case 1:
        adminLoop(userName);
        break;
      case 2:
        clientLoop(userName);
        break;
      default:
        System.out.println("UNKNOWN");
        break;
    }
  }
}


