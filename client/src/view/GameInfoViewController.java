package view;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GameInfoViewController extends ViewController
{

  public TextField name;
  public TextField producer;
  public TextField esrb;
  public TextField rating;
  public Label error;

  @Override protected void init()
  {

  }

  public void backButton(ActionEvent actionEvent)
  {
  }
}
