package viewmodel;

import Model.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.CurrentlyLoggedUser;
import mediator.RemoteModel;

import java.util.ArrayList;

/**
 * class that handles the logic for the UserList View
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class UserListViewModel
{
    private final ObservableList<SimpleUserViewModel> list;
    private final ObjectProperty<SimpleUserViewModel> selectedUser;
    private final StringProperty error;
    private final RemoteModel model;

    /**
     * Constructor for a new User list view model.
     *
     * @param model the model we got from server
     */
    public UserListViewModel(RemoteModel model)
    {
        list = FXCollections.observableArrayList();
        selectedUser = new SimpleObjectProperty<>();
        error = new SimpleStringProperty();
        this.model = model;
        reset();
    }

    /**
     * Resets the viewModel
     */
    public void reset()
    {
        fillTable();
        error.set("");
    }

    /**
     * Fills the table with relevant information about all the users
     */
    public void fillTable()
    {
        try
        {
            list.clear();
            ArrayList<User> users = model.getUserList().getUsers();
            for (User user : users)
            {
                SimpleUserViewModel temp = new SimpleUserViewModel(user);
                list.add(temp);
            }
        } catch (Exception e)
        {
            error.set(e.getMessage());
        }
    }

    /**
     * Gets users.
     *
     * @return the users
     */
    public ObservableList<SimpleUserViewModel> getUsers()
    {
        return list;
    }

    /**
     * Sets selected user.
     *
     * @param selectedUser the selected user
     */
    public void setSelectedUser(SimpleUserViewModel selectedUser)
    {
        this.selectedUser.set(selectedUser);
    }

    /**
     * Error property string property.
     *
     * @return the string property
     */
    public StringProperty errorProperty()
    {
        return error;
    }

    /**
     * Gets the Error as a string
     *
     * @return the error
     */
    public String getError()
    {
        return error.get();
    }

    /**
     * Sets the error property
     *
     * @param error The string to set the property to
     */
    public void setError(String error)
    {
        this.error.set(error);
    }

    /**
     * Removes the selected user.
     */
    public void removeUser()
    {
        try
        {
            if (selectedUser.get().getUser().getUsername().equals(CurrentlyLoggedUser.getLoggedInUser().getUsername()))
            {
                throw new IllegalArgumentException("Congratulations you played yourself");
            }

            model.removeUser(selectedUser.get().getUser());
            fillTable();
        } catch (Exception ex)
        {
            error.set(ex.getMessage());
        }

    }
}
