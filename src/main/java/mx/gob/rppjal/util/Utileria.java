package mx.gob.rppjal.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class Utileria {

	public static String guardarArchivo(MultipartFile multiPart, String ruta) {
		
		// Obtenemos el nombre original del archivo.
		String nombreOriginal = multiPart.getOriginalFilename();
		// Generamos cadena de 8 caracteres aleatorios y añadimos el nombre del archivo remplazando los espacios por un guion medio
		String nombreFinal = randomAlphaNumeric(8) + nombreOriginal.replace(' ', '-');
		try {
			
			// Formamos el nombre del archivo para guardarlo en el disco duro.
			File imageFile = new File(ruta + nombreFinal);
			System.out.println("Archivo: " + imageFile.getAbsolutePath());
			
			// Guardamos fisicamente el archivo en HD.
			multiPart.transferTo(imageFile);
			
			return nombreFinal;
		} catch (IOException e) {
			e.printStackTrace(System.err);
			return null;
		}
	}
	
	/**
	 * Metodo para generar una cadena aleatoria de longitud N
	 * @param count
	 * @return
	 */
	public static String randomAlphaNumeric(int count) {
		String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		while(count-- != 0) {
			int character = (int) (Math.random() * CARACTERES.length());
			builder.append(CARACTERES.charAt(character));
		}
		return builder.toString();
	}
}
