package util;

import java.io.*;
import java.net.*;

/*
	This class intend to simulate the Slow Loris attack
	it tries to send multiple TCP request to exhaust the server side
	in a very simple example.
*/
public class DosAtack
{
	public void callServer() 
	{
        Socket socket = null;  
        DataOutputStream outputStream = null;
        DataInputStream inputStream = null;

        try 
		{
            socket = new Socket("172.21.209.71", 2000);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
        } 
		catch (UnknownHostException e) 
		{
            System.err.println("Don't know about host");
        } 
		catch (IOException e) 
		{
            System.err.println("Couldn't get I/O for the connection to: hostname");
        }

		if (socket != null && outputStream != null && inputStream != null) 
		{
			try 
			{
				outputStream.writeBytes("DOS Atack\n");  
	
				
				String responseLine;
				while ((responseLine = inputStream.readLine()) != null) 
				{
					System.out.println("Server: " + responseLine);
					if (responseLine.indexOf("Ok") != -1) 
					{
						break;
					}
				}
				
				System.out.println("Conexao fechou");
				outputStream.close();
				inputStream.close();
				socket.close();   
			} 
			catch (UnknownHostException e) 
			{
				System.err.println("Trying to connect to unknown host: " + e);
			} 
			catch (IOException e) 
			{
				System.err.println("IOException:  " + e);
			}
		}
		
	}
	
	public static void main(String[] args) 
	{
        int i=0;
        
        DosAtack atacker = new DosAtack();
        
        while(i < 1) 
        {
        	i++;
        	atacker.callServer();
        }
    }           
}
