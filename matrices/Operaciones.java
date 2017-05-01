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
        this.message = "";
    }
    
    // Constructor recibiendo dos matrices necesarias para algunos metodos
    public Operaciones(Matrices matriz, Matrices matriz_2)
    {
        this.tamCols = matriz.getTamCols();
        this.tamRows = matriz.getTamRows();
        this.matriz = matriz.getMatriz();
        this.matriz_2 = matriz_2.getMatriz();
        this.message = "";
    }
    
    /**
     *  Este metodo calcula el determinante de la matriz sin recibir parametros
     *  
     * @param none
     * @return Fracciones	El determinante de la matriz
     */
    public Fracciones calcularDeterminante()
    {
        return this.calcularDeterminante(matriz);
    }
    
    /**
     * Este metodo calcula el determinante de la matriz recibida.
     * 
     * @param Fracciones[][]	 Matriz recibida
     * @return Fracciones 	Determinannte de una matriz
     */
    public Fracciones calcularDeterminante(Fracciones[][] matriz)
    {
    	System.out.println("//-- -- Start calcularDeterminante() -- --//"); // <---- MOSTRAR EN CONSOLA
    	this.setMessage("-- Inicio calcular determiante. - -\n");
        // Comprueba si el número de columnas y filas son iguales
        if(matriz.length == matriz[0].length && matriz.length > 1)
        {
            // Esto se hace porque la unica excepción para sacar una determiante es de 2x2
            int valRepeat = matriz.length;
            if(matriz.length == 2)
                valRepeat = 1;
            
             // Declaramos variables auxiliares
            Fracciones[] fr = new Fracciones[valRepeat];
            Fracciones aux = matriz[0][0]; 
            Fracciones sumar = new Fracciones(0);
            Fracciones restar = new Fracciones(0);
            int count, count2=matriz.length;
            boolean firstCnt=true;
            String messageAux="";
            
            // Repetir 2 veces. Primera vez se suman, la otra restan
            for(int oneTwo=0; oneTwo<2; oneTwo++)
            {
                for(int i=0; i<valRepeat; i++)
                {
                    // Ajustamos el contador dependiendo que en vuelta va el ciclo
                    if(oneTwo==0)
                        count = i;
                    else if(firstCnt) {
                        count=--count2;
                        count2--;
                        firstCnt=false;
                    } else {
                        count=--count2;
                        if(count == -1)
                            count = matriz.length-2;
                    }
                    
                    if(oneTwo == 0 && i != 0) {
                    	this.setMessage(" + ");
                    	messageAux += " + ";
                    } else if(oneTwo == 1) {
                    	this.setMessage(" - ");
                    	messageAux += " - ";
                    }
                    
                    System.out.println("*count: "+count); // <---- MOSTRAR EN CONSOLA
                    // Si es -1 se convierte a 2 ya que aqui el -1 no nos sirve
                    if(count == -1) count=matriz.length-1;
                    System.out.println("count (2): "+count); // <---- MOSTRAR EN CONSOLA
                    // Ciclo para hacer las multiplicaciones
                    for(int h=0; h<matriz[0].length; h++)
                    {
                        System.out.println("    h: "+h); // <---- MOSTRAR EN CONSOLA
                       // Primera vuelta
                        if(h==0) {
                            aux = matriz[h][count];
                            System.out.println("    aux: "+aux); // <---- MOSTRAR EN CONSOLA
                            this.setMessage("( "+aux);
                        // Ultima vuelta
                        } else if(h==matriz[0].length-1) {
                            fr[i] = Fracciones.multiplicacion(aux, matriz[h][count]);
                            System.out.println("    fr["+i+" ( i )]: "+fr[i]); // <---- MOSTRAR EN CONSOLA
                            this.setMessage("x "+matriz[h][count]+")");
                            messageAux += "("+fr[i]+")";
                        // Si no es primer ni ultima vuelta
                        } else  {
                            aux = Fracciones.multiplicacion(aux, matriz[h][count]);
                            System.out.println("    aux (2): "+aux); // <---- MOSTRAR EN CONSOLA
                            this.setMessage("x "+matriz[h][count]);
                        }
                        
                        // Convierte a 0 o suma o resta la variable count dependiendo el caso
                        if(count == matriz.length-1 && oneTwo == 0) {
                            count = 0; 
                            System.out.println("    count (3): "+count+"\n"); // <---- MOSTRAR EN CONSOLA
                        } else if(oneTwo==0) {
                            count++;
                            System.out.println("count++: "+count); // <---- MOSTRAR EN CONSOLA
                        } else {
                            count--;
                            System.out.println("Count--: "+count); // <---- MOSTRAR EN CONSOLA
                        }
                        
                        if(count < 0) {
                            count = matriz.length-1;
                        }
                    }
                }
                
                System.out.println("fr"+Arrays.toString(fr)); //<----
                // Si es la primer vuelta sumara todo
                if(oneTwo==0 && valRepeat != 1) {
                    sumar = Fracciones.suma(fr);
                // Condicional especial para matriz 2x2. Suma
                } else if(oneTwo==0 && valRepeat == 1) {
                    sumar = fr[0];
                // Condicional especial para matriz 2x2. Resta
                } else if(valRepeat == 1){
                    restar = new Fracciones(-fr[0].GetNumerador(), fr[0].GetDenominador());
                // Si es la segunda vuelta restara todo
                } else {
                    fr[0] = new Fracciones(-fr[0].GetNumerador(), fr[0].GetDenominador());
                    restar = Fracciones.resta(fr);
                }
            } 
            
            System.out.println("Suma: "+sumar); // <---- MOSTRAR EN CONSOLA
            System.out.println("Resta: "+restar); // <---- MOSTRAR EN CONSOLA
            
            Fracciones result = Fracciones.suma(sumar, restar);
            this.setMessage("\n"+messageAux+ " = "+result);
            
            System.out.println("//-- -- Finish calcularDeterminante() -- --//"); // <---- MOSTRAR EN CONSOLA
            this.setMessage("\n--- Fin calcular determinante ---\n\n");
            // Retorna el resultado final simplificado (La determinante)
            return result;
        }
        JOptionPane.showMessageDialog(null, "Las filas y las columnas deben ser iguales.");
        return null;
    }
    
    /**
     * Este metodo comprueba si hay algun 0 o 1 en un arreglo
     * 
     * @param int	check. Arreglo de enteros a checar
     * @param int 	type. Si verificara los ceros (0) o los unos (1)
     * @return boolean
     */
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
    
    /**
     * Metodo que reseta el arreglo de enteros recibido (Todo el arreglo a 0)
     * 
     * @param int[]		check. Arreglo a cambiar sus valores a 0
     * @return void		No regresa valor.
     */
    public void resetChecked(int[] check)
    {
        for(int i=0; i<check.length; i++)
        {
            check[i] = 0;
        }
    }
    
    /**
     * Método para crear una nueva matriz juntando dos matrices
     * Matriz + Matriz_2
     * 
     * @param Fracciones[][]	 mtzG Matriz en la que se guardaran los datos
     * @return void
     */
    public void createFirstGJ(Fracciones[][] mtzG)
    {
        for(int rows=0; rows<mtzG.length; rows++)
        {
            for(int cols=0; cols<mtzG[0].length; cols++)
            {
                if(cols < tamCols)
                    mtzG[rows][cols] = matriz[rows][cols];
                else
                    mtzG[rows][cols] = matriz_2[rows][cols-tamCols];
            }
        }
    }
    
    /**
     * Método de Gauss Jordan. El resultado final es la matriz ya procesada por el metodo de Gauss Jordan
     * 
     * @param none
     * @return Fracciones[][]	Matriz resultante al usar el método de Gauss Jordan.
     */
    public Fracciones[][] usarGaussJordan()
    {
    	System.out.println("//-- -- Start usarGaussJordan() -- --//"); // <---- MOSTRAR EN CONSOLA
    	this.setMessage("-- Inicio Gauss-Jordan --\n\n");
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
                    	this.setMessage("Matriz:\n");
                    	this.setMessage(this.toStringMatriz(matrizGauss)+"\n");
                    	
                        // Se saca la fraccion auxiliar por la cual se multiplicara toda una fila
                        frAux[h] = new Fracciones(matrizGauss[i][h].GetDenominador(), matrizGauss[i][h].GetNumerador());
                        System.out.println("* frAux["+h+" (h)]: "+frAux[h]); // <---- MOSTRAR EN CONSOLA
                        this.setMessage("Renglón "+(h+1)+" por "+frAux[h]+"\n");
                        // Realizamos un ciclo para pasar por toda un renglon o fila de la matriz
                        for(int j=0; j<matrizGauss[0].length; j++)
                        {
                            System.out.println("    j (1): "+j); // <---- MOSTRAR EN CONSOLA
                            // Multiplicamos fracciones, la auxiliar y la que se encuentra en la matriz original
                            matrizGauss[h][j] = Fracciones.multiplicacion(frAux[h], matrizGauss[h][j]);
                            System.out.println("    - matrizGauss["+h+" (h)]["+j+" (j)]: "+matrizGauss[h][j]); // <---- MOSTRAR EN CONSOLA
                            
                            if(j < matrizGauss[0].length-1)
                            	this.setMessage(matrizGauss[h][j]+" ");
                            else
                            	this.setMessage("| "+matrizGauss[h][j]+"\n");
                        }
                        
                        // Cambiamos que se ha realizado una accion de 3
                        checkAll[h] = 1;
                        System.out.println("#### checkAll["+h+" (h)]: "+checkAll[h]); // <---- MOSTRAR EN CONSOLA
                        this.setMessage("\n");
                    }
                }
                else
                {
                    // Se saca la fraccion auxiliar por la cual se multiplicara toda una fila y luego se sumara otra fila
                    frAux[h] = new Fracciones(-(matrizGauss[h][i].GetNumerador()), matrizGauss[h][i].GetDenominador());
                    this.setMessage("Renglón "+(i+1)+" por ("+frAux[h]+") más renglón "+(h+1)+"\n");
                    for(int j=0; j<matrizGauss[0].length; j++)
                    {
                        System.out.println("   j(2): "+j); // <---- MOSTRAR EN CONSOLA
                        System.out.println("    frAux["+h+" (h)]: "+frAux[h]+" ---  matrizGauss["+i+" (i)]["+j+" (j)]: "+matrizGauss[i][j]);
                        Fracciones auxGJ = Fracciones.multiplicacion(frAux[h], matrizGauss[i][j]);
                        System.out.println("    $ auxGJ: "+auxGJ); // <---- MOSTRAR EN CONSOLA
                        
                        matrizGauss[h][j] = Fracciones.suma(auxGJ, matrizGauss[h][j]);
                        System.out.println("    $ matrizGauss["+h+" (h)]["+j+" (j)]: "+matrizGauss[h][j]); // <---- MOSTRAR EN CONSOLA
                        
                        if(j < matrizGauss[0].length-1)
                        	this.setMessage(matrizGauss[h][j]+" ");
                        else
                        	this.setMessage("| "+matrizGauss[h][j]+"\n");
                    }
                    
                    // Cambiamos que se ha realizado una accion más de 3
                    checkAll[h] = 1;
                    System.out.println("#### checkAll["+h+" (h)]: "+checkAll[h]+"\n"); // <---- MOSTRAR EN CONSOLA
                    this.setMessage("\n");
                }
                
                // Si h es igual a 2 se cambia a 0 sino se suma uno a h (valores de h: 0,1,2)
                if(h == 2)
                    h=0;
                else
                    h++;
            }
        }
        this.setMessage("Matriz final:\n");
        this.setMessage(this.toStringMatriz(matrizGauss)+"\n");
        
        System.out.println("//-- -- Finish usarGaussJordan() -- --//"); // <---- MOSTRAR EN CONSOLA
        this.setMessage("--- Fin Gauss-Jordan ---\n");
        return matrizGauss;
    }
    
    /**
     * Método que regresa en cadena de caracteres (String) los valores resultante de 
     * utilizar el método de Gauss Jordan con una matriz.
     * 
     * @param none
     * @return String 	Valores finales de la matriz usada con el metodo de Gauss Jordan. X, Y, Z.
     */
    public String toStringGaussJordan()
    {
        Fracciones[][] gj = this.usarGaussJordan();
        return "X="+gj[0][this.tamRows]+"  Y="+gj[1][this.tamRows]+"  Z="+gj[2][this.tamRows];
    }
    
    
    /**
     * Método que regresa en un arreglo de Fracciones el valor de X,Y,Z.
     * 
     * @param none
     * @return Fracciones[] 	Valores finales de la matriz usada con el metodo de Gauss Jordan. X, Y, Z.
     */
    public Fracciones[] toFraccionesGaussJordan()
    {
        Fracciones[][] gj = this.usarGaussJordan();
        Fracciones[] xyz = new Fracciones[3];
        
        xyz[0] = gj[0][this.tamRows];
        xyz[1] = gj[1][this.tamRows];
        xyz[2] = gj[2][this.tamRows];
        
        return xyz;
    }
    
    /**
     * Método que sirve para sustituir toda una columna de una matriz.
     * 
     * @param int	column. Número de columna
     * @return Fracciones[][]	Matriz resultante de sustituir una columna.
     */
    public Fracciones[][] sustituirColumna(int column)
    {
        // Se crea e inicializa un arreglo auxiliar
        Fracciones[][] nuevaMatriz = new Fracciones[tamRows][tamCols];
        
        for(int rows=0; rows<tamRows; rows++)
        {
            for(int cols=0; cols<tamCols; cols++)
            {
                // Si la columna que se pide sustituir es igual a la actual en el ciclo.
                if(cols == column) {
                    nuevaMatriz[rows][cols] = matriz_2[rows][0];
                    System.out.println("        &nuevaMatriz["+rows+"]["+cols+"]: "+nuevaMatriz[rows][cols]); // <---- MOSTRAR EN CONSOLA
                } else {
                    nuevaMatriz[rows][cols] = matriz[rows][cols];
                    System.out.println("        nuevaMatriz["+rows+"]["+cols+"]: "+nuevaMatriz[rows][cols]); // <---- MOSTRAR EN CONSOLA
                }
            }
        }
        return nuevaMatriz;
    }
    
    /**
     * Método que realiza un operación con una matriz utilizando el metodo de Determinantes.
     * 
     * @param none
     * @return Fracciones[]	
     */
    public Fracciones[] metodoDeterminantes()
    {
    	System.out.println("//-- -- Start metodoDeterminantes() -- --//"); // <---- MOSTRAR EN CONSOLA
    	this.setMessage("-- Inicio metodo Determinantes --\n");
        // Caculamos la determinante total y la guardamos en una variable
        Fracciones dt = this.calcularDeterminante(this.matriz);
        System.out.println("## dt: "+dt); // <---- MOSTRAR EN CONSOLA
        
        // Declaramos varibles de determinante en X, determinante en Y y determiante en Z
        // Fracciones dx, dy, dz;
        Fracciones d;
        Fracciones[] xyz = new Fracciones[this.tamCols];
        // Declaramos el arreglo bidimensional donde se sustituiran columnas
        Fracciones[][] mtzSustituida;
        
        // Ahora sustituimos la columna de resultados en la primer columna y calculamos el determinante
        
        for(int i=0; i<this.tamCols; i++)
        {
        	String abc = this.letterDeter(i);
        	mtzSustituida = this.sustituirColumna(i);
	        System.out.println("//("+i+"): "+Arrays.deepToString(mtzSustituida)); // <---- MOSTRAR EN CONSOLA
	        this.setMessage(this.toStringMatriz(mtzSustituida));
	        d = this.calcularDeterminante(mtzSustituida);
	        System.out.println("## D"+abc+": "+d); // <---- MOSTRAR EN CONSOLA
	        this.setMessage("D"+abc+" = "+d+"\n");
	        // Sacamos X dividiendo Dx entre Dt
	        xyz[i] = Fracciones.division(d, dt);
	        this.setMessage(abc+" = D"+abc.toLowerCase()+" / Dt = "+xyz[i]+"\n\n");
    	}
        
        System.out.println("X Y Z: "+Arrays.deepToString(xyz)); // <---- MOSTRAR EN CONSOLA
        this.setMessage("X, Y, Z: "+Arrays.deepToString(xyz));
        System.out.println("//-- -- Finish metodoDeterminantes() -- --//"); // <---- MOSTRAR EN CONSOLA
        this.setMessage("\n--- Fin metodo Determinantes ---\n");
        // Retorna en un arreglo los valores de X, Y, Z.
        return xyz;
    }
    
    public String letterDeter(int num)
    {
    	String letter="";
    	if(this.tamCols == 3)
    	{
	    	switch(num)
	    	{
	    		case 0:
	    			letter="X";
	    			break;
	    		case 1:
	    			letter="Y";
	    			break;
	    		case 2:
	    			letter="Z";
	    			break;
	    	}
    	} else {
    		letter = (num+1)+"";
	    }
    	return letter;
    }
    
    // Metodo para sacar la inversa por el metodo de Gauss Jordan
    public Fracciones[][] inversaGaussJordan()
    {
    	this.setMessage("-- Inicio Inversa Gauss-Jordan --\n");
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
                Fracciones[][] gj = this.usarGaussJordan();
                this.setMessage("-- Fin Inversa Gauss-Jordan --");
                return gj;
            } 
            else
                JOptionPane.showMessageDialog(null, "Error. Las filas y columnas de la matriz deben ser iguales.");
        }
        else
            JOptionPane.showMessageDialog(null, "Error. El determinante es 0 por lo tanto no se puede sacar la inversa");
        
        // Si retorna este valor 'null' es que hubo un error y anteriormente debio mostrar un mensaje de error.
        return null;
    }
    
    public String toStringInvGJ()
    {
        Fracciones[][] invGJ = this.inversaGaussJordan();
        String message = "";
        for (Fracciones[] invGJ1 : invGJ)
        {
            for (int cols=tamCols; cols<invGJ[0].length; cols++) {
                if(cols != tamCols)
                    message += " ";
                message += invGJ1[cols];
            }
            message += "\n";
        }
        
        return message;
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
    	this.setMessage("-- Inicio Inversa Cofactores--\n");
    	// Guardar en una variable la determinante de A
        Fracciones dtA = this.calcularDeterminante(matriz);
        // Crear la nueva matriz auxiliar (2x2)
        Fracciones[][] mtzCof = new Fracciones[this.tamRows-1][this.tamCols-1];
        // Crear la matriz adjunta de B 
        Fracciones[][] adjB = new Fracciones[this.tamRows][this.tamCols];
        
        // Enteros auxiliares inicializados en 0 de columnas y filas
        int rows2 = 0;
        int cols2 = 0;
        
        // Comprobar que determinante es diferente a 0
        if(dtA.GetNumerador() != 0)
        {
        	// Empezar con los cofactores (Filas)
            for(int rowsCof=0; rowsCof<tamRows; rowsCof++)
            {
            	// Cofactores (Columas)
                for(int colsCof=0; colsCof<tamCols; colsCof++)
                {
                	System.out.println(" -- -- -- -- -- -- -- -- -- -- -- -- "); // <---- MOSTRAR EN CONSOLA
                	// Empezar con la nueva matriz de los cofactores (Filas)
                    for(int rows=0; rows<tamRows; rows++)
                    {
                    	// Matriz cofactores (columnas)
                        for(int cols=0; cols<tamCols; cols++)
                        {
                            if(rows != rowsCof && cols != colsCof) {
                                mtzCof[rows2][cols2] = matriz[rows][cols];
                            	System.out.println("mtzCof[rows2 ("+rows2+")][cols2 ("+cols2+")]: "+mtzCof[rows2][cols2]); // <---- MOSTRAR EN CONSOLA
                       
                            	cols2++;
                            	if(cols2 >= mtzCof[0].length)
                            		cols2=0;
                            }
                        }
                        if(rows != rowsCof)
                        	rows2++;
                        if(rows2 >= mtzCof.length)
                    		rows2=0;
                    }
                    
                    Fracciones dtMtzCof = this.calcularDeterminante(mtzCof);
                    System.out.println("Determiante de mtzCof: "+dtMtzCof); // <--- MOSTRAR EN CONSOLA
                    if((rowsCof+colsCof) % 2 == 0) {
                        adjB[rowsCof][colsCof] = Fracciones.multiplicacion(new Fracciones(1), dtMtzCof);
                        this.setMessage("A"+rowsCof+colsCof+": ( + )\n");
                    } else {
                        adjB[rowsCof][colsCof] = Fracciones.multiplicacion(new Fracciones(-1), dtMtzCof);
                        this.setMessage("A"+rowsCof+colsCof+"= ( - )\n");
                    }
                    this.setMessage(this.toStringMatriz(mtzCof));
                    System.out.println("adjB[rowsCof ("+rowsCof+")][colsCof ("+colsCof+")]: "+adjB[rowsCof][colsCof]); // <---- MOSTRAR EN CONSOLA
                    this.setMessage("A"+rowsCof+colsCof+": "+adjB[rowsCof][colsCof]+"\n");
                }
            }
        }
        this.setMessage("B = \n"+this.toStringMatriz(adjB));
        // Sacamos la trasversal de adjB
        Matrices matrizTra = new Matrices(adjB, 1, 1);
        // Aquí ya sacamos la adjunta de B
        matriz_2 = matrizTra.sacarTraspuesta();
        this.setMessage("Adj A =\n"+this.toStringMatriz(matrizTra.getMatriz()));
        System.out.println("Hecho: Matriz traspuesta."); // <---- MOSTRAR EN CONSOLA.
        
        this.setMessage("( "+dtA.toString()+" )\n");
        this.setMessage(this.toStringMatriz(matriz_2));
        Fracciones multp = new Fracciones(1, dtA.GetNumerador());
        // Mutiplicar la matriz_2 por el determinante
        Matrices matrizFinal = new Matrices(matriz_2, multp, new Fracciones(1));
        this.setMessage("Obtenemos la matriz inversa.\n");
        this.setMessage(this.toStringMatriz(matrizFinal.getMatriz()));
        return matrizFinal.getMatriz();
    }
    
     public String toStringInvCofactores()
    {
        Fracciones[][] invCofs = this.inversaCofactores();
        String message = "";
        for (Fracciones[] inversa : invCofs)
        {
            for (int cols=0; cols<invCofs[0].length; cols++) {
                if(cols != 0)
                    message += " ";
                message += inversa[cols];
            }
            message += "\n";
        }
        return message;
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
                if(cols == rows)
                    ident[cols][rows] = new Fracciones(1);
                else
                    ident[cols][rows] = new Fracciones(0);
            }
        }
        return ident;
    }
    
    public String toStringMatriz()
    {
    	return this.toStringMatriz(this.matriz);
    }
    
    public String toStringMatriz(Fracciones[][] mtz)
    {
        String message = "";
        for (Fracciones[] rows : mtz)
        {
            for (int cols=0; cols<mtz[0].length; cols++) 
            {
                if(cols != 0)
                    message += " ";
                if(tamCols == cols)
                	message += " | ";
                
                message += rows[cols];
            }
            message += "\n";
        }
        
        return message;
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
    
    public void setMessage(String msge)
    {
        this.message += msge;
    }
    
    public String getMessage()
    {
        return this.message;
    }
}
