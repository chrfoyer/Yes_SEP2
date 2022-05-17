package view;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import viewmodel.SimpleTransactionViewModel;

import java.time.LocalDate;

public class TransactionController extends ViewController
{
  public TableView<SimpleTransactionViewModel> table;
  public TableColumn<SimpleTransactionViewModel, Double> amountColumn;
  public TableColumn<SimpleTransactionViewModel, String> userColumn;
  public TableColumn<SimpleTransactionViewModel, String> typeColumn;
  public TableColumn<SimpleTransactionViewModel, LocalDate> dateColumn;
  public Label error;

  @Override protected void init()
  {

  }

  public void back(ActionEvent actionEvent)
  {
    getViewHandler().openView("AdminView.fxml");
  }
}
