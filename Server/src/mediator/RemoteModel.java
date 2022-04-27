package mediator;

import java.rmi.Remote;

public interface RemoteModel extends Remote
{
  public void rentGame();
  public String viewGames();
}
