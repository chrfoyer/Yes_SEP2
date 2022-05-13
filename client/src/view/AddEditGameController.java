package view;

import Model.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

  /**
   * method initializing all the variables and cells
   */
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

    console.getItems().addAll("PC", "Xbox", "PlayStation");
    esrb.getItems().addAll("E", "E10+", "T", "M", "AO");
    console.setValue(viewModel.consoleProperty().get());
    esrb.setValue(viewModel.esrbProperty().get());

  }

  /**
   * method that resets the fields in the view
   */
  public void reset()
  {
    viewModel.reset();
  }

  /**
   * logic for the button that cancels the editing and opens the InventoryView
   */
  public void cancel(ActionEvent actionEvent)
  {
    getViewModelFactory().getInventoryViewModel().reset();
    getViewHandler().openView("InventoryView.fxml");
  }

  /**
   * logic for the button that applies changes and  opens the InventoryView
   */
  public void confirm(ActionEvent actionEvent)
  {
    viewModel.setConsole(console.getValue().toString());
    viewModel.setEsrb(esrb.getValue().toString());

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
