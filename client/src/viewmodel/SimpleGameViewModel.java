package viewmodel;

import Model.Game;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SimpleGameViewModel {
  private StringProperty name;
  private ObjectProperty<Integer> timeLeft;
  private StringProperty console;
  private StringProperty producer;
  private StringProperty esrb;
  private ObjectProperty<Boolean> rented;
  private ObjectProperty<LocalDate> dateAdded;
  private Game game;
  private ObjectProperty<Float> review;

  public SimpleGameViewModel(Game game) {
    this.name = new SimpleStringProperty(game.getName());
    this.timeLeft = new SimpleObjectProperty<>(game.getDaysLeft());
    this.producer = new SimpleStringProperty(game.getProducer());
    this.esrb = new SimpleStringProperty(game.getEsrb());
    this.rented = new SimpleObjectProperty<>(game.isRented());
    this.console = new SimpleStringProperty(game.getConsole());
    this.dateAdded = new SimpleObjectProperty<>(game.getDateAdded());
    this.review = new SimpleObjectProperty(game.getReview());
    this.game = game;
  }

  public StringProperty getNameProperty() {
    return name;
  }

  public ObjectProperty<Integer> getTimeProperty() {
    return timeLeft;
  }

  public ObjectProperty<Boolean> getRentedProperty() {
    return rented;
  }

  public StringProperty getRentedStringProperty() {
    if (rented.get()) {
      return new SimpleStringProperty("Yes");
    } else {
      return new SimpleStringProperty("No");
    }
  }

  public StringProperty getEsrbProperty() {
    return esrb;
  }

  public StringProperty getProducer() {
    return producer;
  }

  public StringProperty getConsole() {
    return console;
  }

  public Game getGame() {
    return game;
  }

  public ObjectProperty<LocalDate> getDateAdded() {
    return dateAdded;
  }

  public void rent() {
    game.rentGame();
  }

  public ObjectProperty<Float> getReview() {
    return review;
  }
}
