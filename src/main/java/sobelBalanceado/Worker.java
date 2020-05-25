package sobelBalanceado;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;



public class Worker implements ISobel {

	private ImageManipulation iManipulation;
	
	@Override
	public Imagen send(Imagen image) throws RemoteException {
		Imagen nueva = null;
		try {
			iManipulation = new ImageManipulation(image.getImage(),image.get_CORTES());
			ArrayList<BufferedImage> imageParts = iManipulation.cutImage();
			
			ArrayList<BufferedImage> imageParts_with_Sobel = new ArrayList<BufferedImage>();
			
			for(BufferedImage img : imageParts) {
				Sobel sobel = new Sobel(img);
				imageParts_with_Sobel.add(sobel.applyFilter());
			}
		
			BufferedImage result = iManipulation.joinImage(imageParts_with_Sobel);
			
			nueva = new Imagen (result,0);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nueva;
		
	}
	
	
	

}
