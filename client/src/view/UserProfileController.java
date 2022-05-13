package view;

import Model.Game;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import viewmodel.LoginViewModel;
import viewmodel.SimpleGameViewModel;
import viewmodel.UserProfileViewModel;

import java.util.Optional;

public class UserProfileController extends ViewController
{
  public Label username;
  public TableView<SimpleGameViewModel> table;
  public TableColumn<SimpleGameViewModel, String> nameColumn;
  public TableColumn<SimpleGameViewModel, Integer> timeColumn;
  public Label error;
  private UserProfileViewModel viewModel;
  final ObservableList<SimpleGameViewModel> data = FXCollections.observableArrayList(
      new SimpleGameViewModel(new Game("TestName", "TestProducer", "PC","E")),
      new SimpleGameViewModel(new Game("TestName2", "TestProducer2", "PC","E"))
  );

  /**
   * method initializinig all the variables and cells
   */
  @Override protected void init()
  {
    viewModel = getViewModelFactory().getUserProfileViewModel();
    username.textProperty().bind(
            viewModel.getUsernameProperty());

    nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
    timeColumn.setCellValueFactory(cellData -> cellData.getValue().getTimeProperty());

    table.setItems(data);
    reset();
  }

  public void reset() {
    viewModel.reset();
  }

  public void payment(ActionEvent actionEvent)
  {
    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Transactions are handled by an external thing");
    alert.showAndWait();
  }

  // TODO: 12/05/2022 Distinguish for game
  public void returnGame(ActionEvent actionEvent)
  {
  }

  public void extend(ActionEvent actionEvent)
  {
  }

  /**
   * method for opening browse view
   * @param actionEvent browse button clicked
   */
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
