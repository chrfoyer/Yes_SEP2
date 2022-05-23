package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import viewmodel.UserEditViewModel;

import java.util.Optional;

/**
 * controller for the User Edit View
 */
public class UserEditViewController extends ViewController
{
    @FXML
    public TextField fullName;
    @FXML
    public DatePicker dob;
    @FXML
    public TextField address;
    @FXML
    public TextField email;
    @FXML
    public TextField username;
    @FXML
    public TextField password;
    @FXML
    public TextField confirmPassword;

    public UserEditViewModel viewModel;
    public Label error;
    public Label balance;
    public TextField fineRefundAmount;
    public Label hasSubscription;

    /**
     * method for initializing all the variables and binding them
     */
    @Override
    protected void init()
    {
        viewModel = getViewModelFactory().getUserEditViewModel();
        fullName.textProperty().bindBidirectional(viewModel.getNameProperty());
        dob.valueProperty().bindBidirectional(viewModel.getDobProperty());
        address.textProperty().bindBidirectional(viewModel.getAddressProperty());
        email.textProperty().bindBidirectional(viewModel.getEmailProperty());
        username.textProperty().bindBidirectional(viewModel.getUsernameProperty());
        password.textProperty().bindBidirectional(viewModel.getPasswordProperty());
        confirmPassword.textProperty()
                .bindBidirectional(viewModel.getConfirmProperty());
        error.textProperty().bind(viewModel.getErrorLabel());
        balance.textProperty().bind(viewModel.getBalanceLabel());
        fineRefundAmount.textProperty()
                .bindBidirectional(viewModel.getFineRefundProperty());
        hasSubscription.textProperty().bind(viewModel.getHasSubscriptionProperty());

    }

    /**
     * method for canceling and opening the UserlistView
     */
    @FXML
    public void cancel()
    {
        getViewHandler().openView("UserListView.fxml");

    }

    /**
     * method for applying the user edit changes
     */
    @FXML
    public void apply()
    {
        viewModel.editUser();
        getViewModelFactory().getUserListViewModel().reset();
        getViewHandler().openView("UserListView.fxml");

    }

    /**
     * logic for fine/refund button pressed, fining/refunding a user and subtracting/adding from their balance
     */
    public void fine_refund()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to revoke this user's subscription?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK)
        {
            viewModel.fineRefund();
            getViewModelFactory().getUserListViewModel().reset();
            getViewHandler().openView("UserListView.fxml");
        }
    }

    /**
     * logic behind banHammer button, revoking a user's subscription
     */
    public void banHammer()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to revoke this user's subscription?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK)
        {
            viewModel.revokeSubscription();
            getViewModelFactory().getUserListViewModel().reset();
            getViewHandler().openView("UserListView.fxml");
        }
    }
}
