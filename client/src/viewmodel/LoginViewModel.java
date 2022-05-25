package viewmodel;

import Model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mediator.CurrentlyLoggedUser;
import mediator.RemoteModel;

/**
 * class that handles the logic for the Login View
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class LoginViewModel
{

    private final RemoteModel model;
    private final StringProperty usernameProperty;
    private final StringProperty passwordProperty;
    private final StringProperty errorLabel;

    /**
     * ViewModel that connects Login to the model
     *
     * @param model RemoteModel because of RMI
     */
    public LoginViewModel(RemoteModel model)
    {
        this.model = model;
        usernameProperty = new SimpleStringProperty();
        passwordProperty = new SimpleStringProperty();
        errorLabel = new SimpleStringProperty();
    }

    /**
     * Getter for property
     *
     * @return usernameProperty
     */
    public StringProperty getUsernameProperty()
    {
        return usernameProperty;
    }

    /**
     * Getter for property
     *
     * @return passwordProperty
     */
    public StringProperty getPasswordProperty()
    {
        return passwordProperty;
    }

    /**
     * getter for error label
     *
     * @return errorLabel
     */
    public StringProperty getErrorLabel()
    {
        return errorLabel;
    }

    /**
     * boolean method to check if user can log in
     *
     * @return true or false depending on whether a user can log in
     */
    public boolean login()
    {
        try
        {
            if (getUsernameProperty().get().equals(""))
                throw new IllegalArgumentException("Username cant be empty");
            if (getPasswordProperty().get().equals(""))
                throw new IllegalArgumentException("Password cant be empty");

            if (model.login(getUsernameProperty().get(), getPasswordProperty().get()))
            {
                //todo security fix
                CurrentlyLoggedUser.login(
                        new User(getUsernameProperty().get(), getPasswordProperty().get()));
                return true;
            }
            return false;
        } catch (Exception e)
        {
            errorLabel.set(e.getMessage());
        }
        return false;
    }

    /**
     * Call this to reset the text inside the fields
     */
    public void reset()
    {
        usernameProperty.set("");
        passwordProperty.set("");
        errorLabel.set("");
    }

    /**
     * method for logging out a user
     */
    public void logout()
    {
        CurrentlyLoggedUser.logout();
    }
}
