package criteriosBusqueda;

import system.general.Persona;
import system.interfaces.Criteria;

import java.util.ArrayList;

public class CriteriaCiudad implements Criteria {

    private String ciudad;

    public CriteriaCiudad(String ciudad){
        this.ciudad = ciudad;
    }

    @Override
    public ArrayList<Persona> meetCriteria(ArrayList<Persona> personas){
        ArrayList<Persona> personasFiltradas = new ArrayList<>();
        for(Persona persona : personas){
            if(persona.getCiudad().equalsIgnoreCase(ciudad))
                personasFiltradas.add(persona);
        }
        return personasFiltradas;
    }
}
