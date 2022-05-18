package viewmodel;

import Model.Game;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class SimpleGameViewModel
{
  private final StringProperty name;
  private final ObjectProperty<Integer> timeLeft;
  private final StringProperty console;
  private final StringProperty producer;
  private final StringProperty esrb;
  private final ObjectProperty<Boolean> rented;
  private final ObjectProperty<LocalDate> dateAdded;
  private final Game game;
  private final ObjectProperty<Float> review;

  public SimpleGameViewModel(Game game)
  {
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

  public StringProperty getNameProperty()
  {
    return name;
  }

  public ObjectProperty<Integer> getTimeProperty()
  {
    return timeLeft;
  }

  public ObjectProperty<Boolean> getRentedProperty()
  {
    return rented;
  }

  public StringProperty getRentedStringProperty()
  {
    if (rented.get())
    {
      return new SimpleStringProperty("Yes");
    } else
    {
      return new SimpleStringProperty("No");
    }
  }

  public StringProperty getEsrbProperty()
  {
    return esrb;
  }

  public StringProperty getProducer()
  {
    return producer;
  }

  public StringProperty getConsole()
  {
    return console;
  }

  public Game getGame()
  {
    return game;
  }

  public ObjectProperty<LocalDate> getDateAdded()
  {
    return dateAdded;
  }

  public void rent()
  {
    game.rentGame();
  }

  public ObjectProperty<Float> getReview()
  {
    return review;
  }
}
