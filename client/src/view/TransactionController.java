package view;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import viewmodel.SimpleTransactionViewModel;
import viewmodel.TransactionViewModel;

/**
 * controller for the Transaction View
 */
public class TransactionController extends ViewController
{
    public TableView<SimpleTransactionViewModel> table;
    public TableColumn<SimpleTransactionViewModel, String> userColumn;
    public TableColumn<SimpleTransactionViewModel, String> actionColumn;
    public TableColumn<SimpleTransactionViewModel, String> dateColumn;
    public Label error;
    private TransactionViewModel viewModel;

    /**
     * method initializing all variables
     */
    @Override
    protected void init()
    {
        userColumn.setCellValueFactory(
                cellData -> cellData.getValue().userProperty());
        actionColumn.setCellValueFactory(
                cellData -> cellData.getValue().typeProperty());
        dateColumn.setCellValueFactory(
                cellData -> cellData.getValue().dateProperty().asString());
        viewModel = getViewModelFactory().getTransactionViewModel();

        table.setItems(viewModel.getData());
        error.textProperty().bind(viewModel.getErrorLabel());
        viewModel.reset();

    }

    /**
     * logic for back button, opening Admin View
     */
    public void back()
    {
        getViewHandler().openView("AdminView.fxml");
    }
}
