package view;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import viewmodel.ViewModelFactory;

public class ViewHandler extends ViewCreator
{
  private Scene currentScene;
  private Stage primaryStage;
  private ViewModelFactory viewModelFactory;

  public ViewHandler(ViewModelFactory viewModelFactory)
  {
    this.viewModelFactory = viewModelFactory;
  }

  public void start(Stage primaryStage)
  {
    this.primaryStage = primaryStage;
    this.currentScene = new Scene(new Region());
    primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue)
      {
        primaryStage.setMaximized(false);
        Alert alert = new Alert(Alert.AlertType.ERROR, "I am fine the way i am! Thank you ver much!");
        alert.showAndWait();
      }
    });
    primaryStage.iconifiedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue)
      {
        primaryStage.setIconified(false);
        Alert alert = new Alert(Alert.AlertType.ERROR,
            "N̶̾̕O̶̐͂ ̵̽̃E̵̋̀S̸̈́̊Ć̶͇Ȁ̸̔P̸̈͌Ë̴̱́\n");
        alert.setHeaderText("W̸̩̒H̸̬͌R̵̤̈́E̴̻͐ ̶̡̓ ̶̱͋Ȃ̶͕R̴̬̚E̶̦͑ ̶͉̂ ̷͕̐Y̶͖͒O̴͍̓U̸̜̐ ̸̻́ ̸̣̊G̴̡͌O̶̹͗I̶̭͂N̴̰̓G̶̝͠?̴̢̒");
        alert.showAndWait();
      }
    });
    openView("LoginView.fxml");
  }

  public void openView(String id)
  {
    Region root = getViewController(id).getRoot();
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


  @Override protected void initViewController(ViewController controller, Region root) {
    controller.init(this,viewModelFactory,root);
  }
}
