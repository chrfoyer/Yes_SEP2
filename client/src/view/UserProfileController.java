package view;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import viewmodel.SimpleGameViewModel;

public class UserProfileController extends ViewController
{
  public Label username;
  public TableView<SimpleGameViewModel> table;
  public TableColumn<SimpleGameViewModel,String> nameColumn;
  public TableColumn<SimpleGameViewModel, Integer> timeColumn;
  public Label error;

  @Override protected void init()
  {

  }

  public void payment(ActionEvent actionEvent)
  {
  }

  public void returnGame(ActionEvent actionEvent)
  {
  }

  public void extend(ActionEvent actionEvent)
  {
  }

  public void browse(ActionEvent actionEvent)
  {
  }
}
