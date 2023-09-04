import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;

public class Peer
{

	public static void main(String[] args)
	{
		try
		{
			Scanner kbd = new Scanner(System.in);
			String UserInput = "";
			String PrintData = "";
			int msgnumber = 0;
			ArrayList<String> msglist = new ArrayList<>();
			ArrayList<String> msgtimelist = new ArrayList<>();
			Calendar date = null;
			SimpleDateFormat msgtimeformat = new SimpleDateFormat("E MM/dd/yyyy hh:mm:ss a");
			String msgtime = "";
			String ack = "Here is an acknowledgement proving the connection is stable.";
			

			
			
			
			System.out.println("Hello, and welcome to Peer.Java!" + 
					"\nThe purpose of this program is to demonstrate how a "
					+ "real time Peer to Peer chat application would work on "
					+ "a LAN over two different devices." + "\n A connection will now be established with HostPeer.Java");
			
			//connect to peer
			System.out.println("Connecting to HostPeer.Java to write data to HostPeer.Java...");
			Socket WriteSocket = new Socket("127.0.0.1", 6667);
			System.out.println("Connected to HostPeer.Java!");
			
			//reserve port for peer to connect to you
			System.out.println("Reserving port 6666 to read data from HostPeer.Java...");
			ServerSocket ReadSocket = new ServerSocket(6666);
			
			//peer connects to this program
			Socket ReadConnection = ReadSocket.accept();
			System.out.println("HostPeer.Java is now connected to you!");
			
			//read and write data from peer
			BufferedReader Input = new BufferedReader(new InputStreamReader(ReadConnection.getInputStream()));
			DataOutputStream Output = new DataOutputStream(WriteSocket.getOutputStream());
			
			//acknowledgement data on this output stream
			DataOutputStream Ack = new DataOutputStream(WriteSocket.getOutputStream());

			System.out.println("Now that the connection has been established there are a few commands that are available to use."
								+ "\n'inbox' will show you how many messages you have and you can pick which one you'd like to see." +
								"\n'test' will test your connection and you will receive an acknowledgement from your peer that the connection is stable." +
								"\n'exit' will close the connections and terminate the program.");
			
			//keep connection established unless told to exit
			while (!UserInput.equals("exit"))
			{
				//if user wants to view inbox
				if(UserInput.equals("inbox"))
				{
					
					System.out.println("You have: " + msglist.size() + " message(s)" + 
									   "\nInput a number for the message you would like to see." 
										+ "\nYour first message will be under '0'");
					
					msgnumber = kbd.nextInt();
					System.out.println("On [" + msgtimelist.get(msgnumber) +"] " +"Peer said: " + msglist.get(msgnumber));

				}
				//when test request is received
				else if (PrintData.equals("test"))
		        {
		            System.out.println("A test request has been received please press enter twice to start the connection test.");  
					UserInput = kbd.nextLine();
		            Ack.writeBytes(ack);
					System.out.println("Connection test successful and stable.");
		        }
				//what happens when no commands are entered
				else
				msgtime = msgtimeformat.format(Calendar.getInstance().getTime());
				System.out.println("[" + msgtime + "]" +" Peer: " + PrintData);
				msglist.add(PrintData);
				msgtimelist.add(msgtime);
				UserInput = kbd.nextLine();
				Output.writeBytes(UserInput + "\n");
				PrintData = Input.readLine();
			}
			System.out.println("Closing all sockets and connections.");
			Output.close();
			Input.close();
			WriteSocket.close();
			ReadSocket.close();
			kbd.close();
		}
		
		catch(Exception exc)
		{
			System.out.println("Error is : " + exc.toString());
		}

	}

}