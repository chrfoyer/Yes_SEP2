package viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mediator.RemoteModel;

// TODO: 12/05/2022 Delegation from the controller 
public class BrowseViewModel {
  private RemoteModel model;
  private StringProperty errorLabel;
  private ObjectProperty<SimpleGameViewModel> selectedGameProperty;
  // Need the list of games in an observable list
  // Need a property for the selected simple game view model

  public BrowseViewModel(RemoteModel model) {
    this.model = model;
    errorLabel = new SimpleStringProperty();
    selectedGameProperty = new SimpleObjectProperty<>();
  }

  public void reset() {
    errorLabel.set("");
    //
  }
}
