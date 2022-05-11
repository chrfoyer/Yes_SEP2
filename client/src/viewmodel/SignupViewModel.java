package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import mediator.RemoteModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @version 0.1
 */
public class SignupViewModel
{
  private RemoteModel model;
  private StringProperty usernameProperty;
  private StringProperty passwordProperty;
  private Label errorLabel;

  public SignupViewModel(RemoteModel model)
  {
    this.model = model;
    usernameProperty = new SimpleStringProperty();
    passwordProperty = new SimpleStringProperty();
    errorLabel = new Label();
  }

  public StringProperty getUsernameProperty()
  {
    return usernameProperty;
  }

  public StringProperty getPasswordProperty()
  {
    return passwordProperty;
  }

  public void login()
  {
    try
    {
      // TODO: 2022. 05. 11. Model logic validate signup
    }
    catch (Exception e)
    {
      errorLabel.setText(e.getMessage());
    }
  }

  public void reset()
  {
    usernameProperty.set("");
    passwordProperty.set("");
    errorLabel.setText("");
  }
}
