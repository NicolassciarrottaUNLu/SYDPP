package sobelBalanceado;

import java.util.ArrayList;

public class Cola implements ICola{

private class Nodo {
		
		public Imagen image;
		public int id;
		public Nodo siguiente;
		
		public Nodo(Imagen image) {
			this.image = image;
			this.id=image.getId();
			this.siguiente = null;
		}
		
		public int getId() {
			return this.id;
		}
	}
	
	private Nodo cabeza,ultimo;
	
	
	
	public boolean isEmpty() {
		return (cabeza==null);
	}

	
	
	public void add(Imagen image) {
		Nodo nuevoNodo = new Nodo(image);
		if (isEmpty()) {
			cabeza = nuevoNodo;
		}else {
			ultimo.siguiente=nuevoNodo;
		}
		ultimo = nuevoNodo;
	}

	
	public void delete() {
		if (! isEmpty()) {
			Nodo eliminar = cabeza;
			cabeza = cabeza.siguiente;
			eliminar.siguiente=null;
				if (isEmpty()) {
					ultimo=null;
				}
		}
		
	}

	public ArrayList<String> getMessages (String address){
		ArrayList<String> a = new ArrayList<String>();
		if(isEmpty()) {
			a.add("No messages found");
			return a;
		}else {
			//for(int i=0; i<)
			return a;
		}
	}
	
	public Imagen getImagen(int id) {
		if (isEmpty()){
			return null;
		}else {
			if (cabeza.id==id) {
				return (cabeza.image);
			}else {
				return (null);
			}
		}
	}
	
	public void reencolar(Imagen image) {
		this.delete();
		this.add(image);
	}

	
	public boolean empty() {
		if(cabeza==ultimo) {
			return true;
		}else {
			return false;
		}
	}

	
	public int getLargo() {
		return (ultimo.id - cabeza.id);
	}
	
	public int getId() {
		return getId();
	}





}
