package viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mediator.RemoteModel;

/**
 * Class with the functionality for the GameInfoViewController
 */
public class GameInfoViewModel {
  private RemoteModel model;
  private ObjectProperty<SimpleGameViewModel> selectedGameProperty;
  private StringProperty name;
  private ObjectProperty<Integer> timeLeft;
  private StringProperty console;
  private StringProperty producer;
  private StringProperty esrb;
  private ObjectProperty<Boolean> rented;
  private StringProperty error;
  private StringProperty review;


  public GameInfoViewModel(RemoteModel model) {
    this.model = model;
    this.name = new SimpleStringProperty();
    this.timeLeft = new SimpleObjectProperty<>();
    this.producer = new SimpleStringProperty();
    this.esrb = new SimpleStringProperty();
    this.rented = new SimpleObjectProperty<>();
    this.console = new SimpleStringProperty();
    this.error = new SimpleStringProperty();
    this.review = new SimpleStringProperty();
    selectedGameProperty = new SimpleObjectProperty<>();
  }

  public void setSelectedGameProperty(SimpleGameViewModel selectedGameViewModel) {
    this.selectedGameProperty.set(selectedGameViewModel);
  }

  public void reset() {
    if (selectedGameProperty.get() != null) {
      SimpleGameViewModel selected = selectedGameProperty.get();
      name.set(selected.getNameProperty().get());
      timeLeft.set(selected.getTimeProperty().get());
      producer.set(selected.getProducer().get());
      esrb.set(selected.getEsrbProperty().get());
      rented.set(selected.getRentedProperty().get());
      console.set(selected.getConsole().get());
      review.set(selected.getReview().get() + "");
      error.set("");
    } else {
      name.set("");
      timeLeft.set(null);
      producer.set("");
      esrb.set("");
      rented.set(null);
      console.set("");
      review.set("");
      error.set("");
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

  public Integer getTimeLeft() {
    return timeLeft.get();
  }

  public ObjectProperty<Integer> timeLeftProperty() {
    return timeLeft;
  }

  public String getConsole() {
    return console.get();
  }

  public StringProperty consoleProperty() {
    return console;
  }

  public String getProducer() {
    return producer.get();
  }

  public StringProperty producerProperty() {
    return producer;
  }

  public String getEsrb() {
    return esrb.get();
  }

  public StringProperty esrbProperty() {
    return esrb;
  }

  public StringProperty reviewProperty() {
    return review;
  }


  public Boolean getRented() {
    return rented.get();
  }

  public ObjectProperty<Boolean> rentedProperty() {
    return rented;
  }

  public String getError() {
    return error.get();
  }

  public StringProperty errorProperty() {
    return error;
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public void setTimeLeft(Integer timeLeft) {
    this.timeLeft.set(timeLeft);
  }

  public void setConsole(String console) {
    this.console.set(console);
  }

  public void setProducer(String producer) {
    this.producer.set(producer);
  }

  public void setEsrb(String esrb) {
    this.esrb.set(esrb);
  }

  public void setRented(Boolean rented) {
    this.rented.set(rented);
  }

  public void setError(String error) {
    this.error.set(error);
  }
}
