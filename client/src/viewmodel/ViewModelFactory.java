package viewmodel;

import mediator.RemoteModel;


/**
 * The viewModel creator following the Factory design pattern
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 *
 */
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

    /**
     * Instantiates a new View model factory.
     *
     * @param model the model
     */
    public ViewModelFactory(RemoteModel model)
    {
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

    /**
     * Gets login view model.
     *
     * @return the login view model
     */
    public LoginViewModel getLoginViewModel()
    {
        return loginViewModel;
    }

    /**
     * Gets signup view model.
     *
     * @return the signup view model
     */
    public SignupViewModel getSignupViewModel()
    {
        return signupViewModel;
    }

    /**
     * Gets user profile view model.
     *
     * @return the user profile view model
     */
    public UserProfileViewModel getUserProfileViewModel()
    {
        return userProfileViewModel;
    }

    /**
     * Gets admin view model.
     *
     * @return the admin view model
     */
    public AdminViewModel getAdminViewModel()
    {
        return adminViewModel;
    }

    /**
     * Gets browse view model.
     *
     * @return the browse view model
     */
    public BrowseViewModel getBrowseViewModel()
    {
        return browseViewModel;
    }

    /**
     * Gets inventory view model.
     *
     * @return the inventory view model
     */
    public InventoryViewModel getInventoryViewModel()
    {
        return inventoryViewModel;
    }

    /**
     * Gets game info view model.
     *
     * @return the game info view model
     */
    public GameInfoViewModel getGameInfoViewModel()
    {
        return gameInfoViewModel;
    }

    /**
     * Gets add edit game view model.
     *
     * @return the add edit game view model
     */
    public AddEditGameViewModel getAddEditGameViewModel()
    {
        return addEditGameViewModel;
    }

    /**
     * Gets user list view model.
     *
     * @return the user list view model
     */
    public UserListViewModel getUserListViewModel()
    {
        return userListViewModel;
    }

    /**
     * Gets user edit view model.
     *
     * @return the user edit view model
     */
    public UserEditViewModel getUserEditViewModel()
    {
        return userEditViewModel;
    }

    /**
     * Gets balance view model.
     *
     * @return the balance view model
     */
    public BalanceViewModel getBalanceViewModel()
    {
        return balanceViewModel;
    }

    /**
     * Gets transaction view model.
     *
     * @return the transaction view model
     */
    public TransactionViewModel getTransactionViewModel()
    {
        return transactionViewModel;
    }
}
