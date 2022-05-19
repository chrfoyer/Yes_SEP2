package viewmodel;

import Model.Game;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mediator.RemoteModel;

/**
 * class that handles the logic for the Add Edit View
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
     * constructer creating view model and setting the remote model to model
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
     * method for setting the selected
     * @param selectedGameViewModel
     */
    public void setSelectedGameProperty(SimpleGameViewModel selectedGameViewModel)
    {
        this.selectedGameProperty.set(selectedGameViewModel);
    }

    public String getName()
    {
        return name.get();
    }

    public void setName(String name)
    {
        this.name.set(name);
    }

    public StringProperty nameProperty()
    {
        return name;
    }

    public StringProperty consoleProperty()
    {
        return console;
    }

    public StringProperty producerProperty()
    {
        return producer;
    }

    public StringProperty esrbProperty()
    {
        return esrb;
    }

    public StringProperty errorProperty()
    {
        return error;
    }

    public void setEsrb(String esrb)
    {
        this.esrb.set(esrb);
    }

    public void setConsole(String console)
    {
        this.console.set(console);
    }

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

    public void editGame()
    {
        try
        {
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
