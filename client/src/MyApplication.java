import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import mediator.CurrentlyLoggedUser;
import mediator.RemoteModel;
import view.ViewHandler;
import viewmodel.ViewModelFactory;

import java.rmi.Naming;


/**
 * Class to start the GUI and connect to the server
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class MyApplication extends Application
{
    /**
     * Starts the GUI and creates server connection
     * @param primaryStage Stage
     */
    public void start(Stage primaryStage)
    {
        try
        {
            RemoteModel server = null;
            try
            {
                server = (RemoteModel) Naming.lookup("rmi://localhost:1099/Games");
            } catch (Exception ex)
            {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Server connection not detected, please restart server");
                Platform.runLater(alert::showAndWait);
            }
            // The model is now responsible for creating the client object

            ViewModelFactory viewModelFactory = new ViewModelFactory(server);
            ViewHandler view = new ViewHandler(viewModelFactory);

            CurrentlyLoggedUser.setModel(server);

            view.start(primaryStage);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
