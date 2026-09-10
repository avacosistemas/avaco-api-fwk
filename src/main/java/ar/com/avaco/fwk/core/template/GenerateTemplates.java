package ar.com.avaco.fwk.core.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenerateTemplates {

	private static final String ENTITY_MINUSCULA = "entityMinuscula";
	private static final String PACKAGE = "package";
	private static final String ENTITY = "entity";

	public static void main(String[] args) throws IOException {

		Map<String, String> variables = new HashMap<String, String>();
		variables.put(ENTITY, "Reclamo");
		variables.put(ENTITY_MINUSCULA, "reclamo");
		variables.put(PACKAGE, "reclamos");

		List<String> archivos = new ArrayList<>();
		archivos.add("templates/Repository.txt");
		archivos.add("templates/RepositoryCustom.txt");
		archivos.add("templates/RepositoryImpl.txt");
		archivos.add("templates/Service.txt");
		archivos.add("templates/ServiceImpl.txt");
		archivos.add("templates/EPService.txt");
		archivos.add("templates/EPServiceImpl.txt");
		archivos.add("templates/RestController.txt");

		for (String path : archivos) {

			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream is = classloader.getResourceAsStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String contenido = reader.lines().collect(Collectors.joining("\n"));
			contenido = replaceVariables(contenido, variables);
			guardarArchivo(contenido, "output/", path.split("/")[1], variables.get(ENTITY));

		}

	}

	public static void guardarArchivo(String contenido, String rutaDestino, String archivo, String entidad)
			throws IOException {
		Files.createDirectories(Paths.get(rutaDestino)); // crear carpeta si no existe
		String rutaCompleta = (rutaDestino + entidad + archivo).replace("txt", "java");
		Files.write(Paths.get(rutaCompleta), contenido.getBytes());
		System.out.println("Archivo generado: " + rutaCompleta);
	}

	public static String replaceVariables(String contenido, Map<String, String> variables) {
		for (Map.Entry<String, String> entrada : variables.entrySet()) {
			String target = "{" + entrada.getKey() + "}";
			contenido = contenido.replace(target, entrada.getValue());
		}
		return contenido;
	}

}
