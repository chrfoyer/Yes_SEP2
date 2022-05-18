package viewmodel;

import Model.Game;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mediator.RemoteModel;

import java.util.ArrayList;

/**
 * @version 0.1
 */
public class AdminViewModel
{

  private RemoteModel model;
  private StringProperty registeredUsers;
  private StringProperty totalGames;
  private StringProperty rentedGames;
  private StringProperty recentGame;
  private StringProperty errorLabel;

  /**
   * ViewModel that connects Login to the model
   *
   * @param model RemoteModel because of RMI
   */
  public AdminViewModel(RemoteModel model)
  {
    this.model = model;
    registeredUsers = new SimpleStringProperty();
    totalGames = new SimpleStringProperty();
    rentedGames = new SimpleStringProperty();
    recentGame = new SimpleStringProperty();
    errorLabel = new SimpleStringProperty();
  }

  /**
   * Getter for property
   *
   * @return registeredUsers
   */
  public StringProperty getRegisteredUsers()
  {
    return registeredUsers;
  }

  public StringProperty getTotalGames()
  {
    return totalGames;
  }

  public StringProperty getRentedGames()
  {
    return rentedGames;
  }

  public StringProperty getRecentGame()
  {
    return recentGame;
  }

  public StringProperty getErrorLabel()
  {
    return errorLabel;
  }

  /**
   * Call this to reset the text inside the fields
   */
  public void reset()
  {
    updateLabels();
    errorLabel.set("");
  }

  public void updateLabels()
  {
    try
    {
      int registeredUser = 0;
      int totalGame = 0;
      int totalRent = 0;
      Game recent = model.viewGames().getGamesArrayCopy().get(0);
      registeredUser = model.getUserList().size();
      ArrayList<Game> allGames = model.viewGames().getGamesArrayCopy();
      for (Game game : allGames)
      {
        totalGame++;
        if (game.isRented())
          totalRent++;
      }
      registeredUsers.set(registeredUser + "");
      totalGames.set(totalGame + "");
      rentedGames.set(totalRent + "");
      recentGame.set(recent.getName());
    }
    catch (Exception e)
    {
      errorLabel.set(e.getMessage());
    }
  }

}
