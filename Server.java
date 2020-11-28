import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends Chatroom{
    public static void main(String args[]){
        String _host = args[0];
        int port = Integer.parseInt(args[1]);

        try{
            // The remote object used by client
            Chatroom c = new Chatroom();
            // Create stub
            Chatroom chatroomStub = (Chatroom) UnicastRemoteObject.exportObject(c, 0);
            // Create registry and bind remote object
            Registry reg = LocateRegistry.createRegistry(port);
            reg.bind("Chatroom", chatroomStub);
            
            System.out.println("Chatroom Server Ready");
        }
        catch(Exception e){
            System.err.println("Server error: " + e);
        }
    }
}
