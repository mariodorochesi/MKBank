package criteriosBusqueda;

import system.general.Persona;
import system.interfaces.Criteria;

import java.util.ArrayList;

public class CriteriaMale implements Criteria {

    @Override
    public ArrayList<Persona> meetCriteria(ArrayList<Persona> personas){
        ArrayList<Persona> personasFiltradas = new ArrayList<>();

        for(Persona persona : personas){
            if(persona.getGenero().equals("Hombre")){
                personasFiltradas.add(persona);
            }
        }
        return personasFiltradas;
    }
}
