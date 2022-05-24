package viewmodel;

import Model.User;
import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * class that holds a User object and relevant properties to display information
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class SimpleUserViewModel
{

    private final StringProperty username;
    private final StringProperty password;
    private final BooleanProperty isAdmin;
    private final StringProperty email;
    private final StringProperty address;
    private final StringProperty name;
    private final ObjectProperty<LocalDate> bday;
    private final BooleanProperty hasSubscription;
    private final User user;
    private IntegerProperty age;

    /**
     * Constructor for new Simple user view model.
     *
     * @param user the user
     */
    public SimpleUserViewModel(User user)
    {
        username = new SimpleStringProperty(user.getUsername());
        password = new SimpleStringProperty(user.getPassword());
        isAdmin = new SimpleBooleanProperty(user.isAdmin());
        email = new SimpleStringProperty(user.getEmail());
        address = new SimpleStringProperty(user.getAddress());
        name = new SimpleStringProperty(user.getName());
        bday = new SimpleObjectProperty<>(user.getBday());
        hasSubscription = new SimpleBooleanProperty(user.hasSubscription());
        this.user = user;
        // TODO: 13/05/2022
        // age = new SimpleIntegerProperty(user....)
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername()
    {
        return username.get();
    }

    /**
     * Username property string property.
     *
     * @return the string property
     */
    public StringProperty usernameProperty()
    {
        return username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword()
    {
        return password.get();
    }

    /**
     * Is is admin boolean.
     *
     * @return the boolean
     */
    public boolean isIsAdmin()
    {
        return isAdmin.get();
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail()
    {
        return email.get();
    }

    /**
     * Email property string property.
     *
     * @return the string property
     */
    public StringProperty emailProperty()
    {
        return email;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress()
    {
        return address.get();
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName()
    {
        return name.get();
    }

    /**
     * Gets bday.
     *
     * @return the bday
     */
    public LocalDate getBday()
    {
        return bday.get();
    }

    /**
     * Bday property object property.
     *
     * @return the object property
     */
    public ObjectProperty<LocalDate> bdayProperty()
    {
        return bday;
    }

    /**
     * Is has subscription boolean.
     *
     * @return the boolean
     */
    public boolean isHasSubscription()
    {
        return hasSubscription.get();
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    public int getAge()
    {
        return age.get();
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser()
    {
        return user;
    }
}
