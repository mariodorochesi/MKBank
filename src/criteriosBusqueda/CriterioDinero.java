package criteriosBusqueda;

import system.general.Persona;
import system.interfaces.Criteria;

import java.util.ArrayList;

public class CriterioDinero implements Criteria {

    private long montoFiltro;

    public CriterioDinero(long montoFiltro){
        this.montoFiltro = montoFiltro;
    }

    public ArrayList<Persona> meetCriteria(ArrayList<Persona> personas){

        ArrayList<Persona> personasFiltradas = new ArrayList<>();
        for(Persona persona : personas){
            if(persona.getCuentaUsuario() != null){
                if(persona.getCuentaUsuario().montoTotalUsuario() <= montoFiltro)
                    personasFiltradas.add(persona);
            }
        }
        return personasFiltradas;
    }

}
