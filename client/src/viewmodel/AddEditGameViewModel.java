package viewmodel;

import Model.Game;
import javafx.beans.property.*;
import mediator.RemoteModel;

public class AddEditGameViewModel {
  private RemoteModel model;
  private ObjectProperty<SimpleGameViewModel> selectedGameProperty;
  private StringProperty name;
  private ObjectProperty<Integer> timeLeft;
  private StringProperty console;
  private StringProperty producer;
  private StringProperty esrb;
  private ObjectProperty<Boolean> rented;
  private StringProperty error;

  public AddEditGameViewModel(RemoteModel model) {
    this.model = model;
    this.name = new SimpleStringProperty();
    this.timeLeft = new SimpleObjectProperty<>();
    this.producer = new SimpleStringProperty();
    this.esrb = new SimpleStringProperty();
    this.rented = new SimpleObjectProperty<>();
    this.console = new SimpleStringProperty();
    this.error = new SimpleStringProperty();
    selectedGameProperty = new SimpleObjectProperty<>();
  }

  public void setSelectedGameProperty(SimpleGameViewModel selectedGameViewModel) {
    this.selectedGameProperty.set(selectedGameViewModel);
  }

  public void reset() {
    try {
      if (selectedGameProperty.get() == null) {
        name.set("");
        producer.set("");
        esrb.set("E");
        console.set("PC");
      } else {
        SimpleGameViewModel selectedGameViewModel = selectedGameProperty.get();
        name.set(selectedGameViewModel.getNameProperty().get());
        timeLeft.set(selectedGameViewModel.getTimeProperty().get());
        producer.set(selectedGameViewModel.getProducer().get());
        esrb.set(selectedGameViewModel.getEsrbProperty().get());
        rented.set(selectedGameViewModel.getRentedProperty().get());
        console.set(selectedGameViewModel.getConsole().get());

        error.set("");
      }
    } catch (Exception e) {
      error.set(e.getMessage());
      e.printStackTrace();
    }
  }

  public SimpleGameViewModel getSelectedGameProperty() {
    return selectedGameProperty.get();
  }

  public ObjectProperty<SimpleGameViewModel> selectedGamePropertyProperty() {
    return selectedGameProperty;
  }

  public String getName() {
    return name.get();
  }

  public StringProperty nameProperty() {
    return name;
  }

  public StringProperty consoleProperty() {
    return console;
  }

  public StringProperty producerProperty() {
    return producer;
  }

  public StringProperty esrbProperty() {
    return esrb;
  }

  public ObjectProperty<Boolean> rentedProperty() {
    return rented;
  }

  public StringProperty errorProperty() {
    return error;
  }

  public void setEsrb(String esrb) {
    this.esrb.set(esrb);
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public void setConsole(String console) {
    this.console.set(console);
  }

  public void addGame() {
    //todo nullcheck
    try {
      Game gameToAdd = new Game(name.get(), producer.get(), console.get(),
              esrb.get());
      model.addGame(gameToAdd);
    } catch (Exception e) {
      error.set(e.getMessage());
    }
  }

  public void editGame() {
    //todo nullcheck
    try {
      Game gameOld = new Game(selectedGameProperty.get().getGame().getName(),
              selectedGameProperty.get().getGame().getProducer(),
              selectedGameProperty.get().getGame().getConsole(),
              selectedGameProperty.get().getGame().getEsrb());

      Game gameNew = selectedGameProperty.get().getGame();
      gameNew = selectedGameProperty.get().getGame();
      gameNew.setConsole(console.get());
      gameNew.setProducer(producer.get());
      gameNew.setEsrb(esrb.get());
      gameNew.setName(name.get());

      model.updateGameInfo(gameOld, gameNew);

      //change finished without error
    } catch (Exception e) {
      error.set(e.getMessage());
    }
  }
}
