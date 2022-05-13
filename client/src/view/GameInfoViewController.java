package view;

import Model.Game;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import viewmodel.GameInfoViewModel;
import viewmodel.SimpleGameViewModel;

public class GameInfoViewController extends ViewController {

  public TextField name;
  public TextField producer;
  public TextField esrb;
  public TextField rating;
  public TextField console;
  public Label error;
  public GameInfoViewModel viewModel;

  /**
   * method initializing all the variables and cells
   */
  @Override
  protected void init() {
    viewModel = getViewModelFactory().getGameInfoViewModel();
    name.textProperty().bindBidirectional(viewModel.nameProperty());
    producer.textProperty().bindBidirectional(viewModel.producerProperty());
    esrb.textProperty().bindBidirectional(viewModel.esrbProperty());
    // Bind rating
    console.textProperty().bindBidirectional(viewModel.consoleProperty());
    error.textProperty().bind(viewModel.errorProperty());
  }

  /**
   * method that resets the fields in the view
   */
  public void reset() {
    viewModel.reset();
  }

  /**
   * Logic for the button that opens the BrowseView
   */
  public void backButton() {
    // TODO: 12/05/2022 reset

    getViewHandler().openView("BrowseView.fxml");
    // Note: This might not always be the case, depending upon where we come from to get to this window.
  }


}
