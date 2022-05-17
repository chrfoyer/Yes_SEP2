package view;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import viewmodel.BalanceViewModel;

import java.awt.*;
import java.net.URL;
import java.util.Optional;

public class BalanceViewController extends ViewController
{
  public Label balanceAmount;
  public Label errorLabel;
  public Label subscriptionDisplay;
  private BalanceViewModel viewModel;

  @Override protected void init()
  {
    viewModel = getViewModelFactory().getBalanceViewModel();

    balanceAmount.textProperty().bind(viewModel.getBalanceAmount());
    errorLabel.textProperty().bind(viewModel.getErrorLabel());
    subscriptionDisplay.textProperty().bind(viewModel.getSubssriptionDisplay());
    viewModel.reset();
  }

  public void addFunds(ActionEvent actionEvent)
  {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
        "Transactions are handled by an external provider. Are you alright with that?");
    Optional<ButtonType> option = alert.showAndWait();
    if (option.get() == ButtonType.OK)
    {
      try
      {
        //Desktop.getDesktop().browse(
        //    new URL("https://www.youtube.com/watch?v=UX0sbhIy9MA").toURI());
        viewModel.addFunds();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }

  public void paySubscription(ActionEvent actionEvent)
  {
    viewModel.paySubscription();
  }
}
