import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/* 
 * The following is the server which takes the packets arriving from the 
 * client and simultaneously writes it in the output file. The path where you
 * want the file to be saved and stored has to given through the command line
 * argument. It receives packet of size 10242 from the server.
 * 
 * It takes out the last two bytes as the sequence number and acknowledgement
 * number from the packet received and prints the acknowledgement number
 * and sequence number.
 * 
 * Author :   Neha Upadhyay    nxu3128@rit.edu
 */

public class ServerProtocol {
	
	public static void main(String[] args)
	{
		
		try
		{
			// declares the variables to be used and the path to be taken as command line
			String path = args[0];
			String portN = "63001";
			int port = Integer.parseInt(portN);
			
			// declares the packet and the socket 
			DatagramSocket Socket = new DatagramSocket(port);
			DatagramPacket Packet;
			
			
			// receive a packet
			byte[] newData = new byte[1024];
			String receive;
			Packet = new DatagramPacket(newData, newData.length);
			Socket.receive(Packet);
			receive = new String(Packet.getData(),0,newData.length);
			String[] receiveData = receive.split("/");
			
			System.out.println("The salinity is " +receiveData[0]);
			System.out.println("The water temperature is " +receiveData[1]);
			System.out.println("The air temperature is " +receiveData[2]);
			
			int chunks = Integer.parseInt(receiveData[3].trim()) / 10240;
			
			// declares the variables and the output stream to store the incoming data from the packets
			boolean value = true;
			byte[] Data = new byte[10242];
			byte[] writeData = new byte[10240];
			int offset,length;
			OutputStream outputPic = new BufferedOutputStream(new FileOutputStream(path));
			byte[] actualData = new byte[10242];
			
			//works till the number of chunks and receives the packets.
			// simultaneously writes in the output file
			
			while(chunks >=0)
			{
				
				Packet = new DatagramPacket(Data, Data.length);
				Socket.receive(Packet);
				actualData = Packet.getData();
				for(int i = 0 ; i < 10240; i++)
				{
					writeData[i] = actualData[i];
				}
				
				// taking out the last two bytes and they are acknowledgement number and sequence number
				System.out.println("Received Acknowledgement Number is " +actualData[10240] + " Sequence Number is " +actualData[10241]);
				
				offset = Packet.getOffset();
				length = Packet.getLength() - 2;
				outputPic.write(writeData,offset,length);

				chunks = chunks - 1;
			}
		}
		
		catch(Exception e)
		{
				e.printStackTrace();
		}
		
		
	}

}
