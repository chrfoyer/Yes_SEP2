package view;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import viewmodel.GameInfoViewModel;

/**
 * controller for the Game Info View
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class GameInfoViewController extends ViewController
{

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
    protected void init()
    {
        viewModel = getViewModelFactory().getGameInfoViewModel();
        name.textProperty().bindBidirectional(viewModel.nameProperty());
        producer.textProperty().bindBidirectional(viewModel.producerProperty());
        esrb.textProperty().bindBidirectional(viewModel.esrbProperty());
        // Bind rating
        console.textProperty().bindBidirectional(viewModel.consoleProperty());
        error.textProperty().bind(viewModel.errorProperty());
        rating.textProperty().bind(viewModel.reviewProperty());
    }

    /**
     * method that resets the fields in the view
     */
    public void reset()
    {
        viewModel.reset();
    }

    /**
     * Logic for the button that opens the BrowseView
     */
    public void backButton()
    {
        getViewModelFactory().getBrowseViewModel().reset();
        getViewHandler().openView("BrowseView.fxml");
    }

}
