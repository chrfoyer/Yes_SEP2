package view;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import viewmodel.AddEditGameViewModel;
import viewmodel.LoginViewModel;

import java.util.Optional;

public class AddEditGameController extends ViewController
{
  public TextField gameName;
  public TextField producer;
  public ChoiceBox console;
  public ChoiceBox esrb;
  public Label error;
  public AddEditGameViewModel viewModel;

  @Override protected void init()
  {
    viewModel = getViewModelFactory().getAddEditGameViewModel();
    gameName.textProperty().bindBidirectional(viewModel.nameProperty());
    producer.textProperty().bindBidirectional(viewModel.producerProperty());
    //todo
    //esrb.textProperty().bindBidirectional(viewModel.esrbProperty());
    // Bind rating
    // console.textProperty().bindBidirectional(viewModel.consoleProperty());
    error.textProperty().bind(viewModel.errorProperty());
  }

  public void cancel(ActionEvent actionEvent)
  {
    getViewModelFactory().getInventoryViewModel().reset();
    getViewHandler().openView("InventoryView.fxml");
  }

  public void confirm(ActionEvent actionEvent)
  {
    if (LoginViewModel.currentlyLoggedInUser.isAdmin())
    {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
          "Are you sure you want to change the game info?");
      Optional<ButtonType> option = alert.showAndWait();
      if (option.get() == ButtonType.OK)
      {
        viewModel.confirm();
        //this is retarded
        //please don't use production
        if (error.textProperty().get().equals(""))
        {
          getViewModelFactory().getInventoryViewModel().reset();
          getViewHandler().openView("InventoryView.fxml");
        }
      }
    }
  }
}
