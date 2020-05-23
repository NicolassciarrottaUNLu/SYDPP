package sobelRMI;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Imagen implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte[] byteImage;
	private int _CORTES;
	
	public Imagen (BufferedImage image, int _CORTES) {
		this.byteImage = buffImgToByteArr(image);
		this._CORTES=_CORTES;
	}
	
	public static byte[] buffImgToByteArr(BufferedImage img) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		BufferedImage bImage = img;
		try {
			ImageIO.write(bImage, "jpg", bos);
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Imagen ByteArrToImagenObj(byte[] data) {
		try {
			ByteArrayInputStream bs = new ByteArrayInputStream(data); 
			ObjectInputStream is = new ObjectInputStream(bs);
			Imagen img = (Imagen)is.readObject();
			is.close();
			return img;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public BufferedImage getImage() {
		ByteArrayInputStream bis = new ByteArrayInputStream(this.byteImage);
		try {
			return ImageIO.read(bis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int get_CORTES() {
		return _CORTES;
	}
	 
}
