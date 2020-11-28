import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientIntf extends Remote {
    public void setName(String s) throws RemoteException;
    public void setChatroom(ChatroomIntf c) throws RemoteException;
    public void sendMessage(String m) throws RemoteException;
    public void receiveMessage(String m) throws RemoteException;
}
