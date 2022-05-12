package viewmodel;

import Model.Game;
import javafx.beans.property.*;

public class SimpleGameViewModel
{
  private StringProperty name;
  private ObjectProperty<Integer> timeLeft;
  private StringProperty console;
  private StringProperty producer;
  private StringProperty esrb;
  private ObjectProperty<Boolean> rented;

  public SimpleGameViewModel(Game game)
  {
    this.name = new SimpleStringProperty(game.getName());
    this.timeLeft = new SimpleObjectProperty<>(game.getDaysLeft());
    this.producer = new SimpleStringProperty(game.getProducer());
    this.esrb = new SimpleStringProperty(game.getEsrb());
    this.rented = new SimpleObjectProperty<>(game.isRented());
    this.console = new SimpleStringProperty(game.getConsole());
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
}
