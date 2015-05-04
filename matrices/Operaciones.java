package matrices;

import javax.swing.JOptionPane;
import fraccion.Fracciones;

/**
 *
 * @author Bernal
 */
public class Operaciones {
    
    private int tamCols;
    private int tamRows;
    private Fracciones[][] matriz;
    private Fracciones[][] matriz_2;

    public Operaciones()
    {
        this.tamCols = 0;
        this.tamRows = 0;
        this.matriz = null;
    }
    
    public Operaciones(int tamCols, int tamRows, int[][] matriz)
    {
        this.tamCols = tamCols;
        this.tamRows = tamRows;
        this.matriz = this.convertirFraccion(matriz);
        
        this.matriz = new Fracciones[tamCols][tamRows];
    }
    
    public Operaciones(int tamCols, int tamRows, int[][] matriz, int tamRows_2, int tamCols_2, int[][] matriz_2)
    {
        this.tamCols = tamCols;
        this.tamRows = tamRows;
        this.matriz = this.convertirFraccion(matriz);
        this.matriz = this.convertirFraccion(matriz_2);
        
        this.matriz = new Fracciones[tamRows][tamCols];
        this.matriz_2 = new Fracciones[tamRows_2][tamCols_2];
    }
    
    public Fracciones[][] convertirFraccion(int[][] matriz)
    {
        Fracciones[][] nuevaMatriz = new Fracciones[tamCols][tamRows];
        for(int rows=0; rows<matriz.length; rows++)
        {
            for(int cols=0; cols<matriz[rows].length; cols++)
            {
                nuevaMatriz[rows][cols] = new Fracciones(matriz[rows][cols]);
            }
        }
        return nuevaMatriz;
    }
    
    public Fracciones[][] sacarTrasversal()
    {
        if(tamCols == tamRows)
        {
            for(int i=0; i<tamRows; i++)
            {
                for(int j=0; j<tamCols; i++)
                {
                    matriz[j][i] = matriz[i][j];
                }
            }
            return matriz;
        }
        JOptionPane.showMessageDialog(null, "Error al sacar matriz traspuesta.\n Las columnas y filas deben ser del mismo tañano");
        return null;
    }
    
    public Fracciones calcularDeterminante()
    {
        if(this.tamRows == this.tamCols)
        {
            Fracciones[] fr = new Fracciones[tamRows];
            Fracciones aux = matriz[0][0];
            int count; 
            Fracciones sumar = new Fracciones(0);
            Fracciones restar = new Fracciones(0);
            
            for(int oneTwo=0; oneTwo<2; oneTwo++)
            {
                for(int i=0; i<tamRows; i++)
                {
                    count = i-oneTwo;
                    if(count == -1) count=2;
                    for(int h=0; h<tamCols; h++)
                    {
                        if(h==0)
                            aux = matriz[h][count];
                        else if(h==tamCols-1)
                            fr[i] = Fracciones.multiplicacion(aux, matriz[h][count]);
                        else
                            aux = Fracciones.multiplicacion(aux, matriz[h][count]);

                        if(count == tamRows-1) 
                            count = 0; 
                        else if(oneTwo==0)
                            count++;
                        else
                            count--;
                    }
                }
                if(oneTwo==0)
                    sumar = Fracciones.suma(fr);
                else
                    restar = Fracciones.resta(fr);
            }
            return Fracciones.resta(sumar, restar);
        }
        JOptionPane.showMessageDialog(null, "Sólo se puede sacar el determinante de una matriz de 3x3 por el momento");
        return null;
    }
    
    public boolean allChecked(int[] check, int type)
    {        
        for(int i=0; i<check.length; i++)
        {
            if(check[i]==type)
            {
                return false;
            }
        }
        
        return true;
    }
    
    public void resetChecked(int[] check)
    {
        for(int i=0; i<check.length; i++)
        {
            check[i] = 0;
        }
    }
    
    public Fracciones[][] metodoGaussJordan()
    {
        // Creamos el arreglo de Fracciones de la nueva matriz de GJ
        Fracciones[][] matrizGauss = new Fracciones[tamRows][tamCols+matriz_2[0].length];
        // Creamos el arrreglo de enteros para comprobar que se haya revisado toda la columna de la matriz
        int[] checkAll = new int[3];
        // limit para una matriz de 3x3 el limite es 3
        int limit = 3;
        // Contador h
        int h=0;
        // Inicializamos el arreglo de Fracciones para fracciones auxiliares que multiplicaran a toda una fila
        Fracciones[] frAux = new Fracciones[limit];

        // Esto se repetira 3 veces
        for(int i=0; i<limit; i++)
        {
            // Se repite varias veces (para mover el contador) hasta que el arreglo 'checkAll' contenga puros '1'
            while (!this.allChecked(checkAll, 0))
            {
                // Llama al metodo 'allChecked' para comprobar si 'checkAll' contiene puros 0
                if(this.allChecked(checkAll, 1))
                {
                    // Si i = j se cumple la condición si no se salta hasta la seccion del contador
                    if(i==h)
                    {
                        // Se saca la fraccion auxiliar por la cual se multiplicara toda una fila
                        frAux[h] = new Fracciones(matriz[h][i].GetDenominador(), matriz[i][h].GetDenominador());
                        // Realizamos un ciclo para pasar por toda un renglon o fila de la matriz
                        for(int j=0; j<limit; j++)
                        {
                            // Multiplicamos fracciones, la auxiliar y la que se encuentra en la matriz original
                            matrizGauss[h][j] = Fracciones.multiplicacion(frAux[h], matriz[h][j]);
                        }
                        
                        // Este ciclo es de la segunda matriz.
                        for (int a=0; a<matriz_2[i].length; a++)
                        {
                            matrizGauss[h][limit+a] = Fracciones.multiplicacion(frAux[h], matriz_2[h][a]);
                        }
                        
                        // Cambiamos que se ha realizado una accion de 3
                        checkAll[h] = 1;
                    }
                }
                else
                {
                    // Se saca la fraccion auxiliar por la cual se multiplicara toda una fila y luego se sumara otra fila
                    frAux[h] = new Fracciones(-(matriz[h][i].GetNumerador()), matriz[h][i].GetDenominador());
                    for(int j=0; j<limit; j++)
                    {
                        matrizGauss[h][j] = Fracciones.multiplicacion(frAux[h], matriz[h-1][j]);
                        matrizGauss[h][j] = Fracciones.suma(matrizGauss[h][j], matriz[h][j]);
                    }
                    
                    // Este ciclo es de la segunda matriz.
                    for (int a=0; a<matriz_2[i].length; a++)
                    {
                        matrizGauss[h][limit+a] = Fracciones.multiplicacion(frAux[h], matriz_2[h-1][a]);
                        matrizGauss[h][limit+a] = Fracciones.suma(matrizGauss[h][limit+a], matriz_2[h][a]);
                    }
                
                    // Cambiamos que se ha realizado una accion más de 3
                    checkAll[h] = 1;
                }
                
                // Si el arreglo 'checkAll' contie puros 1 e 'i' es diferente al limit-1 y 'h' es diferente al limit-2
                if(this.allChecked(checkAll, 0) && i != limit-1 && h != limit-2)
                    this.resetChecked(checkAll);
                
                // Si h es igual a 2 se cambia a 0 sino se suma uno a h (valores de h: 0,1,2)
                if(h == 2)
                    h=0;
                else
                    h++;
            }
        }
        return matrizGauss;
    }
    
    public void metodoDeterminantes()
    {
        
    }
    
    public void inversaGaussJordan()
    {
        
    }
    
    public void inversaCofactores()
    {
        
    }
}
