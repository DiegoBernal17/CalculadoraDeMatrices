package matrices;

import javax.swing.JOptionPane;
import fraccion.Fracciones;

/**
 *
 * @author Bernal
 */
public class Operaciones {
    
    private final int tamCols;
    private final int tamRows;
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
        this.matriz = new Fracciones[tamCols][tamRows];
        this.matriz_2 = new Fracciones[tamCols][tamRows];
        this.matriz = this.convertirFraccion(matriz);
    }
    
    public Operaciones(int tamCols, int tamRows, int[][] matriz, int tamRows_2, int tamCols_2, int[][] matriz_2)
    {
        this.tamCols = tamCols;
        this.tamRows = tamRows;
        this.matriz = new Fracciones[tamRows][tamCols];
        this.matriz_2 = new Fracciones[tamRows_2][tamCols_2];
        this.matriz = this.convertirFraccion(matriz);
        this.matriz = this.convertirFraccion(matriz_2);

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
    
    // Este metodo invierte filas y columnas
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
    
    public Fracciones calcularDeterminante(Fracciones[][] matriz)
    {
        // Comprueba si el número de columnas y filas son iguales
        if(this.tamRows == this.tamCols)
        {
            // Declaramos variables auxiliares
            Fracciones[] fr = new Fracciones[tamRows];
            Fracciones aux = matriz[0][0]; 
            Fracciones sumar = new Fracciones(0);
            Fracciones restar = new Fracciones(0);
            int count;
            
            // Repetir 2 veces. Primera vez se suman, la otra restan
            for(int oneTwo=0; oneTwo<2; oneTwo++)
            {
                for(int i=0; i<tamRows; i++)
                {
                    // Restamos 0 o 1 dependiendo que ne vuelta va el ciclo
                    count = i-oneTwo;
                    // Si es -1 se convierte a 2 ya que aqui el -1 no nos sirve
                    if(count == -1) count=2; 
                    // Ciclo para hacer las multiplicaciones
                    for(int h=0; h<tamCols; h++)
                    {
                        // Primera vuelta
                        if(h==0)
                            aux = matriz[h][count];
                        // Ultima vuelta
                        else if(h==tamCols-1)
                            fr[i] = Fracciones.multiplicacion(aux, matriz[h][count]);
                        // Si no es primer ni ultima vuelta
                        else
                            aux = Fracciones.multiplicacion(aux, matriz[h][count]);

                        // Convierte a 0 o suma o resta la variable count dependiendo el caso
                        if(count == tamRows-1) 
                            count = 0; 
                        else if(oneTwo==0)
                            count++;
                        else
                            count--;
                    }
                }
                // Si es la primer vuelta sumara todo
                if(oneTwo==0)
                    sumar = Fracciones.suma(fr);
                // Si es la segunda vuelta restara todo
                else
                    restar = Fracciones.resta(fr);
            }
            // Retorna el resultado final (La determinante)
            return Fracciones.resta(sumar, restar);
        }
        JOptionPane.showMessageDialog(null, "Sólo se puede sacar el determinante de una matriz de 3x3 por el momento");
        return null;
    }
    
    // Este metodo comprueba si hay algun 0 o 1 en un arreglo
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
    
    // Metodo que resete el arreglo de enteros recibido (Todo a 0)
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
    
    public Fracciones[][] sustituirColumna(int column)
    {
        Fracciones[][] nuevaMatriz = new Fracciones[tamCols][tamRows];
        for(int cols=0; cols<tamCols; cols++)
        {
            for(int rows=0; rows<tamRows; rows++)
            {
                if(cols == column)
                    nuevaMatriz[cols][rows] = matriz_2[cols][rows];
                else
                    nuevaMatriz[cols][rows] = matriz[cols][rows];
            }
        }
        return nuevaMatriz;
    }
    
    public String metodoDeterminantes()
    {
        // Caculamos la determinante total y la guardamos en una variable
        Fracciones dt = this.calcularDeterminante(this.matriz);
        // Declaramos varibles de determinante en X, determinante en Y y determiante en Z
        Fracciones dx, dy, dz, x, y, z;
        // Declaramos el arreglo bidimensional donde se sustituiran columans
        Fracciones[][] mtzSustituida;
        
        // Ahora sustituimos la columna de resultados en la primer columna y calculamos el determinante
        mtzSustituida = this.sustituirColumna(0);
        dx = this.calcularDeterminante(mtzSustituida);
        // Sacamos X dividiendo Dx entre Dt
        x = Fracciones.division(dx, dt);
        
        // Ahora sustituimos la columna de resultados en la segunda columna y calculamos el determinante
        mtzSustituida = this.sustituirColumna(1);
        dy = this.calcularDeterminante(mtzSustituida);
        // Sacamos Y dividiendo Dy entre Dt
        y = Fracciones.division(dy, dt);
        
        // Por ultimo sustituimos columna de resultados en la tercer columna y calculamos el determinante
        mtzSustituida = this.sustituirColumna(2);
        dz = this.calcularDeterminante(mtzSustituida);
        // Sacamos Z dividiendo Dz entre Dt
        z = Fracciones.division(dz, dt);
        
        return "X="+x+"  Y="+y+"  Z="+z;
    }
    
    public Fracciones[][] inversaGaussJordan()
    {
        Fracciones dt = this.calcularDeterminante(this.matriz);
        if(dt.GetNumerador() != 0)
        {
            if(tamCols == tamRows)
            {
                this.setMatriz_2(this.matrizIdentidad());
                return this.metodoGaussJordan();
            } 
            else
                JOptionPane.showMessageDialog(null, "Error. Las filas y columnas de la matriz deben ser iguales.");
        }
        else
            JOptionPane.showMessageDialog(null, "Error. El determinante es 0 por lo tanto no se puede sacar la inversa");
        return null;
    }
    
    public void comprobacionInvGJ()
    {
        
    }
    
    public void inversaCofactores()
    {
        
    }
    
    public Fracciones[][] matrizIdentidad()
    {
        Fracciones[][] ident = new Fracciones[tamCols][tamRows];
        for(int rows=0; rows<ident.length; rows++)
        {
            for(int cols=0; cols<ident[rows].length; cols++)
            {
                if(rows == cols)
                    ident[rows][cols] = new Fracciones(1);
                else
                    ident[rows][cols] = new Fracciones(0);
            }
        }
        return ident;
    }
    
    public void setMatriz_2(Fracciones[][] matriz)
    {
        this.matriz_2 = matriz;
    }
}
