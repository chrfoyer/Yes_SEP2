package viewmodel;

import Model.Game;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mediator.RemoteModel;

/**
 * class that handles the logic for the Add Edit View controller
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class AddEditGameViewModel
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

    /**
     * constructor creating view model and setting the remote model to model
     * @param model what the model will be set to
     */
    public AddEditGameViewModel(RemoteModel model)
    {
        this.model = model;
        this.name = new SimpleStringProperty();
        this.timeLeft = new SimpleObjectProperty<>();
        this.producer = new SimpleStringProperty();
        this.esrb = new SimpleStringProperty();
        this.rented = new SimpleObjectProperty<>();
        this.console = new SimpleStringProperty();
        this.error = new SimpleStringProperty();
        selectedGameProperty = new SimpleObjectProperty<>();
    }

    /**
     * logic behind the reset for the Add Edit view
     */
    public void reset()
    {
        try
        {
            if (selectedGameProperty.get() == null)
            {
                name.set("");
                producer.set("");
                esrb.set("E");
                console.set("PC");
            } else
            {
                SimpleGameViewModel selectedGameViewModel = selectedGameProperty.get();
                name.set(selectedGameViewModel.getNameProperty().get());
                timeLeft.set(selectedGameViewModel.getTimeProperty().get());
                producer.set(selectedGameViewModel.getProducer().get());
                esrb.set(selectedGameViewModel.getEsrbProperty().get());
                rented.set(selectedGameViewModel.getRentedProperty().get());
                console.set(selectedGameViewModel.getConsole().get());

                error.set("");
            }
        } catch (Exception e)
        {
            error.set(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * method for getting the SimpleGameViewModel of the selected game
     * @return SimpleGameViewModel of selected game
     */
    public SimpleGameViewModel getSelectedGameProperty()
    {
        return selectedGameProperty.get();
    }

    /**
     * method for setting the selected game property
     * @param selectedGameViewModel The simple game view model of the selected game
     */
    public void setSelectedGameProperty(SimpleGameViewModel selectedGameViewModel)
    {
        this.selectedGameProperty.set(selectedGameViewModel);
    }

    /**
     * method for getting the name of the game
     * @return String game name
     */
    public String getName()
    {
        return name.get();
    }

    /**
     * method for setting the game's name
     * @param name what to set game name to
     */
    public void setName(String name)
    {
        this.name.set(name);
    }

    /**
     * method for getting name property
     * @return StringProperty name
     */
    public StringProperty nameProperty()
    {
        return name;
    }

    /**
     * method for getting the game's console property
     * @return StringProperty console
     */
    public StringProperty consoleProperty()
    {
        return console;
    }

    /**
     * method for getting the game's producer property
     * @return StringProperty producer
     */
    public StringProperty producerProperty()
    {
        return producer;
    }

    /**
     * method for getting the game's esrb property
     * @return StringProperty esrb
     */
    public StringProperty esrbProperty()
    {
        return esrb;
    }

    /**
     * method for getting the error property
     * @return StringProperty error
     */
    public StringProperty errorProperty()
    {
        return error;
    }

    /**
     * method for setting the game's esrb
     * @param esrb to assign to the game
     */
    public void setEsrb(String esrb)
    {
        this.esrb.set(esrb);
    }

    /**
     * method for setting the games console
     * @param console to assign to the game
     */
    public void setConsole(String console)
    {
        this.console.set(console);
    }

    /**
     * method for adding a game
     */
    public void addGame()
    {
        try
        {
            if (name.get().equals("") || producer.get().equals(""))
                throw new IllegalArgumentException("Name and producer cant be empty");
            Game gameToAdd = new Game(name.get(), producer.get(), console.get(),
                    esrb.get());
            model.addGame(gameToAdd);
        } catch (Exception e)
        {
            error.set(e.getMessage());
        }
    }

    /**
     * method for editing a game
     */
    public void editGame()
    {
        try
        {
            if(rented.get())throw new IllegalArgumentException("You cannot edit a rented game!");

            if (name.get().equals("") || producer.get().equals(""))
                throw new IllegalArgumentException("Name and producer cant be empty");

            Game gameOld = new Game(selectedGameProperty.get().getGame().getName(),
                    selectedGameProperty.get().getGame().getProducer(),
                    selectedGameProperty.get().getGame().getConsole(),
                    selectedGameProperty.get().getGame().getEsrb());

            Game gameNew = selectedGameProperty.get().getGame();
            gameNew.setConsole(console.get());
            gameNew.setProducer(producer.get());
            gameNew.setEsrb(esrb.get());
            gameNew.setName(name.get());

            model.updateGameInfo(gameOld, gameNew);

            //change finished without error
        } catch (Exception e)
        {
            error.set(e.getMessage());
        }
    }
}
