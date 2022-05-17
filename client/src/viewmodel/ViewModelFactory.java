package viewmodel;

import mediator.RemoteModel;

import java.rmi.RemoteException;

public class ViewModelFactory {

  private LoginViewModel loginViewModel;
  private SignupViewModel signupViewModel;
  private UserProfileViewModel userProfileViewModel;
  private AdminViewModel adminViewModel;
  private BrowseViewModel browseViewModel;
  private InventoryViewModel inventoryViewModel;
  private GameInfoViewModel gameInfoViewModel;
  private AddEditGameViewModel addEditGameViewModel;
  private UserListViewModel userListViewModel;
  private UserEditViewModel userEditViewModel;
  private BalanceViewModel balanceViewModel;
  private TransactionViewModel transactionViewModel;

  //  private ChatViewModel chatViewModel;

  public ViewModelFactory(RemoteModel model) throws RemoteException {
    //    chatViewModel = new ChatViewModel(model);
    loginViewModel = new LoginViewModel(model);
    signupViewModel = new SignupViewModel(model);
    userProfileViewModel = new UserProfileViewModel(model);
    adminViewModel = new AdminViewModel(model);
    browseViewModel = new BrowseViewModel(model);
    inventoryViewModel = new InventoryViewModel(model);
    gameInfoViewModel = new GameInfoViewModel(model);
    addEditGameViewModel = new AddEditGameViewModel(model);
    userListViewModel = new UserListViewModel(model);
    userEditViewModel = new UserEditViewModel(model);
    balanceViewModel=new BalanceViewModel(model);
    transactionViewModel=new TransactionViewModel(model);
  }

  //  public ChatViewModel getChatViewModel()
  //  {
  //    return chatViewModel;
  //  }
  //
  public LoginViewModel getLoginViewModel() {
    return loginViewModel;
  }

  public SignupViewModel getSignupViewModel() {
    return signupViewModel;
  }

  public UserProfileViewModel getUserProfileViewModel() {
    return userProfileViewModel;
  }

  public AdminViewModel getAdminViewModel() {
    return adminViewModel;
  }

  public BrowseViewModel getBrowseViewModel() {
    return browseViewModel;
  }

  public InventoryViewModel getInventoryViewModel() {
    return inventoryViewModel;
  }

  public GameInfoViewModel getGameInfoViewModel() {
    return gameInfoViewModel;
  }

  public AddEditGameViewModel getAddEditGameViewModel() {
    return addEditGameViewModel;
  }

  public UserListViewModel getUserListViewModel() {
    return userListViewModel;
  }

  public UserEditViewModel getUserEditViewModel(){
    return userEditViewModel;
  }

  public BalanceViewModel getBalanceViewModel()
  {
    return balanceViewModel;
  }

  public TransactionViewModel getTransactionViewModel()
  {
    return transactionViewModel;
  }
}
