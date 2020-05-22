package sobelRMI;

import java.awt.image.BufferedImage;
import java.rmi.Remote;

public interface ISobel extends Remote{

	public BufferedImage applyFilter(BufferedImage image, int _PARTS) throws java.rmi.RemoteException;
}
