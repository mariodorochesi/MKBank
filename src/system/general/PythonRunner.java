package system.general;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class PythonRunner {

    /**
     * Ejecuta el comando generado por ComandPythonGenRun
     * @param pythonScriptName  Nombre del script de python
     * @throws URISyntaxException
     * @throws IOException
     */
    public void run(String pythonScriptName) throws URISyntaxException, IOException {
        executeCommand(
                comandPythonGenRun(pythonScriptName));
    }

    /**
     * Ejecuta una linea de comando ingresada como parametro
     * @param command   Linea de comando
     * @return
     * @throws IOException
     */
    private String executeCommand(String command) throws IOException {
        StringBuffer output = new StringBuffer();
        System.out.println(command);
        Runtime.getRuntime().exec(command);
        return output.toString();

    }

    /**
     * Genera una linea de comandos para ejecutar un script de python en
     * la ruta que se encuentra Java
     * @param pythonScriptName Nombre del script que se quiere ejcutar
     * @return Retorna una linea de comando
     * @throws URISyntaxException
     * @throws IOException
     */
    private String comandPythonGenRun(String pythonScriptName) throws URISyntaxException, IOException {
        String path = (new File(".").getAbsolutePath()).replace(".", "");
        System.out.println("Absolute path: " + path);

        // path.substring(1)
        return "python " + '"' + path +
                pythonScriptName + '"';
    }

}
