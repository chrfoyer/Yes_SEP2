package viewmodel;

import Model.Game;
import Model.GameList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.RemoteModel;

import java.rmi.RemoteException;

// TODO: 12/05/2022 Delegation from the controller 
public class BrowseViewModel {
  private RemoteModel model;
  private StringProperty errorLabel;
  private ObjectProperty<SimpleGameViewModel> selectedGameProperty;
  final ObservableList<SimpleGameViewModel> data = FXCollections.observableArrayList();
  // Need the list of games in an observable list
  // Need a property for the selected simple game view model

  public BrowseViewModel(RemoteModel model) {
    this.model = model;
    errorLabel = new SimpleStringProperty();
    selectedGameProperty = new SimpleObjectProperty<>();
    GameList temp = null;
    try
    {
      temp = model.viewGames();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    for (Game game: temp.getGamesArrayCopy())
    {
      if (!game.isRented())
      {
        data.add(new SimpleGameViewModel(game));
      }
    }
  }

  public void reset() {
    data.clear();
    GameList temp = null;
    try
    {
      temp = model.viewGames();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    for (Game game: temp.getGamesArrayCopy())
    {
      if (!game.isRented())
      {
        data.add(new SimpleGameViewModel(game));
      }
    }
    errorLabel.set("");
  }

  public ObservableList<SimpleGameViewModel> getData()
  {
    return data;
  }

  public StringProperty getErrorLabel()
  {
    return errorLabel;
  }

  public void rentGame(SimpleGameViewModel game) throws RemoteException
  {
    if (game != null)
    {
      model.rentGame(game.getGame());

    }else
    {
      errorLabel.set("Game must be selected first!");
    }
  }

  public void setSelectedGameProperty(SimpleGameViewModel selectedGameProperty)
  {
    this.selectedGameProperty.set(selectedGameProperty);
  }
}
