import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Iperfer {

	public static void main(String[] args) throws IOException {

//				if(args.length != 3) {
//					System.err.println("Not enough arguments");
//					System.exit(1);
//				}


		String hostName = "10.0.0.1";
		int portNumber = 5001;
		long time = 20;


		if(portNumber>1024 && portNumber < 65535) {
			client(hostName, portNumber, time);
		}else {
			System.err.print("Error: port number is not between 1025 and 65535");
			System.exit(1);
		}
	}

	public static void client(String hostName, int portNumber, long time){

		try (
				Socket tcpSocket = new Socket(hostName, portNumber);
				PrintWriter out =
						new PrintWriter(tcpSocket.getOutputStream(), true);
				) {
			long totalTime = (long) (time*Math.pow(10,9));
			long startTime = System.nanoTime();
			boolean toFinish = false;
			long totalNumberOfBytes = 0;
			while(!toFinish){
				byte[] dataChunk = new byte[1000];
				totalNumberOfBytes+=(long)1000;
				Arrays.fill(dataChunk, (byte)0);
				out.println(dataChunk);
				toFinish = (System.nanoTime() - startTime >= totalTime);
			}
			int sentInKB = (int) (totalNumberOfBytes/1024);
			double rate = ((totalNumberOfBytes/(long)Math.pow(2,20 )/time)*.1);
			System.out.print("sent= "+sentInKB+" KB rate= "+rate+" Mbps");
		}  catch (IOException e) {
			System.err.println("Cant connection to " +
					hostName);
			System.exit(1);
		} 
	}
}
