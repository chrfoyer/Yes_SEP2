package view;

import Model.Game;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import mediator.CurrentlyLoggedUser;
import viewmodel.LoginViewModel;
import viewmodel.SimpleGameViewModel;
import viewmodel.UserProfileViewModel;

import java.awt.*;
import java.net.URL;
import java.util.Optional;

/**
 * Class which delegates to UserProfileViewModel
 */
public class UserProfileController extends ViewController
{
  public Label username;
  public TableView<SimpleGameViewModel> table;
  public TableColumn<SimpleGameViewModel, String> nameColumn;
  public TableColumn<SimpleGameViewModel, Integer> timeColumn;
  public Label error;
  UserProfileViewModel viewModel;

  /**
   * method initializing all the variables and cells
   */
  @Override protected void init()
  {
    viewModel = getViewModelFactory().getUserProfileViewModel();
    username.textProperty().bind(viewModel.getUsernameProperty());

    nameColumn.setCellValueFactory(
        cellData -> cellData.getValue().getNameProperty());
    timeColumn.setCellValueFactory(
        cellData -> cellData.getValue().getTimeProperty());
    error.textProperty().bind(viewModel.getErrorLabel());

    table.setItems(viewModel.getData());
    getViewModelFactory().getUserProfileViewModel().reset();

    reset();
  }

  /**
   * method that resets the fields in the view
   */
  public void reset()
  {
    viewModel.reset();
  }

  /**
   * Logic of the button that handles payments Has a confirmation popup
   *
   * @param actionEvent
   */
  public void payment(ActionEvent actionEvent)
  {
    getViewModelFactory().getBalanceViewModel().reset();
    getViewHandler().openView("BalanceView.fxml");
  }

  // TODO: 12/05/2022 Distinguish for game

  /**
   * Logic of the button to return a game Has a confirmation popup
   *
   * @param actionEvent
   */
  public void returnGame(ActionEvent actionEvent)
  {
    SimpleGameViewModel selected = table.getSelectionModel().getSelectedItem();


    Alert popup = new Alert(Alert.AlertType.INFORMATION,
        "You will have an option to leave a review,please follow the next prompt!");
    popup.showAndWait();

    ButtonType oneStar = new ButtonType("1", ButtonBar.ButtonData.APPLY);
    ButtonType twoStar = new ButtonType("2", ButtonBar.ButtonData.APPLY);
    ButtonType threeStar = new ButtonType("3", ButtonBar.ButtonData.APPLY);
    ButtonType fourStar = new ButtonType("4", ButtonBar.ButtonData.APPLY);
    ButtonType fiveStar = new ButtonType("5", ButtonBar.ButtonData.APPLY);

    int review = 0;
    Alert reviewAlert = new Alert(Alert.AlertType.NONE,
        "Leave your review by choosing the button!", oneStar, twoStar,
        threeStar, fourStar, fiveStar);
    Optional<ButtonType> reviewChoice = reviewAlert.showAndWait();
    switch (reviewChoice.get().getText())
    {
      case "1":
        review = 1;
        break;
      case "2":
        review = 2;
        break;

      case "3":
        review = 3;
        break;

      case "4":
        review = 4;
        break;

      case "5":
        review = 5;
        break;
    }
    viewModel.leaveReview(review,selected);

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
        "Are you sure you want to return " + selected.getNameProperty().get()
            + "?");
    Optional<ButtonType> option = alert.showAndWait();
    if (option.get() == ButtonType.OK)
    {
      viewModel.returnGame(selected);
    }
  }

  /**
   * Logic off the button that extends the rented time of the game Has a confirmation popup
   *
   * @param actionEvent
   */
  public void extend(ActionEvent actionEvent)
  {
    SimpleGameViewModel selected = table.getSelectionModel().getSelectedItem();
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
        "Are you sure you want to extend " + selected.getNameProperty().get()
            + "?");
    Optional<ButtonType> option = alert.showAndWait();
    if (option.get() == ButtonType.OK)
    {
      viewModel.extendGame(table.getSelectionModel().getSelectedItem());
    }
  }

  /**
   * Method for opening browse view Has a confirmation popup
   *
   * @param actionEvent browse button clicked
   */
  public void browse(ActionEvent actionEvent)
  {
    getViewModelFactory().getBrowseViewModel().reset();
    getViewHandler().openView("BrowseView.fxml");
  }

  /**
   * Brings user back to log-in screen Has a confirmation popup
   *
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
      CurrentlyLoggedUser.logout();
    }
    //or nothign happens
  }
}
