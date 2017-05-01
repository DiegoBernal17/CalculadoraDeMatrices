package matrices;

import javax.swing.JOptionPane;
import fraccion.Fracciones;

/**
 *
 * @author Bernal
 */
public class Operaciones2 {
    
    private final int tamCols;
    private final int tamRows;
    private Fracciones[][] matriz;
    private Fracciones[][] matriz_2;

    // Constructor sin parametros.
    public Operaciones2()
    {
        this.tamCols = 0;
        this.tamRows = 0;
        this.matriz = null;
    }
    
    // Constructor recibiendo 3 parametros
    public Operaciones2(int tamCols, int tamRows, int[][] matriz)
    {
        this.tamCols = tamCols;
        this.tamRows = tamRows;
        this.matriz = new Fracciones[tamCols][tamRows];
        this.matriz_2 = new Fracciones[tamCols][tamRows];
        this.matriz = this.convertirFraccion(matriz);
    }
    
    // Constructor recibiendo 6 parametros. Recibe una segunda matriz necesaria para algunos metodos
    public Operaciones2(int tamCols, int tamRows, int[][] matriz, int tamRows_2, int tamCols_2, int[][] matriz_2)
    {
        this.tamCols = tamCols;
        this.tamRows = tamRows;
        this.matriz = new Fracciones[tamRows][tamCols];
        this.matriz_2 = new Fracciones[tamRows_2][tamCols_2];
        this.matriz = this.convertirFraccion(matriz);
        this.matriz = this.convertirFraccion(matriz_2);

    }
    
    // Este metodo convierte un arreglo de enteros a un arreglo de Fracciones
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
    
    // Este metodo calcula el determinante de la matriz recibida.
    public Fracciones calcularDeterminante(Fracciones[][] matriz)
    {
        // Comprueba si el número de columnas y filas son iguales
        if(matriz.length == matriz[0].length)
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
                    // Si es -1 se convierte a 2 ya que aqui el -1 no nos sirve
                    if(count == -1) count=2; 
                    // Ciclo para hacer las multiplicaciones
                    for(int h=0; h<matriz[0].length; h++)
                    {
                        // Primera vuelta
                        if(h==0)
                            aux = matriz[h][count];
                        // Ultima vuelta
                        else if(h==matriz[0].length-1)
                            fr[i] = Fracciones.multiplicacion(aux, matriz[h][count]);
                        // Si no es primer ni ultima vuelta
                        else
                            aux = Fracciones.multiplicacion(aux, matriz[h][count]);

                        // Convierte a 0 o suma o resta la variable count dependiendo el caso
                        if(count == matriz.length-1) 
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
    
    // Metodo de Gauss Jordan. El resultao final es la matriz ya procesada por el metodo de Gauss Jordan
    public Fracciones[][] usarGaussJordan()
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
    
    /* Codigo estructurado del metodo de Gauss Jordan.
    public Fracciones[][] metodoGaussJordan()
    {
        Fracciones[][] matrizGauss = new Fracciones[tamRows][tamCols+matriz_2[0].length];
        
        //Convertimos el arreglo  matrizGauss[0][0] a 1 (uno) y multiplicamos todos sus elemntos
        matrizGauss[0][0] = new Fracciones(matriz[0][0].GetDenominador(), matriz[0][0].GetNumerador());
        matrizGauss[0][1] = Fracciones.multiplicacion(matriz[0][1], matrizGauss[0][0]);
        matrizGauss[0][2] = Fracciones.multiplicacion(matriz[0][2], matrizGauss[0][0]);
        
        for (int i=0; i<matriz_2[0].length; i++) {
            matrizGauss[0][3+i] = Fracciones.multiplicacion(matriz_2[0][i], matrizGauss[0][0]);
        }
        
        // Numeros fraccionales (objeto Fracciones) auxiliares. frAux1;
        Fracciones frAux1;
        frAux1 = new Fracciones(-(matriz[1][0].GetNumerador()), matriz[1][0].GetDenominador());
        
        // Sacamos el 0 necesario para matrizGauss[1][0] y hacemos las multiplicaciones
        matrizGauss[1][0] = Fracciones.multiplicacion(frAux1, matriz[0][0]);
        matrizGauss[1][0] = Fracciones.suma(matriz[1][0], matrizGauss[1][0]);
        
        matrizGauss[1][1] = Fracciones.multiplicacion(frAux1, matriz[0][1]);
        matrizGauss[1][1] = Fracciones.suma(matriz[1][1], matrizGauss[1][1]);
        
        matrizGauss[1][2] = Fracciones.multiplicacion(frAux1, matriz[0][2]);
        matrizGauss[1][2] = Fracciones.suma(matriz[1][2], matrizGauss[1][2]);
        
        for (int i=0; i<matriz_2[1].length; i++) {
            matrizGauss[1][3+i] = Fracciones.multiplicacion(frAux1, matriz_2[0][i]);
            matrizGauss[1][3+i] = Fracciones.suma(matriz[1][i], matrizGauss[1][3+i]);
        }
        
        // Numeros fraccionales (objeto Fracciones) auxiliares. frAux2;
        Fracciones frAux2;
        frAux2 = new Fracciones(-(matriz[2][0].GetDenominador()), matriz[2][0].GetNumerador());
        
        // Sacamos el 0 necesario para matrizGauss[2][0] y hacemos las multiplicaciones
        matrizGauss[2][0] = Fracciones.multiplicacion(frAux2, matriz[0][0]);
        matrizGauss[2][0] = Fracciones.suma(matriz[2][0], matrizGauss[2][0]);
        
        matrizGauss[2][1] = Fracciones.multiplicacion(frAux2, matriz[0][2]);
        matrizGauss[2][1] = Fracciones.suma(matriz[2][1], matrizGauss[2][1]);
        
        matrizGauss[2][2] = Fracciones.multiplicacion(frAux2, matriz[0][3]);
        matrizGauss[2][2] = Fracciones.suma(matriz[2][2], matrizGauss[2][2]);
        
        for (int i=0; i<matriz_2[2].length; i++) {
            matrizGauss[2][3+i] = Fracciones.multiplicacion(frAux2, matriz_2[0][i]);
            matrizGauss[2][3+i] = Fracciones.suma(matriz[2][i], matrizGauss[2][3+i]);
        }
        
        Fracciones frAux3;
        frAux3 = new Fracciones(matriz[1][1].GetDenominador(), matriz[1][1].GetDenominador());
    
        matrizGauss[1][1] = Fracciones.multiplicacion(frAux3, matriz[1][1]);
        matrizGauss[1][2] = Fracciones.multiplicacion(frAux3, matriz[1][2]);
        
        for (int i=0; i<matriz_2[1].length; i++) {
            matrizGauss[1][3+i] = Fracciones.multiplicacion(frAux3, matriz_2[0][i]);
        }
        
        // Numeros fraccionales (objeto Fracciones) auxiliares. frAux4;
        Fracciones frAux4;
        frAux4 = new Fracciones(-(matriz[0][1].GetNumerador()), matriz[0][1].GetDenominador());
        
        // Sacamos el 0 necesario para matrizGauss[0][1] y hacemos las multiplicaciones
        matrizGauss[0][1] = Fracciones.multiplicacion(frAux3, matriz[1][1]);
        matrizGauss[0][1] = Fracciones.suma(matriz[0][1], matrizGauss[0][1]);
        
        matrizGauss[0][2] = Fracciones.multiplicacion(frAux1, matriz[1][2]);
        matrizGauss[0][2] = Fracciones.suma(matriz[2][1], matrizGauss[0][2]);
        
        for (int i=0; i<matriz_2[0].length; i++) {
            matrizGauss[0][3+i] = Fracciones.multiplicacion(frAux4, matriz_2[1][i]);
            matrizGauss[0][3+i] = Fracciones.suma(matriz_2[0][i], matrizGauss[0][3+i]);
        }
        
        // Numeros fraccionales (objeto Fracciones) auxiliares. frAux5;
        Fracciones frAux5;
        frAux5 = new Fracciones(-(matriz[2][1].GetNumerador()), matriz[2][1].GetDenominador());
        
        // Sacamos el 0 necesario para matrizGauss[2][1] y hacemos las multiplicaciones
        matrizGauss[2][1] = Fracciones.multiplicacion(frAux5, matriz[0][0]);
        matrizGauss[2][1] = Fracciones.suma(matriz[2][0], matrizGauss[1][0]);
        
        matrizGauss[0][2] = Fracciones.multiplicacion(frAux5, matriz[0][1]);
        matrizGauss[0][2] = Fracciones.suma(matriz[1][0], matrizGauss[1][1]);
        
        for (int i=0; i<matriz_2[0].length; i++) {
            matrizGauss[0][3+i] = Fracciones.multiplicacion(frAux4, matriz_2[1][i]);
            matrizGauss[0][3+i] = Fracciones.suma(matriz[2][i], matrizGauss[0][3+i]);
        }
        
        //
        Fracciones frAux6;
        frAux6 = new Fracciones(matriz[2][2].GetDenominador(), matriz[2][2].GetDenominador());
    
        matrizGauss[2][2] = Fracciones.multiplicacion(frAux3, matriz[1][2]);
        
        for (int i=0; i<matriz_2[2].length; i++) {
            matrizGauss[2][3+i] = Fracciones.multiplicacion(frAux6, matriz_2[2][i]);
        }
        
        // Numeros fraccionales (objeto Fracciones) auxiliares. frAux7;
        Fracciones frAux7;
        frAux7 = new Fracciones(-(matriz[0][2].GetNumerador()), matriz[0][2].GetDenominador());
        
        // Sacamos el 0 necesario para matrizGauss[0][2] y hacemos las multiplicaciones
        matrizGauss[0][2] = Fracciones.multiplicacion(frAux7, matriz[2][2]);
        matrizGauss[0][2] = Fracciones.suma(matriz[0][2], matrizGauss[0][2]);
        
        for (int i=0; i<matriz_2[0].length; i++) {
            matrizGauss[0][3+i] = Fracciones.multiplicacion(frAux7, matriz_2[2][i]);
            matrizGauss[0][3+i] = Fracciones.suma(matriz_2[0][i], matrizGauss[0][3+i]);
        }
        
        // Numeros fraccionales (objeto Fracciones) auxiliares. frAux5;
        Fracciones frAux8;
        frAux8 = new Fracciones(-(matriz[1][1].GetNumerador()), matriz[1][1].GetDenominador());
        
        // Sacamos el 0 necesario para matrizGauss[2][1] y hacemos las multiplicaciones
        matrizGauss[1][2] = Fracciones.multiplicacion(frAux8, matriz[2][2]);
        matrizGauss[1][2] = Fracciones.suma(matriz[2][1], matrizGauss[1][2]);
        
        for (int i=0; i<matriz_2[1].length; i++) {
            matrizGauss[1][3+i] = Fracciones.multiplicacion(frAux7, matriz_2[2][i]);
            matrizGauss[1][3+i] = Fracciones.suma(matriz[1][i], matrizGauss[1][3+i]);
        }
        
        return matrizGauss;
    } */
    
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
    
    // Metodo para mostrar X, Y y Z de una matriz mediante el metodo de Gauss Jordan.
    public String metodoGaussJordan()
    {
        Fracciones[][] gj = this.usarGaussJordan();
        return "X="+gj[0][this.tamRows]+"  Y="+gj[1][this.tamRows]+"  Z="+gj[2][this.tamRows];
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
        
        // Retorna el valor de X, Y, Z.
        return "X="+x+"  Y="+y+"  Z="+z;
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
        matriz_2 = matrizTra.sacarTraspuesta();
        
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
}
