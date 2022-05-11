package viewmodel;

import Model.Game;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimpleGameViewModel
{
  private StringProperty name;
  private IntegerProperty timeLeft; //todo HOW TO BIND INTEGERPROPRETY

  public SimpleGameViewModel(Game game)
  {
    this.name = new SimpleStringProperty(game.getName());
    this.timeLeft = new SimpleIntegerProperty(game.getDaysLeft());
  }

  public StringProperty getName()
  {
    return name;
  }

  public IntegerProperty getTimeLeft()
  {
    return timeLeft;
  }
}
