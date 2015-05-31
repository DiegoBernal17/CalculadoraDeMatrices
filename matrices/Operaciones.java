package matrices;

import javax.swing.JOptionPane;
import fraccion.Fracciones;
import java.util.Arrays;

/**
 *
 * @author Bernal
 */
public class Operaciones {
    
    private final int tamCols;
    private final int tamRows;
    private Fracciones[][] matriz;
    private Fracciones[][] matriz_2;
    private String message;

    // Constructor sin parametros.
    public Operaciones()
    {
        this.tamCols = 0;
        this.tamRows = 0;
        this.matriz = null;
        this.matriz_2 = null;
    }
    
    // Contructor recibiendo una matriz
    public Operaciones(Matrices mtz) 
    {
        this.matriz = mtz.getMatriz();
        this.tamCols = mtz.getTamCols();
        this.tamRows = mtz.getTamRows();
    }
    
    // Constructor recibiendo dos matrices necesarias para algunos metodos
    public Operaciones(Matrices matriz, Matrices matriz_2)
    {
        this.tamCols = matriz.getTamCols();
        this.tamRows = matriz.getTamRows();
        this.matriz = matriz.getMatriz();
        this.matriz_2 = matriz_2.getMatriz();
    }
    
    // Este metodo calcula el determinante de la matriz
    public Fracciones calcularDeterminante()
    {
        return this.calcularDeterminante(matriz);
    }
    
    // Este metodo calcula el determinante de la matriz recibida.
    public Fracciones calcularDeterminante(Fracciones[][] matriz)
    {
        // Comprueba si el número de columnas y filas son iguales
        if(matriz.length == matriz[0].length && matriz.length > 1)
        {
            // Declaramos variables auxiliares
            Fracciones[] fr = new Fracciones[matriz.length];
            Fracciones aux = matriz[0][0]; 
            Fracciones sumar = new Fracciones(0);
            Fracciones restar = new Fracciones(0);
            int count;
            
            // Repetir 2 veces. Primera vez se suman, la otra restan
            for(int oneTwo=0; oneTwo<2; oneTwo++)
            {
                for(int i=0; i<matriz.length; i++)
                {
                    // Restamos 0 o 1 dependiendo que en vuelta va el ciclo
                    count = i-oneTwo;
                    System.out.println("*count: "+count); //<----
                    // Si es -1 se convierte a 2 ya que aqui el -1 no nos sirve
                    if(count == -1) count=matriz.length-1;
                    System.out.println("count (2): "+count); //<----
                    // Ciclo para hacer las multiplicaciones
                    for(int h=0; h<matriz[0].length; h++)
                    {
                            System.out.println("    h: "+h); //<----
                            // Primera vuelta
                        if(h==0) {
                            aux = matriz[h][count];
                            System.out.println("    aux: "+aux); //<----
                        // Ultima vuelta
                        }else if(h==matriz[0].length-1) {
                            fr[i] = Fracciones.multiplicacion(aux, matriz[h][count]);
                            System.out.println("    fr["+i+" ( i )]: "+fr[i]); //<----
                        // Si no es primer ni ultima vuelta
                        }else {
                            aux = Fracciones.multiplicacion(aux, matriz[h][count]);
                            System.out.println("    aux (2): "+aux); //<----
                        }
                        
                        // Convierte a 0 o suma o resta la variable count dependiendo el caso
                        if(count == matriz.length-1) {
                            count = 0; 
                            System.out.println("    count (2): "+count+"\n"); //<----
                        }else if(oneTwo==0) {
                            count++;
                            System.out.println("count++: "+count); //<----
                        } else {
                            count--;
                            System.out.println("Count--: "+count); //<----
                        }
                        
                        if(count < 0) {
                            count = matriz.length-1;
                        }
                    }
                }
                System.out.println("fr"+Arrays.toString(fr)); //<----
                // Si es la primer vuelta sumara todo
                if(oneTwo==0)
                    sumar = Fracciones.suma(fr);
                // Si es la segunda vuelta restara todo
                else {
                    fr[0] = new Fracciones(-fr[0].GetNumerador(), fr[0].GetDenominador());
                    restar = Fracciones.resta(fr);
                }
            }
            
            System.out.println("Suma: "+sumar); // <----
            System.out.println("Resta: "+restar); // <----   
            // Retorna el resultado final simplificao (La determinante)
            return Fracciones.suma(sumar, restar);
        }
        JOptionPane.showMessageDialog(null, "Sólo se puede sacar el determinante de una matriz mayor de 3x3 por el momento");
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
    
    public void createFirstGJ(Fracciones[][] mtzG)
    {
        for(int rows=0; rows<mtzG.length; rows++)
        {
            for(int cols=0; cols<mtzG[0].length; cols++)
            {
                if(cols < tamCols)
                    mtzG[rows][cols] = matriz[rows][cols];
                else
                    mtzG[rows][cols] = matriz_2[rows][mtzG[0].length-cols-1];
            }
        }
    }
    
    // Metodo de Gauss Jordan. El resultao final es la matriz ya procesada por el metodo de Gauss Jordan
    public Fracciones[][] usarGaussJordan()
    {
        // Creamos el arreglo de Fracciones de la nueva matriz de GJ
        Fracciones[][] matrizGauss = new Fracciones[tamRows][tamCols+matriz_2[0].length];
        // Asignamos valores que contiene 'matriz' y 'matriz_2'. Los une en una misma matriz.
        this.createFirstGJ(matrizGauss);
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
            // Comprobar que el arreglo ' checkAll' contenga puros 0, para ello reseterlo llamando un metodo
            this.resetChecked(checkAll);
            System.out.println("i: "+i); // <---- MOSTRAR EN CONSOLA
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
                        frAux[h] = new Fracciones(matrizGauss[i][h].GetDenominador(), matrizGauss[i][h].GetNumerador());
                        System.out.println("* frAux["+h+" (h)]: "+frAux[h]); // <---- MOSTRAR EN CONSOLA
                        // Realizamos un ciclo para pasar por toda un renglon o fila de la matriz
                        for(int j=0; j<matrizGauss[0].length; j++)
                        {
                            System.out.println("    j (1): "+j); // <---- MOSTRAR EN CONSOLA
                            // Multiplicamos fracciones, la auxiliar y la que se encuentra en la matriz original
                            matrizGauss[h][j] = Fracciones.multiplicacion(frAux[h], matrizGauss[h][j]);
                            System.out.println("    - matrizGauss["+h+" (h)]["+j+" (j)]: "+matrizGauss[h][j]); // <---- MOSTRAR EN CONSOLA
                        }
                        
                        // Cambiamos que se ha realizado una accion de 3
                        checkAll[h] = 1;
                        System.out.println("#### checkAll["+h+" (h)]: "+checkAll[h]); // <---- MOSTRAR EN CONSOLA
                    }
                }
                else
                {
                    // Se saca la fraccion auxiliar por la cual se multiplicara toda una fila y luego se sumara otra fila
                    frAux[h] = new Fracciones(-(matrizGauss[h][i].GetNumerador()), matrizGauss[h][i].GetDenominador());
                    for(int j=0; j<matrizGauss[0].length; j++)
                    {
                        System.out.println("    j(2): "+j); // <---- MOSTRAR EN CONSOLA
                        System.out.println("    frAux["+h+" (h)]: "+frAux[h]+" ---  matrizGauss["+i+" (i)]["+j+" (j)]: "+matrizGauss[i][j]);
                        Fracciones auxGJ = Fracciones.multiplicacion(frAux[h], matrizGauss[i][j]);
                        System.out.println("    $ auxGJ: "+auxGJ); // <---- MOSTRAR EN CONSOLA
                        
                        matrizGauss[h][j] = Fracciones.suma(auxGJ, matrizGauss[h][j]);
                        System.out.println("    $ matrizGauss["+h+" (h)]["+j+" (j)]: "+matrizGauss[h][j]); // <---- MOSTRAR EN CONSOLA
                    }
                    
                    // Cambiamos que se ha realizado una accion más de 3
                    checkAll[h] = 1;
                    System.out.println("#### checkAll["+h+" (h)]: "+checkAll[h]+"\n"); // <---- MOSTRAR EN CONSOLA
                }
                
                
                // Si h es igual a 2 se cambia a 0 sino se suma uno a h (valores de h: 0,1,2)
                if(h == 2)
                    h=0;
                else
                    h++;
            }
            System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
        }
        return matrizGauss;
    }
    
    // Metodo para mostrar X, Y y Z de una matriz mediante el metodo de Gauss Jordan.
    public String toStringGaussJordan()
    {
        Fracciones[][] gj = this.usarGaussJordan();
        return "X="+gj[0][this.tamRows]+"  Y="+gj[1][this.tamRows]+"  Z="+gj[2][this.tamRows];
    }
    
    public Fracciones[] toFraccionesGaussJordan()
    {
        Fracciones[][] gj = this.usarGaussJordan();
        Fracciones[] xyz = new Fracciones[3];
        
        xyz[0] = gj[0][this.tamRows];
        xyz[1] = gj[1][this.tamRows];
        xyz[2] = gj[2][this.tamRows];
        
        return xyz;
    }
    
    // Metodo para sustituir toda una columna
    public Fracciones[][] sustituirColumna(int column)
    {
        // Se crea e inicializa un arreglo auxiliar
        Fracciones[][] nuevaMatriz = new Fracciones[tamCols][tamRows];
        
        for(int cols=0; cols<tamCols; cols++)
        {
            for(int rows=0; rows<tamRows; rows++)
            {
                // Si la columna que se pide sustituir es igual a la actual en el ciclo.
                if(cols == column)
                    nuevaMatriz[cols][rows] = matriz_2[cols][rows];
                else
                    nuevaMatriz[cols][rows] = matriz[cols][rows];
            }
        }
        return nuevaMatriz;
    }
    
    public Fracciones[] metodoDeterminantes()
    {
        // Caculamos la determinante total y la guardamos en una variable
        Fracciones dt = this.calcularDeterminante(this.matriz);
        // Declaramos varibles de determinante en X, determinante en Y y determiante en Z
        Fracciones dx, dy, dz;
        Fracciones[] xyz = new Fracciones[3];
        // Declaramos el arreglo bidimensional donde se sustituiran columans
        Fracciones[][] mtzSustituida;
        
        // Ahora sustituimos la columna de resultados en la primer columna y calculamos el determinante
        mtzSustituida = this.sustituirColumna(0);
        dx = this.calcularDeterminante(mtzSustituida);
        // Sacamos X dividiendo Dx entre Dt
        xyz[0] = Fracciones.division(dx, dt);
        
        // Ahora sustituimos la columna de resultados en la segunda columna y calculamos el determinante
        mtzSustituida = this.sustituirColumna(1);
        dy = this.calcularDeterminante(mtzSustituida);
        // Sacamos Y dividiendo Dy entre Dt
        xyz[1] = Fracciones.division(dy, dt);
        
        // Por ultimo sustituimos columna de resultados en la tercer columna y calculamos el determinante
        mtzSustituida = this.sustituirColumna(2);
        dz = this.calcularDeterminante(mtzSustituida);
        // Sacamos Z dividiendo Dz entre Dt
        xyz[2] = Fracciones.division(dz, dt);
        
        // Retorna en un arreglo los valores de X, Y, Z.
        return xyz;
    }
    
    // Metodo para sacar la inversa por el metodo de Gauss Jordan
    public Fracciones[][] inversaGaussJordan()
    {
        // Calcula la determinante de la matriz para comprobar si no es 0
        Fracciones dt = this.calcularDeterminante(this.matriz);
        if(dt.GetNumerador() != 0)
        {
            // Las filas y columnas deben ser del mismo tamaño
            if(tamCols == tamRows)
            {
                // Saca la matriz identida y la guarda en la 'matriz_2'
                this.setMatriz_2(this.matrizIdentidad());
                // Retorna la matriz bidimensinal ya procesada por el metodo de Gauss Jordan
                // y que seria ya la matriz inversa
                return this.usarGaussJordan();
            } 
            else
                JOptionPane.showMessageDialog(null, "Error. Las filas y columnas de la matriz deben ser iguales.");
        }
        else
            JOptionPane.showMessageDialog(null, "Error. El determinante es 0 por lo tanto no se puede sacar la inversa");
        // Si retorna este valor 'null' es que hubo un error y anteriorente debio mostrar un mensaje de error.
        return null;
    }
    
    // Este metodo solo es llamado para mostrar la comprobacion de la inversa por GJ.    A * A^2 = |
    public void comprobacionInvGJ()
    {
        // Multiplicar matriz de fracciones
        // Fracciones.multiplicacion(this.matriz, this.inversaGaussJordan());
    }
    
    public void convertirAUnos(Fracciones[][] fr, char signo)
    {
        for(int rows=0; rows<fr.length; rows++)
        {
            for(int cols=0; cols<fr[rows].length; cols++)
            {
                if(signo == '+')
                    fr[rows][cols] = new Fracciones(1);
                else if(signo == '-')
                    fr[rows][cols] = new Fracciones(-1);
            }
        }
    }
    
    // Este metodo saca la inversa por el metodo de cofactores
    public Fracciones[][] inversaCofactores()
    {
        Fracciones dtA = this.calcularDeterminante(matriz);
        Fracciones[][] aux = new Fracciones[this.tamRows-1][this.tamCols-1];
        Fracciones[][] aux2 = new Fracciones[this.tamRows][this.tamCols];
        int rows2 = 0;
        int cols2 =0;
        
        // Comprobar que determinante es diferente a 0
        if(dtA.GetNumerador() != 0)
        {
            for(int rowsCof=0; rowsCof<tamRows; rowsCof++)
            {
                for(int colsCof=0; colsCof<tamCols; colsCof++)
                {
                    for(int rows=0; rows<tamRows; rows++)
                    {
                        for(int cols=0; cols<tamCols; cols++)
                        {
                            if(rows2 != rows && cols2  != cols)
                                aux[rows2][cols2] = matriz[rows][cols];
                        }
                    }
                    
                    if((rowsCof+colsCof) % 2 == 0)
                        aux2[rowsCof][colsCof] = Fracciones.multiplicacion(new Fracciones(1), this.calcularDeterminante(aux));
                    else
                        aux2[rowsCof][colsCof] = Fracciones.multiplicacion(new Fracciones(-1), this.calcularDeterminante(aux));
                }
            }
        }
        
        // Sacamos la trasversal de aux 2
        Matrices matrizTra = new Matrices(aux2, 1, 1);
        matriz_2 = matrizTra.sacarTrasversal();
        
        // Mutiplicar la matriz_2 por el determinante
        Matrices matrizFinal = new Matrices(matriz_2, dtA, new Fracciones(1));
        return matrizFinal.getMatriz();
    }
    
    // Lo que hace este metodo es crear una matriz Fracciones auxiliar par asi convertir todo en 0 y 1
    // segun como se estructura la matriz identidad. Siempre dara el mismo resultado segun el tamaño de la matriz
    public Fracciones[][] matrizIdentidad()
    {
        // Matriz auxiliar 'ident'
        Fracciones[][] ident = new Fracciones[tamCols][tamRows];
        for(int cols=0; cols<ident.length; cols++)
        {
            for(int rows=0; rows<ident[cols].length; rows++)
            {
                // Rows y Cols tiene que tener el mismo valor (Ejemplo rows=1 y cols=1)
                // El valor de la matriz ident es igual a '1' de lo contrario lo demas seran solo '0'
                if(rows == cols)
                    ident[rows][cols] = new Fracciones(1);
                else
                    ident[rows][cols] = new Fracciones(0);
            }
        }
        return ident;
    }
    
    public void setMatriz(Fracciones[][] matriz)
    {
        this.matriz = matriz;
    }
    
    public void setMatriz_2(Fracciones[][] matriz)
    {
        this.matriz_2 = matriz;
    }
    
    public Fracciones[][] getMatriz()
    {
        return this.matriz;
    }
    
    public Fracciones[][] getMatriz_2()
    {
        return this.matriz_2;
    }
    
    public String getMessage()
    {
        return this.message;
    }
}
