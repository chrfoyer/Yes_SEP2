package viewmodel;

import Model.Game;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.CurrentlyLoggedUser;
import mediator.RemoteModel;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class with the functionality for the UserProfileController
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.2
 */
public class UserProfileViewModel
{

    /**
     * The Rented games.
     */
    final ObservableList<SimpleGameViewModel> rentedGames = FXCollections.observableArrayList();
    private final RemoteModel model;
    private final StringProperty usernameProperty;
    private final StringProperty errorLabel;

    /**
     * ViewModel that connects Login to the model
     *
     * @param model RemoteModel because of RMI
     */
    public UserProfileViewModel(RemoteModel model)
    {
        this.model = model;
        usernameProperty = new SimpleStringProperty();
        errorLabel = new SimpleStringProperty();
    }

    /**
     * Getter for property
     *
     * @return usernameProperty username property
     */
    public StringProperty getUsernameProperty()
    {
        return usernameProperty;
    }

    /**
     * Getter for property
     *
     * @return errorProperty error label
     */
    public StringProperty getErrorLabel()
    {
        return errorLabel;
    }

    /**
     * Call this to reset the text inside the fields
     */
    public void reset()
    {
        usernameProperty.set(
                "Currently logged in: " + CurrentlyLoggedUser.getLoggedInUser()
                        .getUsername());
        fillTable();
    }

    /**
     * Fills the table with relevant information
     * Only puts in the games that the CurrentlyLogged in users has rented
     */
    public void fillTable()
    {
        ArrayList<Game> userRented = null;
        try
        {
            userRented = model.getGamesRentedByUser(CurrentlyLoggedUser.getLoggedInUser());
        } catch (SQLException e)
        {
            e.printStackTrace();
            errorLabel.set(e.getMessage());
        } catch (java.rmi.RemoteException e)
        {
            e.printStackTrace();
        }

        try
        {
            rentedGames.clear();
            for (Game game : userRented)
            {
                rentedGames.add(new SimpleGameViewModel(game));
            }
        } catch (Exception e)
        {
            errorLabel.set(e.getMessage());
        }
    }

    /**
     * Logic for returning the selected game
     *
     * @param game game to be returned
     */
    public void returnGame(Game game)
    {
        try
        {
            model.returnGame(game, CurrentlyLoggedUser.getLoggedInUser());
        } catch (Exception e)
        {
            errorLabel.set(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Leave review.
     *
     * @param review the review
     * @param game   the game
     */
    public void leaveReview(int review, SimpleGameViewModel game)
    {
        try
        {
            model.leaveReview(review, game.getGame());
        } catch (Exception e)
        {
            errorLabel.set(e.getMessage());
        }
    }

    /**
     * Logic for extending the selected game for 5 days
     *
     * @param game game to be extended
     */
    public void extendGame(SimpleGameViewModel game)
    {
        if (game != null)
        {
            try
            {
                model.extendGame(game.getGame(),CurrentlyLoggedUser.getLoggedInUser());
                reset();
            } catch (Exception e)
            {
                errorLabel.set(e.getMessage());
            }

        } else
        {
            errorLabel.set("Game must be selected first");
        }

    }

    /**
     * getter for an ObservableList displayed in the table
     *
     * @return ObservableList of simple game view models
     */
    public ObservableList<SimpleGameViewModel> getData()
    {
        return rentedGames;
    }

}
