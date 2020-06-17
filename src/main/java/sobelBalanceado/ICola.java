package sobelBalanceado;

public interface ICola {
	public boolean isEmpty();
	public void add (Imagen image);
	public void delete();
	public Imagen getImagen(int id);
	
}
