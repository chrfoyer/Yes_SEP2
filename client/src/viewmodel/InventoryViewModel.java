package viewmodel;

import Model.Game;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.RemoteModel;

import java.util.ArrayList;

/**
 * class that handles the logic for Inventory View
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
// TODO: 12/05/2022 Delegation from the controller
public class InventoryViewModel
{
    private final RemoteModel model;
    private final StringProperty errorLabel;
    private final ObjectProperty<SimpleGameViewModel> selectedGameProperty;
    private final ObservableList<SimpleGameViewModel> list;
    // Need the list of games in an observable list
    // Need a property for the selected simple game view model

    /**
     * constructor for the Inventory Viewmodel
     *
     * @param model model to assign to the remotemodel
     */
    public InventoryViewModel(RemoteModel model)
    {
        this.model = model;
        errorLabel = new SimpleStringProperty();
        selectedGameProperty = new SimpleObjectProperty<>();
        list = FXCollections.observableArrayList();
    }

    /**
     * method for resetting
     */
    public void reset()
    {
        fillTable();
    }

    /**
     * method for filling the table
     */
    public void fillTable()
    {
        try
        {
            list.clear();
            ArrayList<Game> games = model.viewGames().getGamesArrayCopy();
            for (Game game : games)
            {
                SimpleGameViewModel temp = new SimpleGameViewModel(game);
                if (!list.contains(temp))
                    list.add(temp);
            }
        } catch (Exception e)
        {
            errorLabel.set(e.getMessage());
        }
    }

    /**
     * gets the ObservableList<SimpleGameViewModel> list
     *
     * @return ObservableList<SimpleGameViewModel> list
     */
    public ObservableList<SimpleGameViewModel> getList()
    {
        return list;
    }

    /**
     * gets the ObservableList<SimpleGameViewModel> selectedGameProperty
     *
     * @return ObservableList<SimpleGameViewModel> selectedGameProperty
     */
    public ObjectProperty<SimpleGameViewModel> getSelectedGameProperty()
    {
        return selectedGameProperty;
    }

    /**
     * sets the SimpleGameViewModel selectedGameProperty
     *
     * @param selectedGameProperty selectedGameProperty
     */
    public void setSelectedGameProperty(SimpleGameViewModel selectedGameProperty)
    {
        this.selectedGameProperty.set(selectedGameProperty);
    }

    /**
     * method for removing a game
     */
    public void removeGame()
    {
        try
        {
            if (selectedGameProperty.get().getGame().isRented())
                throw new IllegalArgumentException("Cannot delete a rented game, wait until user returns it!");
            model.removeGame(selectedGameProperty.get().getGame());
            reset();
        } catch (Exception e)
        {
            errorLabel.set(e.getMessage());
        }
    }

    /**
     * method for getting the error
     *
     * @return errorLabel
     */
    public StringProperty getError()
    {
        return errorLabel;
    }
}
