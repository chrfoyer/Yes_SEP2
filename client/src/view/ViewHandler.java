package view;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import viewmodel.ViewModelFactory;

/**
 * class that handles swapping from view to view
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class ViewHandler extends ViewCreator
{
    private final ViewModelFactory viewModelFactory;
    private Scene currentScene;
    private Stage primaryStage;

    /**
     * method for setting the viewmodelfactory variable to the parameter viewmodelfactory
     *
     * @param viewModelFactory viewModelFactory to set the variable to
     */
    public ViewHandler(ViewModelFactory viewModelFactory)
    {
        this.viewModelFactory = viewModelFactory;
    }

    /**
     * methodd to start the view
     *
     * @param primaryStage what you set the primary stage variable to
     */
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        this.currentScene = new Scene(new Region());
        this.primaryStage.setResizable(false);

        openView("LoginView.fxml");
    }

    /**
     * method for opening views
     *
     * @param id id of the view that will be opened
     */
    public void openView(String id)
    {
        Region root = getViewController(id).getRoot();
        // getViewController(id).reset();
        currentScene.setRoot(root);
        String title = "";
        if (root.getUserData() != null)
        {
            title += root.getUserData();
        }
        primaryStage.setTitle(title);
        primaryStage.setScene(currentScene);
        primaryStage.setWidth(root.getPrefWidth());
        primaryStage.setHeight(root.getPrefHeight());
        primaryStage.show();

    }

    /**
     * method for initializing variables to the parameter variables
     *
     * @param controller what the viewController will be set to
     * @param root       what the root will be set to
     */
    @Override
    protected void initViewController(ViewController controller,
                                      Region root)
    {
        controller.init(this, viewModelFactory, root);
    }
}
