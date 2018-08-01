package criteriosBusqueda;

import system.general.Persona;
import system.interfaces.Criteria;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CriteriaAnd implements Criteria {

    private ArrayList<Criteria> criterias;

    public CriteriaAnd(ArrayList<Criteria> criterias){
        this.criterias = criterias;
    }

    @Override
    public ArrayList<Persona> meetCriteria(ArrayList<Persona> personas){
        ArrayList<Persona> personasFiltradas = personas;

        for(Criteria criteria : criterias){
            personasFiltradas = criteria.meetCriteria(personasFiltradas);
        }
        return personasFiltradas;
    }
}
