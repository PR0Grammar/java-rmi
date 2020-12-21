import java.rmi.RemoteException;
import java.util.ArrayList;

import java.rmi.registry.Registry;

// The remote object used by the client

public class Chatroom implements ChatroomIntf {
    public ArrayList<String> clients = new ArrayList<>();
    public int idCounter = 0;
    private Registry reg;

    public void setRegistry(Registry r){
        this.reg = r;
    }

    // Chatroom broadcast to all clients
    public void broadcastToAll(String m) throws RemoteException{
        if(reg == null){
            throw new RemoteException("Registry is not provided");
        }

        try{
            // Get all clients -> do this
            for(String clientName: clients){
                ClientIntf client = (ClientIntf) reg.lookup("Client_" + clientName);
                client.receiveMessage("Chatroom_Server: " + m);
            }
        }
        catch(Exception e){
            System.out.println("Chatroom Error: " + e);
        }
    }

    public void welcome(String clientName) throws RemoteException{
        clients.add(clientName);

        System.out.println(clientName + " just joined the Chatroom.");
        System.out.println(clientName + " has been assigned id = " + (++idCounter));
        broadcastToAll(clientName + " has joined the Chatroom.");
    }

    // Send message to all other clients except to the method invoker
    public void clientToAll(String clientName, String m) throws RemoteException{
        if(reg == null){
            throw new RemoteException("Registry is not provided");
        }

        try{
            for(String cn: clients){
                if(cn.equals(clientName)) continue;

                ClientIntf client = (ClientIntf) reg.lookup("Client_" + cn);
                client.receiveMessage(clientName + ": " + m);
            }
        }
        catch(Exception e){
            System.out.println("Chatroom Error: " + e);
        }
    }

    // Remove a client
    public void removeClient(String clientName) throws RemoteException{
        if(reg == null){
            throw new RemoteException("Registry is not provided");
        }

        System.out.println(clientName + " has left the Chatroom.");
        broadcastToAll(clientName + " has left the Chatroom.");
        clients.remove(clientName);
    }
}
