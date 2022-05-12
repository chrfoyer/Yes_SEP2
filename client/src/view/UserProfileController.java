package view;

import Model.Game;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import viewmodel.LoginViewModel;
import viewmodel.SimpleGameViewModel;

import java.util.Optional;

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

  /**
   * Brings user back to log-in screen
   * Has a confirmation popup
   * @param actionEvent
   */
  public void logout(ActionEvent actionEvent)
  {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
        "Are you sure you want to log out?");
    Optional<ButtonType> option = alert.showAndWait();
    if (option.get() == ButtonType.OK)
    {
      getViewHandler().openView("LoginView.fxml");
      LoginViewModel.logout();
    }
    //or nothign happens
  }
}
