package viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mediator.RemoteModel;

/**
 * Class with the functionality for the GameInfoViewController
 */
public class GameInfoViewModel
{
    private final RemoteModel model;
    private final ObjectProperty<SimpleGameViewModel> selectedGameProperty;
    private final StringProperty name;
    private final ObjectProperty<Integer> timeLeft;
    private final StringProperty console;
    private final StringProperty producer;
    private final StringProperty esrb;
    private final ObjectProperty<Boolean> rented;
    private final StringProperty error;
    private final StringProperty review;

    /**
     *constructor for Game Info view model
     * @param model to asssign to the remotemodel
     */
    public GameInfoViewModel(RemoteModel model)
    {
        this.model = model;
        this.name = new SimpleStringProperty();
        this.timeLeft = new SimpleObjectProperty<>();
        this.producer = new SimpleStringProperty();
        this.esrb = new SimpleStringProperty();
        this.rented = new SimpleObjectProperty<>();
        this.console = new SimpleStringProperty();
        this.error = new SimpleStringProperty();
        this.review = new SimpleStringProperty();
        selectedGameProperty = new SimpleObjectProperty<>();
    }

    /**
     * method for resetting the view
     */
    public void reset()
    {
        if (selectedGameProperty.get() != null)
        {
            SimpleGameViewModel selected = selectedGameProperty.get();
            name.set(selected.getNameProperty().get());
            timeLeft.set(selected.getTimeProperty().get());
            producer.set(selected.getProducer().get());
            esrb.set(selected.getEsrbProperty().get());
            rented.set(selected.getRentedProperty().get());
            console.set(selected.getConsole().get());
            review.set(selected.getReview().get() + "");
            error.set("");
        } else
        {
            name.set("");
            timeLeft.set(null);
            producer.set("");
            esrb.set("");
            rented.set(null);
            console.set("");
            review.set("");
            error.set("");
        }
    }

    /**
     * getter for the selectedGameProperty
     * @return SimpleGameViewModel selectedGameProperty
     */
    public SimpleGameViewModel getSelectedGameProperty()
    {
        return selectedGameProperty.get();
    }

    /**
     * setter for the selectedGameProperty
     * @param selectedGameViewModel viewmodel to assign to selectedGameProperty
     */
    public void setSelectedGameProperty(SimpleGameViewModel selectedGameViewModel)
    {
        this.selectedGameProperty.set(selectedGameViewModel);
    }

    /**
     * getter for selectedGameProperty
     * @return
     */
    public ObjectProperty<SimpleGameViewModel> selectedGamePropertyProperty()
    {
        return selectedGameProperty;
    }

    /**
     * getter for game's name
     * @return game's name
     */
    public String getName()
    {
        return name.get();
    }

    /**
     * setter for the game's name
     * @param name name to assign to game
     */
    public void setName(String name)
    {
        this.name.set(name);
    }

    /**
     * getter for the name StringProperty of game
     * @return StringProperty name
     */
    public StringProperty nameProperty()
    {
        return name;
    }

    /**
     * getter for the time left on a rental
     * @return time left on a rental
     */
    public Integer getTimeLeft()
    {
        return timeLeft.get();
    }

    /**
     * setter for the time left on a game rental
     * @param timeLeft time left on a game rental
     */
    public void setTimeLeft(Integer timeLeft)
    {
        this.timeLeft.set(timeLeft);
    }

    /**
     * getter for the Object Property<Integer> of time left on a rental
     * @return timeleft
     */
    public ObjectProperty<Integer> timeLeftProperty()
    {
        return timeLeft;
    }

    /**
     * getter for the console the game is played on
     * @return console the game is played on
     */
    public String getConsole()
    {
        return console.get();
    }

    /**
     * setter for the game's console
     * @param console console to assign to the game
     */
    public void setConsole(String console)
    {
        this.console.set(console);
    }

    /**
     * getter for the StringProperty console of the game
     * @return console
     */
    public StringProperty consoleProperty()
    {
        return console;
    }

    /**
     * getter for the producer of a game
     * @return producer of the game
     */
    public String getProducer()
    {
        return producer.get();
    }

    /**
     * setter for the producer of a game
     * @param producer to assign to a game
     */
    public void setProducer(String producer)
    {
        this.producer.set(producer);
    }

    /**
     * getter for the StringProperty producer of a game
     * @return producer
     */
    public StringProperty producerProperty()
    {
        return producer;
    }

    /**
     * getter for the ESRB of a game
     * @return ESRB of a game
     */
    public String getEsrb()
    {
        return esrb.get();
    }

    /**
     * setter for the ESRB of a game
     * @param esrb esrb to be assigned to game
     */
    public void setEsrb(String esrb)
    {
        this.esrb.set(esrb);
    }

    /**
     * getter for the StringProperty esrbProperty
     * @return esrb
     */
    public StringProperty esrbProperty()
    {
        return esrb;
    }

    /**
     * getter for the StringProperty review of a game
     * @return review
     */
    public StringProperty reviewProperty()
    {
        return review;
    }

    /**
     * method for checking if a game is rented
     * @return true or false depending on if a game is rented or not
     */
    public Boolean getRented()
    {
        return rented.get();
    }

    /**
     * setter for a game's rent status
     * @param rented status to be assigned to a game
     */
    public void setRented(Boolean rented)
    {
        this.rented.set(rented);
    }

    /**
     * gets the ObjectProperty<Boolean> rentedProperty
     * @return rentedProperty
     */
    public ObjectProperty<Boolean> rentedProperty()
    {
        return rented;
    }

    /**
     * gets the error
     * @return error
     */
    public String getError()
    {
        return error.get();
    }

    /**
     * sets the error
     * @param error to assign to error
     */
    public void setError(String error)
    {
        this.error.set(error);
    }

    /**
     * gets the errorProperty
     * @return error
     */
    public StringProperty errorProperty()
    {
        return error;
    }
}
