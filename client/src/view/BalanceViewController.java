package view;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import viewmodel.BalanceViewModel;

import java.util.Optional;

/**
 * controller for the BalanceView
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class BalanceViewController extends ViewController
{
    public Label balanceAmount;
    public Label errorLabel;
    public Label subscriptionDisplay;
    private BalanceViewModel viewModel;

    /**
     * method initializing all variables
     */
    @Override
    protected void init()
    {
        viewModel = getViewModelFactory().getBalanceViewModel();

        balanceAmount.textProperty().bind(viewModel.getBalanceAmount());
        errorLabel.textProperty().bind(viewModel.getErrorLabel());
        subscriptionDisplay.textProperty().bind(viewModel.getSubssriptionDisplay());
        viewModel.reset();
    }

    /**
     * logic for teh addFunds button, adding funds to a user
     * @param actionEvent addFunds button pressed
     */
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
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * logic behind the paySubscription button, using funds to give the user a subscription
     * @param actionEvent pay subscription button pressed
     */
    public void paySubscription(ActionEvent actionEvent)
    {
        viewModel.paySubscription();
    }

    /**
     * logic for the back button pressed, opening the UserProfileView
     * @param actionEvent back button pressed
     */
    public void back(ActionEvent actionEvent)
    {
        getViewModelFactory().getUserProfileViewModel().reset();
        getViewHandler().openView("UserProfileView.fxml");
    }
}
