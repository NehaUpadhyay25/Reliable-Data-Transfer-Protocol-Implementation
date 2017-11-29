import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;
import java.util.Scanner;

/* 
 * The following is the Client class which converts the given picture, 
 * taken through the command line argument, in the byte array and stores 
 * it in an array. The array is later on used to transfer the bytes to 
 * the server and then the server then displays the image. It sends the 
 * packets in chunks each of size 10242 bytes.
 * 
 * The last two bytes are acknowledgement number and the sequence number.
 * 
 * Author :   Neha Upadhyay    nxu3128@rit.edu
 */


public class ClientProtocol {
	
	public static void main(String[] args)
	{
		String path = args[0];
		int bytes = 0;
		byte[] sendBytes = new byte[10240];
		int port = 63001;
		Scanner sc = new Scanner(System.in);
		String result , newResult = "";
		Random rand = new Random();
		int value = 0;
		
		try
		{
			// creates variables for packets and sockets
			// to get the salinity
			System.out.println("Do you want to measure salinity(y/n)?");
			result = sc.nextLine();
			
			if(result.equals("y"))
			{
				value = rand.nextInt(100);
				newResult = String.valueOf(value) + "/" ;

			}
			else
			{
				System.out.println("salinity not measured");
				newResult = newResult + "n" + "/";
			}
			
			
			// to get the water temperature
			System.out.println("Do you want to measure water temperature(y/n)?");
			result = sc.nextLine();
			
			if(result.equals("y"))
			{
				value = rand.nextInt(100);
				newResult = newResult + String.valueOf(value) + "/" ;
			}
			else
			{
				System.out.println("salinity not measured");
				newResult = newResult + "n" + "/";
			}
			

			// to get the air temperature
			System.out.println("Do you want to measure air temperature(y/n)?");
			result = sc.nextLine();
						
			if(result.equals("y"))
			{
				value = rand.nextInt(100);
				newResult = newResult + String.valueOf(value) + "/";
			}
			else
			{
				System.out.println("salinity not measured");
				newResult = newResult + "n" + "/";

			}
						

			// initializes variables
			DatagramSocket senderSocket = new DatagramSocket();
			DatagramPacket senderPacket;
		    InetAddress address = InetAddress.getByName("localhost");
		    
		    // operations performed on the picture given by the command line
		    File openFile = new File(path);
		    
		    // picture taken as input stream and converted into byte array
		    InputStream input = new BufferedInputStream(new FileInputStream(openFile));
		    ByteArrayOutputStream stream = new ByteArrayOutputStream((int) openFile.length());
		    byte[] byteArray = new byte[(int) openFile.length()];

		    System.out.println("length of file " +byteArray.length);
		    int chunks = byteArray.length / 10240;
		    
		    // send first data 
		    newResult = newResult + String.valueOf(byteArray.length);
		    byte[] buff = new byte[1024];
		    buff = newResult.getBytes();
		    senderPacket = new DatagramPacket(buff, buff.length, address, port);
	    	senderSocket.send(senderPacket);
		    
		    // converts the picture in bytes and stores them in a byte array
		    while(bytes!= -1)
			{
				bytes = input.read(byteArray);
				if(bytes > 0)
				{
					stream.write(byteArray , 0 , bytes);
				}
			}
		    byteArray = stream.toByteArray(); 
		    byte[] sendData = new byte[10242];
		    int j = 0;
		    int ack = 0 , seq = 0;
		    
		    // send the data to the server in chunks
		    
		    while(chunks>=0)
		    {
		    	for(int i = 0 ; i <10240;i++)
		    	{
		    		// fills the byte array to be send
		    		if(j < byteArray.length)
		    		{
		    			sendData[i] = byteArray[j];
		    		}
		    		
		    		// handles exception where byte array finishes
		    		else
		    		{
		    			sendData[i] = 0;
		    		}
		    		j = j + 1;
		    	}
		    	
		    	// adding the acknowledgement number and sequence number in the byte array
		    	
		    	sendData[10240] = (byte) ack;
		    	sendData[10241] = (byte) seq;
		    	System.out.println("Sending acknowledgement = " +ack +" and sequence number = " +seq);
		    	
		    	// sends the packet to the server of size 10242 bytes. the last two bytes are acknowledgement and
		    	// sequence number
		    	
		    	ack = ack + 1;
		    	seq = seq + 1;
		    	
		    	senderPacket = new DatagramPacket(sendData, sendData.length, address, port);
		    	senderSocket.send(senderPacket);
		    	
		    	chunks  = chunks - 1;
		    	
		    }
		    
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		

	}

}
