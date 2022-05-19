package viewmodel;

import Model.User;
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

public class UserListViewModel
{
  private final ObservableList<SimpleUserViewModel> list;
  private final ObjectProperty<SimpleUserViewModel> selectedUser;
  private final StringProperty error;
  private final RemoteModel model;

  public UserListViewModel(RemoteModel model) throws RemoteException
  {
    list = FXCollections.observableArrayList();
    selectedUser = new SimpleObjectProperty<>();
    error = new SimpleStringProperty();
    this.model = model;
    reset();
  }

  public void reset()
  {
    fillTable();
  }

  public void fillTable()
  {
    try
    {
      list.clear();
      ArrayList<User> users = model.getUserList().getUsers();
      for (User user : users)
      {
        SimpleUserViewModel temp = new SimpleUserViewModel(user);
        list.add(temp);
      }
    } catch (Exception e)
    {
      error.set(e.getMessage());
    }
  }

  public ObservableList<SimpleUserViewModel> getUsers()
  {
    return list;
  }

  public SimpleUserViewModel getSelectedUser()
  {
    return selectedUser.get();
  }

  public void setSelectedUser(SimpleUserViewModel selectedUser)
  {
    this.selectedUser.set(selectedUser);
  }

  public StringProperty errorProperty()
  {
    return error;
  }

  public String getError()
  {
    return error.get();
  }

  public void removeUser() throws RemoteException
  {
    try
    {
      if (selectedUser.get().getUser().getUsername().equals(CurrentlyLoggedUser.getLoggedInUser().getUsername()))
      {
        throw new IllegalArgumentException("Congratulations you played yourself");
      }
      model.removeUser(selectedUser.get().getUser());
    } catch (Exception ex)
    {
      error.set(ex.getMessage());
    }

  }
}
