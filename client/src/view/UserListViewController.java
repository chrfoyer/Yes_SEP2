package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import viewmodel.SimpleUserViewModel;
import viewmodel.UserListViewModel;

import java.time.LocalDate;

public class UserListViewController extends ViewController {
  @FXML
  public TableColumn<SimpleUserViewModel, String> usernameColumn;
  @FXML
  public TableColumn<SimpleUserViewModel, LocalDate> birthdayColumn;
  @FXML
  public TableColumn<SimpleUserViewModel, String> emailColumn;
  @FXML
  public TableView<SimpleUserViewModel> table;
  @FXML
  public Label error;
  private UserListViewModel viewModel;

  @Override
  protected void init() {
    viewModel = getViewModelFactory().getUserListViewModel();
    usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
    birthdayColumn.setCellValueFactory(cellData -> cellData.getValue().bdayProperty());
    emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
    table.setItems(viewModel.getUsers());
    error.textProperty().bind(viewModel.errorProperty());
    table.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> viewModel.setSelectedUser(newV));
  }

  public void reset() {
    viewModel.reset();
  }

  @FXML
  public void remove(ActionEvent actionEvent) {
    // Remove the user
  }

  /**
   * method for opening UserEditView
   *
   * @param actionEvent edit button pressed
   */
  @FXML
  public void edit(ActionEvent actionEvent) {
    // Must use the selected user
    getViewHandler().openView("UserEditView.fxml");
  }

  public void back(ActionEvent actionEvent) {
    getViewHandler().openView("AdminView.fxml");
  }
}
