package matrices;
/**
 *
 * @author Diego Bernal
 */
// import javax.swing.JOptionPane;

public class AppMatrices 
{
    /*
    public static void main(String[] args)
    {
        int continuar = JOptionPane.YES_OPTION;
        while(continuar == JOptionPane.YES_OPTION)
        {
            char opt = Messages.pedirAccion();
            
            String input;
            input = JOptionPane.showInputDialog("Dame el número de filas de A");
            int rows = Integer.parseInt(input);
            input = JOptionPane.showInputDialog("Dame el número de columnas de A");
            int cols = Integer.parseInt(input);
            
            int k = JOptionPane.showConfirmDialog(null, "¿Contiene escalar (k)?", "Confirmar escalar", JOptionPane.YES_NO_OPTION);
            int escalar_a;
            int escalar_b;
            
            if(k == JOptionPane.YES_OPTION)
            {
                input = JOptionPane.showInputDialog("Dame el escalar de A. (Deja en blanco o en 1 si no tiene)");
                if(input.equals(""))
                    escalar_a = 1;
                else
                    escalar_a = Integer.parseInt(input);
                
                input = JOptionPane.showInputDialog("Dame el escalar de B. (Deja en blanco o en 1 si no tiene)");
                if(input.equals(""))
                    escalar_b = 1;
                else
                    escalar_b = Integer.parseInt(input);
            } else {
                escalar_a = 1;
                escalar_b = 1;
            }
            
            Matrices matriz_1 = new Matrices(rows, cols, escalar_a, escalar_b);
            
            if(opt == '1' || opt == '2')
            {
                Matrices matriz_2 = new Matrices(rows, cols);
                OpMatrices operaciones = new OpMatrices(rows, cols, matriz_1, matriz_2);
                
                matriz_1.pedirValores("A: Primer");
                matriz_2.pedirValores("B: Segunda");
                if(opt == '1')
                {
                    operaciones.sumarMatrices();
                    operaciones.mostrarMatrices("+");
                }
                else
                {
                    operaciones.restarMatrices();
                    operaciones.mostrarMatrices("-");
                }
            } 
            else if(opt == '2')
            {
                input = JOptionPane.showInputDialog("Dame el número de filas de B");
                int rows_2 = Integer.parseInt(input);
                input = JOptionPane.showInputDialog("Dame el número de columnas de B");
                int cols_2 = Integer.parseInt(input);
                
                Matrices matriz_2 = new Matrices(rows_2, cols_2);
                    
                if(cols == rows_2)
                {
                    OpMatrices operaciones = new OpMatrices(rows, cols_2, matriz_1, matriz_2);

                    matriz_1.pedirValores("A: Primer");
                    matriz_2.pedirValores("B: Segunda");
                    operaciones.multiplicarMatrices();
                    operaciones.mostrarMatrices(" ");
                }
                else
                    JOptionPane.showMessageDialog(null, matriz_1.getTam()+" y "+matriz_2.getTam()+" no se puede multiplicar.");
            }
            continuar = JOptionPane.showConfirmDialog(null, "¿Deseas continuar?", "Continuar", JOptionPane.YES_NO_OPTION);
        }
        System.exit(0);
    } */
}
