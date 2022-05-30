package viewmodel;

import Model.Game;
import Model.GameList;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.CurrentlyLoggedUser;
import mediator.RemoteModel;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * class that handles the logic for the Browse view controller
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class BrowseViewModel
{
    final ObservableList<SimpleGameViewModel> data = FXCollections.observableArrayList();
    private final RemoteModel model;
    private final StringProperty errorLabel;
    private final ObjectProperty<SimpleGameViewModel> selectedGameProperty;
    // Need the list of games in an observable list
    // Need a property for the selected simple game view model

    /**
     * constructor for browse view model
     *
     * @param model to asssign to the remotemodel
     */
    public BrowseViewModel(RemoteModel model)
    {
        this.model = model;
        errorLabel = new SimpleStringProperty();
        selectedGameProperty = new SimpleObjectProperty<>();
        GameList temp = null;
        try
        {
            temp = model.viewGames();
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
        for (Game game : temp.getGamesArrayCopy())
        {
            if (!game.isRented())
            {
                data.add(new SimpleGameViewModel(game));
            }
        }
    }

    /**
     * method for resetting
     */
    public void reset()
    {
        data.clear();
        errorLabel.set("");
        try
        {
            GameList temp = model.viewGames();
            for (Game game : temp.getAvailableGames())
            {
                data.add(new SimpleGameViewModel(game));
            }
        } catch (Exception e)
        {
            errorLabel.set(e.getMessage());
        }
    }

    /**
     * getter for getting data
     *
     * @return data
     */
    public ObservableList<SimpleGameViewModel> getData()
    {
        return data;
    }

    /**
     * method for getting the error label
     *
     * @return errorLabel
     */
    public StringProperty getErrorLabel()
    {
        return errorLabel;
    }

    /**
     * method for renting a game
     *
     * @param game game to be rented
     * @return boolean true or false of whether the game can be rented or not
     */
    public boolean rentGame(Game game)
    {
        try
        {
            if (game == null)
                throw new IllegalArgumentException("You must make a selection first!");
            model.rentGame(game, CurrentlyLoggedUser.getLoggedInUser());
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            errorLabel.set(e.getMessage());
        }
        return false;
    }

    /**
     * Search through the available games using the parameters and set the observable list of game view models
     * accordingly.
     *
     * @param name    A part of the name one is searching for
     * @param console The selected console
     * @param esrb    The selected age restricted rating
     */
    public void search(String name, String console, String esrb)
    {
        try
        {
            data.clear();
            ArrayList<Game> searchHolder = model.searchGames(name, console, esrb);
            ArrayList<SimpleGameViewModel> searchViewModelHolder = new ArrayList<>();
            for (Game game : searchHolder)
            {
                searchViewModelHolder.add(new SimpleGameViewModel(game));
            }
            Platform.runLater(() -> data.addAll(searchViewModelHolder));
        } catch (RemoteException e)
        {
            errorLabel.set(e.getMessage());
        }
    }

    /**
     * method for checking if the user can rent a game
     *
     * @param selectedGame game to check its esrb to the users age
     * @return boolean true or false depending on if the user meets the age requirement
     */
    public boolean ageCheck(Game selectedGame)
    {
        try
        {
            if (CurrentlyLoggedUser.getLoggedInUser().getAge() < 17 && selectedGame.getEsrb().equals("M"))
            {
                throw new IllegalAccessException("You are too young for this game");
            } else if (CurrentlyLoggedUser.getLoggedInUser().getAge() < 18 && selectedGame.getEsrb().equals("AO"))
            {
                throw new IllegalAccessException("You are too young for this game");
            }
        } catch (Exception e)
        {
            errorLabel.set(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * method for setting the selected game property
     *
     * @param selectedGameProperty to assign to selectedGameProperty
     */
    public void setSelectedGameProperty(SimpleGameViewModel selectedGameProperty)
    {
        this.selectedGameProperty.set(selectedGameProperty);
    }
}
