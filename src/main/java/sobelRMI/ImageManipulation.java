package sobelRMI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class ImageManipulation {

	private BufferedImage image;
	private int cantPartes;
	
	public ImageManipulation(BufferedImage image, int cantidad) throws IOException {
		this.image = image;
		this.cantPartes = (int) Math.sqrt(cantidad);
	}
	
	public ArrayList<BufferedImage> cutImage(){
		int width = image.getWidth()-2;
		int height = image.getHeight()-2;
		
		ArrayList<BufferedImage> arrayImagen = new ArrayList<BufferedImage>();
		
		for (int i = 0; i < cantPartes; i++) {
			for (int j = 0; j < cantPartes; j++) {
				BufferedImage parte = image.getSubimage(j*(width/cantPartes),i*(height/cantPartes),width/cantPartes,height/cantPartes);
				arrayImagen.add(parte);
			}
		}
		return arrayImagen;
	}
	
	public BufferedImage joinImage(ArrayList<BufferedImage> parts) {
		/*
		 * Calculo no exacto, hay que restar las partes
		 */
		int width = image.getWidth()-cantPartes*2;
		int height = image.getHeight()-cantPartes*2;
		BufferedImage imgFinal = new BufferedImage(width, height, image.getType());
		Graphics g = imgFinal.getGraphics();
		
		int part = 0;
		for (int j = 0; j < cantPartes; j++) {
			for (int j2 = 0; j2 < cantPartes; j2++) {
					g.drawImage(parts.get(part),j2*(width/cantPartes),j*(height/cantPartes),null);
					part++;
				}
		}
		return imgFinal;
	}
}
