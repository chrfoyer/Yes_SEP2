package view;

import Model.Game;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import viewmodel.LoginViewModel;
import viewmodel.SimpleGameViewModel;

public class UserProfileController extends ViewController
{
  public Label username;
  public TableView<SimpleGameViewModel> table;
  public TableColumn<SimpleGameViewModel, String> nameColumn;
  public TableColumn<SimpleGameViewModel, Integer> timeColumn;
  public Label error;
  final ObservableList<SimpleGameViewModel> data = FXCollections.observableArrayList(
      new SimpleGameViewModel(new Game("TestName", "TestProducer", "PC","E")),
      new SimpleGameViewModel(new Game("TestName2", "TestProducer2", "PC","E"))
  );

  @Override protected void init()
  {
    username.textProperty().bind(
        getViewModelFactory().getUserProfileViewModel().getUsernameProperty());

    nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
    timeColumn.setCellValueFactory(cellData -> cellData.getValue().getTimeProperty());

    table.setItems(data);
    getViewModelFactory().getUserProfileViewModel().reset();
  }

  public void payment(ActionEvent actionEvent)
  {
  }

  // TODO: 12/05/2022 Distinguish for game 
  public void returnGame(ActionEvent actionEvent)
  {
  }

  public void extend(ActionEvent actionEvent)
  {
  }

  public void browse(ActionEvent actionEvent)
  {
    getViewHandler().openView("BrowseView.fxml");
  }

  public void logout(ActionEvent actionEvent)
  {
    getViewHandler().openView("LoginView.fxml");
    LoginViewModel.logout();
  }
}
