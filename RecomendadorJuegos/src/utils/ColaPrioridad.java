package utils;

import java.util.ArrayList;
import java.util.Random;

public class ColaPrioridad<T extends Comparable> {

	private Nodo<T> primero;
	private Nodo<T> ultimo;
	private int maxLength;
	private int length;
	
	public ColaPrioridad(int maxLength) {
		this.primero = null;
		this.ultimo = null;
		
		this.maxLength = maxLength;
		this.length = 0;
	}
	
	public void add(T element) {
		// La cola está vacía...
		if (primero == null) {
			primero = new Nodo<T>(element);
			ultimo = primero;
		}
		// La cola tiene al menos un elemento...
		else {
			boolean posicionEncontrada = false;
			int indice = 0;
			Nodo<T> nodoActual = primero;
			
			while (!posicionEncontrada && indice < length) {
				// Si hemos llegado al final de la cola...
				if (nodoActual == null) break;
				// Si hemos encontrado el hueco del elemento...
				if (element.compareTo(nodoActual.valor) >= 0)
					posicionEncontrada = true;
				// Si no adelantamos el nodoActual al siguiente...
				else {
					nodoActual = nodoActual.siguiente;
					indice++;
				}
			}
			
			// Si no caben más elementos en la cola y no hemos encontrado hueco...
			if (indice >= maxLength);
			// Si el elemento no tiene un hueco más que al final de la cola...
			else if (indice == length) {
				Nodo<T> nodo = new Nodo<T>(element, ultimo, null);
				ultimo.siguiente = nodo;
				ultimo = nodo;
				length++;
			}
			// Si el elemento tiene que ser el primero de la cola...
			else if (indice == 0) {
				Nodo<T> nodo = new Nodo<T>(element, null, primero);
				primero.anterior = nodo;
				primero = nodo;
				length++;
			}
			// Insertamos el elemento si hemos encontrado un hueco para él...
			else if (indice < length && posicionEncontrada) {
				Nodo<T> nodo = new Nodo<T>(element, nodoActual.anterior, nodoActual);
				nodoActual.anterior.siguiente = nodo;
				nodoActual.anterior = nodo;
				length++;
			} 
			
			// Borramos el último elemento si hemos excedido la longitud máxima...
			if (length >= maxLength) {
				ultimo.anterior.siguiente = null;
				ultimo = ultimo.anterior;
				length--;
			}
		}
	}
	
	
	@Override
	public String toString() {
		String str = "[";
		Nodo<T> nodoActual = primero;
		while (nodoActual != null) {
			str += nodoActual.valor;
			nodoActual = nodoActual.siguiente;
			if (nodoActual != null) 
				str += ", ";
		}
		str += "]";
		
		return str;
	}
	
	public ArrayList<T> toArrayList() {
		ArrayList<T> arrayList = new ArrayList<T>();
		Nodo<T> nodoActual = primero;
		while (nodoActual != null) {
			arrayList.add(nodoActual.valor);
			nodoActual = nodoActual.siguiente;
		}
		
		return arrayList;
	}
	
	public static void main(String[] args) {
		ColaPrioridad<Integer> cola = new ColaPrioridad<Integer>(10);
		Random r = new Random(System.currentTimeMillis());
		for (int i = 0; i < 100; i++) {
			Integer num = r.nextInt(10000);
			System.out.print(num + ", ");
			cola.add(num);
		}
		
		System.out.println();
		System.out.println(cola);
	}
}

class Nodo<T extends Comparable> {
	public T valor;
	public Nodo<T> anterior;
	public Nodo<T> siguiente;
	
	public Nodo(T valor) {
		this.valor = valor;
		this.anterior = null;
		this.siguiente = null;
	}
	
	public Nodo(T valor, Nodo<T> anterior, Nodo<T> siguiente) {
		this.valor = valor;
		this.anterior = anterior;
		this.siguiente = siguiente;
	}
}