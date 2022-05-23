package view;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import viewmodel.SignupViewModel;

/**
 * controller for the Signup View
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */

public class SignupViewController extends ViewController
{
    public TextField fullName;
    public DatePicker dob;
    public TextField address;
    public TextField email;
    public TextField username;
    public TextField confirmPassword;
    public TextField password;
    public Label error;
    private SignupViewModel viewModel;

    /**
     * initializes all the variables and binds them
     */
    @Override
    protected void init()
    {
        viewModel = getViewModelFactory().getSignupViewModel();
        fullName.textProperty().bindBidirectional(viewModel.getNameProperty());
        address.textProperty().bindBidirectional(viewModel.getAddressProperty());
        email.textProperty().bindBidirectional(viewModel.getEmailProperty());
        username.textProperty().bindBidirectional(viewModel.getUsernameProperty());
        password.textProperty().bindBidirectional(viewModel.getPasswordProperty());
        confirmPassword.textProperty()
                .bindBidirectional(viewModel.getConfirmProperty());
        error.textProperty().bind(viewModel.getErrorLabel());
        reset();
        dob.valueProperty().bindBidirectional(viewModel.getDobProperty());
    }

    /**
     * method for resetting viewmodel
     */
    public void reset()
    {
        viewModel.reset();
    }

    /**
     * method for signing up
     * @param actionEvent when clicking the signup button
     */
    public void signup(ActionEvent actionEvent)
    {
        if (viewModel.signup())
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION,"Welcome to the system!");
            alert.setHeaderText("Successful signup");
            alert.showAndWait();
            getViewHandler().openView("LoginView.fxml");
            getViewModelFactory().getSignupViewModel().reset();
        }

    }

    /**
     * method for canceling the signupview
     *
     * @param actionEvent button for canceling signupview
     */
    public void cancel(ActionEvent actionEvent)
    {
        getViewHandler().openView("LoginView.fxml");
        getViewModelFactory().getSignupViewModel().reset();
    }
}
