package criteriosBusqueda;

import system.general.Persona;
import system.interfaces.Criteria;

import java.util.ArrayList;

public class CriterioOr implements Criteria {

    private Criteria criterio1;
    private Criteria criterio2;

    public CriterioOr(Criteria criterio1 , Criteria criterio2){
        this.criterio1 = criterio1;
        this.criterio2 = criterio2;
    }

    @Override
    public ArrayList<Persona> meetCriteria(ArrayList<Persona> personas){

        ArrayList<Persona> primerFiltro = criterio1.meetCriteria(personas);
        ArrayList<Persona> segundoFiltro = criterio2.meetCriteria(personas);

        for(Persona p : segundoFiltro){
            if(!primerFiltro.contains(p)){
                primerFiltro.add(p);
            }
        }
        return primerFiltro;
    }

}
