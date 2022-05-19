package view;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import viewmodel.SimpleTransactionViewModel;
import viewmodel.TransactionViewModel;

public class TransactionController extends ViewController
{
    public TableView<SimpleTransactionViewModel> table;
    public TableColumn<SimpleTransactionViewModel, Double> amountColumn;
    public TableColumn<SimpleTransactionViewModel, String> userColumn;
    public TableColumn<SimpleTransactionViewModel, String> typeColumn;
    public TableColumn<SimpleTransactionViewModel, String> dateColumn;
    public Label error;
    private TransactionViewModel viewModel;

    @Override
    protected void init()
    {
        amountColumn.setCellValueFactory(
                cellData -> cellData.getValue().amountProperty());
        userColumn.setCellValueFactory(
                cellData -> cellData.getValue().userProperty());
        typeColumn.setCellValueFactory(
                cellData -> cellData.getValue().typeProperty());
        dateColumn.setCellValueFactory(
                cellData -> cellData.getValue().dateProperty().asString());
        viewModel = getViewModelFactory().getTransactionViewModel();

        table.setItems(viewModel.getData());
        error.textProperty().bind(viewModel.getErrorLabel());
        viewModel.reset();

    }

    public void back(ActionEvent actionEvent)
    {
        getViewHandler().openView("AdminView.fxml");
    }
}
