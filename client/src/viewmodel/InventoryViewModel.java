package viewmodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.RemoteModel;
import Model.Game;

import java.util.ArrayList;

// TODO: 12/05/2022 Delegation from the controller
public class InventoryViewModel {
  private RemoteModel model;
  private StringProperty errorLabel;
  private ObjectProperty<SimpleGameViewModel> selectedGameProperty;
  private ObservableList<SimpleGameViewModel> list;
  // Need the list of games in an observable list
  // Need a property for the selected simple game view model

  public InventoryViewModel(RemoteModel model) {
    this.model = model;
    errorLabel = new SimpleStringProperty();
    selectedGameProperty = new SimpleObjectProperty<>();
    list = FXCollections.observableArrayList();
  }

  public void reset() {
    fillTable();
  }

  public void fillTable() {
    try {
      list.clear();
      ArrayList<Game> games = model.viewGames().getGamesArrayCopy();
      for (Game game : games) {
        SimpleGameViewModel temp = new SimpleGameViewModel(game);
        if (!list.contains(temp))
          list.add(temp);
        System.out.println(temp);
      }
    } catch (Exception e) {
      errorLabel.set(e.getMessage());
    }
  }

  public ObservableList<SimpleGameViewModel> getList() {
    return list;
  }

  public ObjectProperty<SimpleGameViewModel> getSelectedGameProperty() {
    return selectedGameProperty;
  }

  public void removeGame() {
    try {
      model.removeGame(selectedGameProperty.get().getGame());
      reset();
    } catch (Exception e) {
      errorLabel.set(e.getMessage());
    }
  }

  public StringProperty getError() {
    return errorLabel;
  }

  public void setSelectedGameProperty(SimpleGameViewModel selectedGameProperty) {
    this.selectedGameProperty.set(selectedGameProperty);
  }
}
