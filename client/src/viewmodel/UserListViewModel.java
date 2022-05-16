package viewmodel;

import Model.Game;
import Model.User;
import Model.UserList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.RemoteModel;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class UserListViewModel {
  private ObservableList<SimpleUserViewModel> list;
  private ObjectProperty<SimpleUserViewModel> selectedUser;
  private StringProperty error;
  private RemoteModel model;

  public UserListViewModel(RemoteModel model) throws RemoteException {
    list = FXCollections.observableArrayList();
    selectedUser = new SimpleObjectProperty<>();
    error = new SimpleStringProperty();
    this.model = model;
    reset();
  }

  public void reset() {
    fillTable();
  }

  public void fillTable() {
    try {
      list.clear();
      ArrayList<User> users = model.getUserList().getUsers();
      for (User user : users) {
        SimpleUserViewModel temp = new SimpleUserViewModel(user);
        list.add(temp);
      }
    } catch (Exception e) {
      error.set(e.getMessage());
    }
  }

  public ObservableList<SimpleUserViewModel> getUsers() {
    return list;
  }

  public SimpleUserViewModel getSelectedUser() {
    return selectedUser.get();
  }

  public void setSelectedUser(SimpleUserViewModel selectedUser) {
    this.selectedUser.set(selectedUser);
  }

  public StringProperty errorProperty() {
    return error;
  }

  public String getError() {
    return error.get();
  }
}
