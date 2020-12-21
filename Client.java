import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner; 
import java.rmi.server.UnicastRemoteObject;


public class Client implements ClientIntf{
    private String clientName;
    private ChatroomIntf chatroom;

    public void setName(String n){
        this.clientName = n;
    }

    public void setChatroom(ChatroomIntf c){
        this.chatroom = c;
    }
    
    public void sendMessage(String m) throws RemoteException{
        if(chatroom == null){
            throw new RemoteException("I am not apart of a Chatroom");
        }
        if(clientName == null){
            throw new RemoteException("I do not have a name yet.");
        }

        try{
            chatroom.clientToAll(clientName, m);
        }
        catch(Exception e){
            System.out.println("Client sendMessage() err: " + e);
        }
    }
    
    public void receiveMessage(String m){
        System.out.println(m);
    }

    // Requires two arguments: hostname and port
    public static void main(String[] args){
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        Scanner scan  = new Scanner(System.in);
        Client client = new Client();

        try{
            // Get registry and server stub
            Registry reg = LocateRegistry.getRegistry(host, port);
            ChatroomIntf chatroom = (ChatroomIntf) reg.lookup("Chatroom");
            
            // Ask for client's name, create client stub, and bind to registry
            System.out.println("What is your name?: ");
            String clientName = scan.nextLine();
            ClientIntf clientStub = (ClientIntf) UnicastRemoteObject.exportObject(client, 0);
            reg.bind("Client_" + clientName, clientStub);
            client.setChatroom(chatroom);
            client.setName(clientName);

            // Welcome the client to chatroom
            chatroom.welcome(clientName);


            // Allow console input until "exit"
            while(true){
                String nextMessge = scan.nextLine().trim();

                if(nextMessge.equals("exit")){
                    chatroom.removeClient(clientName);
                    reg.unbind("Client_" + clientName);
                    UnicastRemoteObject.unexportObject(client, false);
                    break;
                }

                client.sendMessage(nextMessge);
            }
            
            // Close scanner
            scan.close();
        }
        catch(Exception e){
            System.out.println("Client Error: " + e);
        }
    }
}