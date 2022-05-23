package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import viewmodel.SimpleUserViewModel;
import viewmodel.UserEditViewModel;
import viewmodel.UserListViewModel;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * controller for the User List View
 */
public class UserListViewController extends ViewController
{
    @FXML
    public TableColumn<SimpleUserViewModel, String> usernameColumn;
    @FXML
    public TableColumn<SimpleUserViewModel, LocalDate> birthdayColumn;
    @FXML
    public TableColumn<SimpleUserViewModel, String> emailColumn;
    @FXML
    public TableView<SimpleUserViewModel> table;
    @FXML
    public Label error;
    private UserListViewModel viewModel;
    private UserEditViewModel editViewModel;

    /**
     * method for initializing all the variables and binding them
     */
    @Override
    protected void init()
    {
        viewModel = getViewModelFactory().getUserListViewModel();
        usernameColumn.setCellValueFactory(
                cellData -> cellData.getValue().usernameProperty());
        birthdayColumn.setCellValueFactory(
                cellData -> cellData.getValue().bdayProperty());
        emailColumn.setCellValueFactory(
                cellData -> cellData.getValue().emailProperty());
        table.setItems(viewModel.getUsers());
        error.textProperty().bind(viewModel.errorProperty());

        editViewModel = getViewModelFactory().getUserEditViewModel();

        table.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> editViewModel.setSelectedUserProperty(newV));
        reset();
    }

    /**
     * method for resetting the viewmodel
     */
    public void reset()
    {
        viewModel.reset();
    }

    /**
     * logic for remove button, removing a user
     * @throws RemoteException Thrown when an error occurs
     */
    @FXML
    public void remove() throws RemoteException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this user? ->" +editViewModel.getUser());
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK)
        {
            viewModel.setSelectedUser(editViewModel.getUser());
            viewModel.removeUser();
            viewModel.reset();
        }
    }

    /**
     * method for opening UserEditView
     */
    @FXML
    public void edit()
    {
        // Must use the selected user
        getViewModelFactory().getUserEditViewModel().reset();
        getViewHandler().openView("UserEditView.fxml");
    }

    /**
     * logic for back button, opening the Admin View
     */
    public void back()
    {
        getViewHandler().openView("AdminView.fxml");
    }
}
