package view;

import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import viewmodel.SignupViewModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * controller for the Signup View
 *
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
        dob.valueProperty().bindBidirectional(viewModel.getDobProperty());
        reset();
    }

    /**
     * method for resetting viewmodel
     */
    public void reset()
    {
        viewModel.reset();
        dob.setValue(null);
    }

    /**
     * method for signing up
     */
    public void signup()
    {
        takeDate();
        if (viewModel.signup())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Welcome to the system!");
            alert.setHeaderText("Successful signup");
            alert.showAndWait();
            getViewHandler().openView("LoginView.fxml");
            getViewModelFactory().getSignupViewModel().reset();
        }

    }

    /**
     * method for canceling the signupview
     */
    public void cancel()
    {
        getViewHandler().openView("LoginView.fxml");
        getViewModelFactory().getSignupViewModel().reset();
    }

    /**
     * Calls signup when pressing enter
     */
    public void onEnter()
    {
        this.signup();
    }

    /**
     * Makes the date picker show the date that is written in the text field
     */
    public void takeDate()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateString = dob.getEditor().getText();
        try
        {
            LocalDate date = LocalDate.parse(dateString, formatter);
            dob.setValue(date);
        } catch (Exception e)
        {
            if (!dob.getEditor().getText().isEmpty())
            {
                viewModel.setErrorLabel("Please rewrite the date");
            }
        }
    }

}
