package system.general;

import system.excepciones.RutInvalido;

public class Utils {


    public static final String RUT_SUPER_ADMIN = "987654321";

    public static void validarRut(String rut) throws RutInvalido {
        if(rut.equals(RUT_SUPER_ADMIN))
            return;
        boolean validacion = false;
        try {
            rut =  rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (Exception e) {
            throw new RutInvalido();
        }
        if(!validacion)
            throw new RutInvalido();
    }
}
