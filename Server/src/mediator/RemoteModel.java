package mediator;

import Model.Game;

import java.rmi.Remote;

public interface RemoteModel extends Remote
{
  public void rentGame(Game game);
  public String viewGames();
}
