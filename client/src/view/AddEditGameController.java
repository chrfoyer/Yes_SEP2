package view;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import viewmodel.AddEditGameViewModel;

import java.util.Optional;

public class AddEditGameController extends ViewController
{
  public TextField gameName;
  public TextField producer;
  public ChoiceBox<String> console;
  public ChoiceBox<String> esrb;
  public Label error;
  public AddEditGameViewModel viewModel;
  public Label title;

  /**
   * method initializing all the variables and cells
   */
  @Override
  protected void init()
  {
    viewModel = getViewModelFactory().getAddEditGameViewModel();
    gameName.textProperty().bindBidirectional(viewModel.nameProperty());
    producer.textProperty().bindBidirectional(viewModel.producerProperty());
    error.textProperty().bind(viewModel.errorProperty());

    console.getItems().addAll("PC", "Xbox", "PlayStation");
    esrb.getItems().addAll("E", "E10+", "T", "M", "AO");
    console.setValue(viewModel.consoleProperty().get());
    esrb.setValue(viewModel.esrbProperty().get());
    reset();
  }

  /**
   * method that resets the fields in the view
   */
  public void reset()
  {
    viewModel.reset();
    title.setText(
            viewModel.getSelectedGameProperty() != null ? "Edit Game" : "Add Game");
  }

  /**
   * logic for the button that cancels the editing and opens the InventoryView
   */
  public void cancel(ActionEvent actionEvent)
  {
    getViewModelFactory().getInventoryViewModel().reset();
    getViewHandler().openView("InventoryView.fxml");
    viewModel.setSelectedGameProperty(null);
  }

  /**
   * logic for the button that applies changes and  opens the InventoryView
   */
  public void confirm(ActionEvent actionEvent)
  {
    viewModel.setConsole(console.getValue());
    viewModel.setEsrb(esrb.getValue());

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
            "Are you sure you want to change the game info?");
    Optional<ButtonType> option = alert.showAndWait();
    if (option.get() == ButtonType.OK)
    {
      if (viewModel.getSelectedGameProperty() == null)
        viewModel.addGame();
      else
        viewModel.editGame();

      getViewHandler().openView("InventoryView.fxml");
      //this is retarded
      //please don't use production
    }
    viewModel.setSelectedGameProperty(null);
  }
}
