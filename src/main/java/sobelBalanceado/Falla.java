package sobelBalanceado;

import java.awt.image.BufferedImage;

public class Falla {

	private BufferedImage imagen;
	private int id;
	public Falla(BufferedImage imagen, int id) {
		super();
		this.imagen = imagen;
		this.id = id;
	}
	public BufferedImage getImagen() {
		return imagen;
	}
	public int getId() {
		return id;
	}
	
	
}
