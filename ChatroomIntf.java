import java.rmi.RemoteException;
import java.rmi.Remote;

// The remote interface. All methods that can be invoked by the client

public interface ChatroomIntf extends Remote{
    public void broadcastToAll(String m) throws RemoteException;
    public void welcome(String clientName) throws RemoteException;
    public void clientToAll(String m) throws RemoteException;
    public void removeClient(Client c) throws RemoteException;
}
