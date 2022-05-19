package viewmodel;

import mediator.RemoteModel;

import java.rmi.RemoteException;

public class ViewModelFactory
{

    private final LoginViewModel loginViewModel;
    private final SignupViewModel signupViewModel;
    private final UserProfileViewModel userProfileViewModel;
    private final AdminViewModel adminViewModel;
    private final BrowseViewModel browseViewModel;
    private final InventoryViewModel inventoryViewModel;
    private final GameInfoViewModel gameInfoViewModel;
    private final AddEditGameViewModel addEditGameViewModel;
    private final UserListViewModel userListViewModel;
    private final UserEditViewModel userEditViewModel;
    private final BalanceViewModel balanceViewModel;
    private final TransactionViewModel transactionViewModel;

    //  private ChatViewModel chatViewModel;

    public ViewModelFactory(RemoteModel model) throws RemoteException
    {
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
        balanceViewModel = new BalanceViewModel(model);
        transactionViewModel = new TransactionViewModel(model);
    }

    //  public ChatViewModel getChatViewModel()
    //  {
    //    return chatViewModel;
    //  }
    //
    public LoginViewModel getLoginViewModel()
    {
        return loginViewModel;
    }

    public SignupViewModel getSignupViewModel()
    {
        return signupViewModel;
    }

    public UserProfileViewModel getUserProfileViewModel()
    {
        return userProfileViewModel;
    }

    public AdminViewModel getAdminViewModel()
    {
        return adminViewModel;
    }

    public BrowseViewModel getBrowseViewModel()
    {
        return browseViewModel;
    }

    public InventoryViewModel getInventoryViewModel()
    {
        return inventoryViewModel;
    }

    public GameInfoViewModel getGameInfoViewModel()
    {
        return gameInfoViewModel;
    }

    public AddEditGameViewModel getAddEditGameViewModel()
    {
        return addEditGameViewModel;
    }

    public UserListViewModel getUserListViewModel()
    {
        return userListViewModel;
    }

    public UserEditViewModel getUserEditViewModel()
    {
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
