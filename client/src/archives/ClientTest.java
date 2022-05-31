package archives;

import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * @deprecated class used to connect to the server in the early stages of the project
 */
@SuppressWarnings("JavaDoc")
public class ClientTest
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        RmiClient client = null;
        try
        {
            client = new RmiClient();
        } catch (RemoteException e2)
        {
            e2.printStackTrace();
        }
        client.login();
    }
}
