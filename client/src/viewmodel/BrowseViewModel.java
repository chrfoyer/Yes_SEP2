package viewmodel;

import Model.Game;
import Model.GameList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.CurrentlyLoggedUser;
import mediator.RemoteModel;

import java.rmi.RemoteException;

// TODO: 12/05/2022 Delegation from the controller 
public class BrowseViewModel
{
    final ObservableList<SimpleGameViewModel> data = FXCollections.observableArrayList();
    private final RemoteModel model;
    private final StringProperty errorLabel;
    private final ObjectProperty<SimpleGameViewModel> selectedGameProperty;
    // Need the list of games in an observable list
    // Need a property for the selected simple game view model

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

    public void reset()
    {
        data.clear();
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
        errorLabel.set("");
    }

    public ObservableList<SimpleGameViewModel> getData()
    {
        return data;
    }

    public StringProperty getErrorLabel()
    {
        return errorLabel;
    }

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
            errorLabel.set(e.getMessage());
        }
        return false;
    }

    public boolean ageCheck(Game selectedGame){
        try {
            if(CurrentlyLoggedUser.getLoggedInUser().getAge() < 17 && selectedGame.getEsrb().equals("M")){
                throw new IllegalAccessException("You are too young for this game");
            } else if(CurrentlyLoggedUser.getLoggedInUser().getAge() < 18 && selectedGame.getEsrb().equals("AO"))
            {
                throw new IllegalAccessException("You are too young for this game");
            }
        } catch(Exception e){
            errorLabel.set(e.getMessage());
            return false;
        }
        return true;
    }

    public void setSelectedGameProperty(SimpleGameViewModel selectedGameProperty)
    {
        this.selectedGameProperty.set(selectedGameProperty);
    }
}
