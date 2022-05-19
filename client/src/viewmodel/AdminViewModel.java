package viewmodel;

import Model.Game;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mediator.RemoteModel;

import java.util.ArrayList;

/**
 * class that handles the logic for the Admin view controller
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class AdminViewModel
{

    private final RemoteModel model;
    private final StringProperty registeredUsers;
    private final StringProperty totalGames;
    private final StringProperty rentedGames;
    private final StringProperty recentGame;
    private final StringProperty errorLabel;

    /**
     * ViewModel that connects Login to the model
     *
     * @param model RemoteModel because of RMI
     */
    public AdminViewModel(RemoteModel model)
    {
        this.model = model;
        registeredUsers = new SimpleStringProperty();
        totalGames = new SimpleStringProperty();
        rentedGames = new SimpleStringProperty();
        recentGame = new SimpleStringProperty();
        errorLabel = new SimpleStringProperty();
    }

    /**
     * Getter for property
     *
     * @return registeredUsers
     */
    public StringProperty getRegisteredUsers()
    {
        return registeredUsers;
    }

    /**
     * getter for total games
     * @return totalGames
     */
    public StringProperty getTotalGames()
    {
        return totalGames;
    }

    /**
     * getter for rented games
     * @return rentedGames
     */
    public StringProperty getRentedGames()
    {
        return rentedGames;
    }

    /**
     * getter for recent game
     * @return recentGame
     */
    public StringProperty getRecentGame()
    {
        return recentGame;
    }

    /**
     * getter for error label
     * @return errorLabel
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
        updateLabels();
        errorLabel.set("");
    }

    /**
     * method for updating labels
     */
    public void updateLabels()
    {
        try
        {
            int registeredUser = 0;
            int totalGame = 0;
            int totalRent = 0;
            Game recent = model.getMostRecentGame();
            registeredUser = model.getUserList().size();
            ArrayList<Game> allGames = model.viewGames().getGamesArrayCopy();
            for (Game game : allGames)
            {
                totalGame++;
                if (game.isRented())
                    totalRent++;
            }
            registeredUsers.set(registeredUser + "");
            totalGames.set(totalGame + "");
            rentedGames.set(totalRent + "");
            recentGame.set(recent.getName());
        } catch (Exception e)
        {
            errorLabel.set(e.getMessage());
        }
    }

}
