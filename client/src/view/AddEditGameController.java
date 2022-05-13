package view;

import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import viewmodel.AddEditGameViewModel;

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

  public void reset() {
    viewModel.reset();
  }

  public void cancel(ActionEvent actionEvent)
  {
    getViewModelFactory().getInventoryViewModel().reset();
    getViewHandler().openView("InventoryView.fxml");
  }

  public void confirm(ActionEvent actionEvent)
  {
  }
}
