import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner; 
import java.rmi.server.UnicastRemoteObject;


public class Client implements ClientIntf{
    public static void main(String[] args){
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        Scanner scan  = new Scanner(System.in);
        Client client = new Client();

        try{
            // Get registry and remote object
            Registry reg = LocateRegistry.getRegistry(host, port);
            ChatroomIntf chatroom = (ChatroomIntf) reg.lookup("Chatroom");
            
            // Ask for client's name, create client stub, and bind to registry
            System.out.println("What is your name?: ");
            String clientName = scan.nextLine();
            Client clientStub = (Client) UnicastRemoteObject.exportObject(client);
            reg.bind("Client_" + clientName, clientStub);
            chatroom.welcome(clientName);

            // Allow console input until "exit"
            while(true){}

        }
        catch(Exception e){
            System.out.println("Client Error: " + e);
        }
    }
}