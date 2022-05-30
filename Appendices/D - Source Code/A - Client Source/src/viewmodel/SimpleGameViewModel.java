package viewmodel;

import Model.Game;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * class that holds a game object, and relevant properties to display information\
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class SimpleGameViewModel
{
    private final StringProperty name;
    private final ObjectProperty<Integer> timeLeft;
    private final StringProperty console;
    private final StringProperty producer;
    private final StringProperty esrb;
    private final ObjectProperty<Boolean> rented;
    private final ObjectProperty<LocalDate> dateAdded;
    private final Game game;
    private final ObjectProperty<Float> review;

    /**
     * constructor taking in a Game class
     *
     * @param game Game that will be displayed
     */
    public SimpleGameViewModel(Game game)
    {
        this.name = new SimpleStringProperty(game.getName());
        this.timeLeft = new SimpleObjectProperty<>(game.getDaysLeft());
        this.producer = new SimpleStringProperty(game.getProducer());
        this.esrb = new SimpleStringProperty(game.getEsrb());
        this.rented = new SimpleObjectProperty<>(game.isRented());
        this.console = new SimpleStringProperty(game.getConsole());
        this.dateAdded = new SimpleObjectProperty<>(game.getDateAdded());
        this.review = new SimpleObjectProperty(game.getReviewAverage());
        this.game = game;
    }

    /**
     * Getter for the name
     *
     * @return StringProperty name
     */
    public StringProperty getNameProperty()
    {
        return name;
    }

    /**
     * Getter for the Time remaining
     *
     * @return int timeLeft
     */
    public ObjectProperty<Integer> getTimeProperty()
    {
        return timeLeft;
    }

    /**
     * Getter for the rental boolean
     *
     * @return boolean name
     */
    public ObjectProperty<Boolean> getRentedProperty()
    {
        return rented;
    }

    /**
     * Getter for the rental boolean in string form
     *
     * @return StringProperty Yes or No
     */
    public StringProperty getRentedStringProperty()
    {
        if (rented.get())
        {
            return new SimpleStringProperty("Yes");
        } else
        {
            return new SimpleStringProperty("No");
        }
    }

    /**
     * Getter for the esrb field
     *
     * @return StringProperty esrb
     */
    public StringProperty getEsrbProperty()
    {
        return esrb;
    }

    /**
     * Getter for the producer field
     *
     * @return StringProperty
     */
    public StringProperty getProducer()
    {
        return producer;
    }

    /**
     * Getter for the console field
     *
     * @return StringProperty
     */
    public StringProperty getConsole()
    {
        return console;
    }

    /**
     * Getter for the Game included
     *
     * @return Game
     */
    public Game getGame()
    {
        return game;
    }

    /**
     * Getter for the date when the was added to the system
     *
     * @return LocalDate
     */
    public ObjectProperty<LocalDate> getDateAdded()
    {
        return dateAdded;
    }

    /**
     * Gets the average rating of the game
     *
     * @return float 1.0 - 5.0
     */
    public ObjectProperty<Float> getReview()
    {
        return review;
    }
}
