package matrices;

import javax.swing.JOptionPane;

/**
 *
 * @author Diego Bernal
 */
public class Messages 
{
    public static char pedirAccion()
    {
        Object[] options = {"1) Sumar matrices", "2) Restar matrices", "3) Multiplicar matrices", "4) Matricez con exponente", "Matrices con exponente afuera"};
        
        Object opcion = JOptionPane.showInputDialog(null,
                     "Escoje una opción", "Seleccionar acción",
                     JOptionPane.INFORMATION_MESSAGE, null,
                     options, options[0]); 
            
        return opcion.toString().charAt(0);
    }
}
