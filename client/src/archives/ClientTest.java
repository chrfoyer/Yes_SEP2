package archives;

import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Kill me please
 */
public class ClientTest {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    RmiClient client = null;
    try {
      client = new RmiClient();
    } catch (RemoteException e2) {
      // TODO Auto-generated catch block
      e2.printStackTrace();
    }
    client.login();
  }
}
