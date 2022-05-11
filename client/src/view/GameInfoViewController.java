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
    // TODO: 11/05/2022 Get the info from the selected game
  }

  public void backButton(ActionEvent actionEvent)
  {
    getViewHandler().openView("BrowseView.fxml");
    // Note: This might not always be the case, depending upon where we come from to get to this window.
  }
}
