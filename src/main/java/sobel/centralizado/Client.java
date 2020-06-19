package sobel.centralizado;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Client {

	
	private static Scanner sc = new Scanner(System.in);
	private static int _CORTES =1;
	private static Long startTime, finalTime;
	
	private static String generateRoute(String route) {
		String routeParts[] = route.split("/");
		String result = "";	
			for(int i=0; i<(routeParts.length-1);i++) {
				result = result + routeParts[i] + "/";
			}
		if (!result.isEmpty()) {
			result += "sobel2.JPG";
		}
		
		return result;
	}
	
	
	public static void main(String[] args)  {
		
		try {
			System.out.println("----- SOBEL FILTER LOCAL -------");
			System.out.println("Insert image to apply Sobel filter (Route)");
			String route = sc.nextLine();
				while (route.isEmpty()){
					System.out.println("Please insert a file");
					route = sc.nextLine();
				}
			
			File file = new File(route);
			BufferedImage image = ImageIO.read(file);
			System.out.println("Image upload successfully");
			System.out.println("Sobel filter in progress..");
			
			startTime = System.nanoTime();
			ImageManipulation imageManipulation = new ImageManipulation(image, _CORTES);
			ArrayList<BufferedImage> imageParts = imageManipulation.cutImage();
			ArrayList<BufferedImage> imageParts_with_Sobel = new ArrayList<BufferedImage>();
			
				for(BufferedImage img : imageParts) {
					Sobel sobel = new Sobel(img);
					imageParts_with_Sobel.add(sobel.applyFilter());
				}
			
				BufferedImage result = imageManipulation.joinImage(imageParts_with_Sobel);
			
			File fileResult = new File(generateRoute(route));
			ImageIO.write(result, "JPG", fileResult);
			finalTime = System.nanoTime();
			
			System.out.println("Done! Your file is located at " + generateRoute(route));
			System.out.println("Processing time: " + ((finalTime-startTime)/1000000) + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
