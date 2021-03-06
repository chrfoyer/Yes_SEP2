import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import mediator.CurrentlyLoggedUser;
import mediator.RemoteModel;
import view.ViewHandler;
import viewmodel.ViewModelFactory;

import java.rmi.Naming;
import java.util.Optional;


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
     *
     * @param primaryStage Stage
     */
    public void start(Stage primaryStage)
    {
        try
        {
            RemoteModel server = null;
            String ip = "localhost";

            TextInputDialog inputDialog = new TextInputDialog("");
            inputDialog.setContentText("IP Address: ");
            inputDialog.setHeaderText("Please enter the IP address of the server you wish to connect to!\n" +
                    "You can also just enter 'localhost' to connect to your computer!");

            inputDialog.showAndWait();
            try
            {
                ip = inputDialog.getEditor().getText();
                if (ip.equals(""))
                {
                    throw new IllegalStateException("No ip address defined!");
                }
                server = (RemoteModel) Naming.lookup("rmi://" + ip + ":1099/Games");
            } catch (Exception ex)
            {
                System.err.println(ex.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Server connection not detected!\nInvalid ip address provided or server not running, please restart!");
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
