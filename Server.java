import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends Chatroom{
    public static void main(String args[]){
        String _host = args[0];
        int port = Integer.parseInt(args[1]);

        try{
            //Create registry
            Registry reg = LocateRegistry.createRegistry(port);

            // The remote object used by client
            Chatroom c = new Chatroom();
            c.setRegistry(reg);
            
            // Create stub
            ChatroomIntf chatroomStub = (ChatroomIntf) UnicastRemoteObject.exportObject(c, 0);
            
            // Bind remote object
            reg.bind("Chatroom", chatroomStub);

            System.out.println("Chatroom Server Ready");
        }
        catch(Exception e){
            System.err.println("Server error: " + e);
        }
    }
}
