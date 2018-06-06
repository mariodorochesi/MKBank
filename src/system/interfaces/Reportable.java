package system.interfaces;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Label;

import java.util.ArrayList;

public interface Reportable {

    /**
     * Modifica los recursos ingresados como paramtro para generar un reporte
     * @param reportLines   - Lista que permite tener un registro en forma de texto del reporte
     * @param grafico       - Grafico de puntos y lineas
     * @param tabla         - Tabla de datos
     * @param lb1           - Label de texto 1
     * @param lb2           - Label de texto 2
     * @param lb3           - Label de texto 3
     * @param lb4           - Label de texto 4
     * @param lb5           - Label de texto 5
     */
    void generarReporte(ArrayList<String> reportLines, ScatterChart grafico, JFXTreeTableView tabla, Label lb1,
                        Label lb2, Label lb3, Label lb4, Label lb5);
}
