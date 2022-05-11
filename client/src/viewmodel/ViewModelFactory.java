package viewmodel;

import mediator.RemoteModel;

import java.rmi.RemoteException;

public class ViewModelFactory
{

  private LoginViewModel loginViewModel;
  private SignupViewModel signupViewModel;

  //  private ChatViewModel chatViewModel;

  public ViewModelFactory(RemoteModel model) throws RemoteException
  {
    //    chatViewModel = new ChatViewModel(model);
    loginViewModel = new LoginViewModel(model);
    signupViewModel = new SignupViewModel(model);
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

}
