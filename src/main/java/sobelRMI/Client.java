package sobelRMI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Client{
	/**
	 * 
	 */
	private static Scanner sc = new Scanner(System.in);
	private static int _CORTES;
	private static Long startTime, finalTime;
	private static String _SERVER = "localhost";
	private static int _PORT = 8000;
	private static BufferedImage result;
	
	private static String generateRoute(String route) {
		String routeParts[] = route.split("/");
		String result = "";	
			for(int i=0; i<(routeParts.length-1);i++) {
				result = result + routeParts[i] + "/";
			}
		if (!result.isEmpty()) {
			result += "sobel.JPG";
		}
		
		return result;
	}
	

	public static void main(String[] args) {
		try {
			System.out.println("----- SOBEL FILTER RMI -------");
			
			Registry clientRMI = LocateRegistry.getRegistry(_SERVER,_PORT);
			ISobel isobel = (ISobel) clientRMI.lookup("service");
			System.out.println("Client RMI started");
			System.out.println("-----------------------------------------");
			System.out.println("Insert image to apply Sobel filter (Route)");
			String route = sc.nextLine();
			System.out.println("Insert numbers of cuts");
			_CORTES=sc.nextInt();
				while (route.isEmpty()){
					System.out.println("Please insert a file");
					route = sc.nextLine();
				}
			
			File file = new File(route);
			BufferedImage image = ImageIO.read(file);
			System.out.println("Image upload successfully");
			System.out.println("Your image will be cut into " + _CORTES + " parts");
			System.out.println("Sobel filter in progress..");
			
			result = isobel.applyFilter(image, _CORTES);
		}catch (Exception e) {
			e.printStackTrace();
		}

}
}