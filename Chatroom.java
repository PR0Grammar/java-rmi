import java.rmi.RemoteException;
import java.util.ArrayList;

// The remote object used by the client

public class Chatroom implements ChatroomIntf {
    public ArrayList<String> clients = new ArrayList<>();

    public void broadcastToAll(String m) throws RemoteException{
        
    }

    public void welcome(String clientName) throws RemoteException{

    }

    public void clientToAll(String m) throws RemoteException{

    }

    public void removeClient(Client c) throws RemoteException{

    }
}
