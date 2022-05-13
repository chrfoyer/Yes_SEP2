package viewmodel;

import Model.Game;
import Model.User;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import mediator.RemoteModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @version 0.1
 */
public class UserProfileViewModel
{

  private RemoteModel model;
  private StringProperty usernameProperty;
  final ObservableList<SimpleGameViewModel> data = FXCollections.observableArrayList(
      new SimpleGameViewModel(new Game("TestName", "TestProducer", "PC","E")),
      new SimpleGameViewModel(new Game("TestName2", "TestProducer2", "PC","E"))
  );
  private StringProperty errorLabel;

  /**
   * ViewModel that connects Login to the model
   *
   * @param model RemoteModel because of RMI
   */
  public UserProfileViewModel(RemoteModel model)
  {
    this.model = model;
    usernameProperty = new SimpleStringProperty();
    errorLabel = new SimpleStringProperty();
  }

  /**
   * Getter for property
   *
   * @return usernameProperty
   */
  public StringProperty getUsernameProperty()
  {
    return usernameProperty;
  }

  /**
   * Getter for property
   *
   * @return errorProperty
   */
  public StringProperty getErrorLabel()
  {
    return errorLabel;
  }

  /**
   * Call this to reset the text inside the fields
   */
  public void reset()
  {
    usernameProperty.set("Currently logged in: "+LoginViewModel.currentlyLoggedInUser.getUsername());
    errorLabel.set("");
  }

  /**
   * Logic for returning the selected game
   *
   * @param game game to be returned
   */
  public void returnGame(SimpleGameViewModel game)
  {
    if (game != null)
    {
      data.remove(game);
    }else
    {
      errorLabel.set("Game must be selected first");
    }
  }

  /**
   * Logic for extending the selected game for 5 days
   *
   * @param game game to be extended
   */
  public void extendGame(SimpleGameViewModel game)
  {
    if (game != null)
    {
      game.getTimeProperty().set(game.getTimeProperty().get()+5);
    }else
    {
      errorLabel.set("Game must be selected first");
    }

  }

  /**
   * getter for an ObservableList displayed in the table
   *
   * @return ObservableList<SimpleGameViewModel>
   */
  public ObservableList<SimpleGameViewModel> getData()
  {
    return data;
  }
}
