package matrices;
/**
 *
 * @author Diego Bernal
 *
import javax.swing.JOptionPane;

public final class Matrices 
{
    // Variables necesarias declaradas
    private int[][] matriz;
    private int[][] matrizAux;
    private int[][] matriz_inicial;
    private int tamRows;
    private int tamCols;
    private int escalar;
    private int expo;
    private String mat;
    private String message;
    
    // Constructor que recibe las filas, columnas y la matriz en arreglo de numeros
    public Matrices(int rows, int cols, int[][] mtz)
    {
        // Utiliza las tres variables recibidas y las demas las inicaliza en 0 o vacias
        this.matriz_inicial = new int[rows][cols];
        this.matriz_inicial = mtz;
        this.matriz = new int[rows][cols];
        this.matriz = mtz;
        this.tamRows = rows;
        this.tamCols = cols;
        this.escalar = 1;
        this.mat = "";
        this.expo = 0;
        this.message = "";
    }
    
    // Contrustor que recibe la matriz, el escalar y el exponente.
    public Matrices(String mat, String escalar, String expo)
    {
        // Mensaje de error vacia.
        this.message = "";
        // Recibe y guarda la matriz, el escalar y el exponente
        this.mat = mat;
        this.escalar = Integer.parseInt(escalar);
        this.expo = Integer.parseInt(expo);
        
        // Llama al metodo iniciar, para inicializar las otras variables
        this.iniciar();
    }
    
    public void iniciar()
    {
        // llama al metodo obtenerRows() y obtenerCols() para obtener las filas y columnas de la matriz
        this.obtenerRows();
        this.obtenerCols();
        // Inicializa el arreglo de la matriz de salida y la matriz inicial
        this.matriz = new int[this.getTamRows()][this.getTamCols()];
        this.matriz_inicial = new int[this.getTamRows()][this.getTamCols()];
        // Obtiene la matriz recibida (inicial) y la matriz que saldra
        this.obtenerMatrizInicial();
        this.obtenerMatriz();
        // Verifica errores que podrian surgir con la matriz recibida.
        this.verificarMatriz();
    }
    
    public void verificarMatriz()
    {
        for(int i=0; i<this.tamRows; i++)
        {
            // Separa por filas la matriz
            String[] ok = this.getMat().split("\n");
            // Verifica la fila 'i'
            int check = ok[i].split("\\ ").length;
            // Si el tamaño de la fila guardada no coincide con la fila actual revisando
            if(this.tamCols != check)
            {
                JOptionPane.showMessageDialog(null, "Las filas y/o columnas no estan completas o hay espacios", "Matriz incompleta", JOptionPane.ERROR_MESSAGE);
                break;
            }
        }
    }
    
    // Obtiene las filas de la matriz y guarda el numero en una variable
    public void obtenerRows()
    {
        this.tamRows = this.getMat().split("\n").length;
    }
    
    // Obtiene las colulmnas de la matriz y guarda el numero en una variable
    public void obtenerCols()
    {
        String[] ok = this.getMat().split("\n", 0);
        this.tamCols = ok[0].split("\\ ").length;
    }
    
    // Guarda la matriz inicial (Sin operaciones) en una arreglo
    public void obtenerMatrizInicial()
    {
        String[] mtz;
        mtz = mat.split("\\ |\n");
        int cont = 0;
        
        for (int i=0; i<tamRows; i++) 
        {
            for (int j=0; j<tamCols; j++) 
            {
                try
                {
                    this.matriz_inicial[i][j] = Integer.parseInt(mtz[cont]);
                    cont++;
                } 
                catch(NumberFormatException e) 
                {
                    JOptionPane.showMessageDialog(null, "No puedes dejar la matrice A o B vacia", "Matriz vacía", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Error: "+e);
                    break;
                }
            }
        }
    }
    
    // Realiza las operaciones necesarias para mandar la matriz final
    public void obtenerMatriz()
    {
        this.matriz = this.matriz_inicial.clone();
        if(escalar == 1 && expo == 1)
            this.matriz = matriz_inicial;
        else if(escalar > 1 || expo > 1)
        {
            if(escalar > 1 && expo == 1)
                matrizEscalar();
            else if(escalar == 1 && expo > 1)
                matrizExponencial();
            else
                matrizEscaExpo();
        }
        else
            System.out.println("error.");
    }
    
    // Realiza operaciones para obtener las matriz con escalar
    public void matrizEscalar()
    {
        this.setMessage("Operación escalar con: "+this.escalar+"\n");
        matrizAux = new int[this.getTamRows()][this.getTamCols()];
        int val;
        for (int i=0;i<this.tamRows;i++) 
        {
            for (int j = 0; j < matriz[i].length; j++) 
            {
                val = matriz[i][j];
                matrizAux[i][j] = val * escalar;
               this.setMessage(val + " * " + escalar + " = " + matrizAux[i][j]);
            }
        }
        System.arraycopy(matrizAux, 0, matriz, 0, matriz.length);
        this.setMessage("\n---Fin escalar---\n");
    }
    
    // Realiza las operaciones necesarias para obtener la matriz final con exponencial
    public void matrizExponencial()
    {   
        if(this.tamCols == this.tamRows)
        {
            this.setMessage("Operacion exponencial con: "+this.expo+"\n");
            for(int count=1;count<expo;count++)
            {
                matrizAux = new int[this.getTamRows()][this.getTamCols()];
                for(int i=0; i<tamRows; i++)
                {
                    for(int j=0; j<tamCols; j++)
                    {
                        this.matrizAux[i][j] = multiplicar(i, j);
                    }
                }
                System.arraycopy(matrizAux, 0, matriz, 0, matriz.length);
            }
            this.setMessage("\n---Fin exponencial---\n");
        }
        else
            JOptionPane.showMessageDialog(null, "No puede tener exponente esa matriz", "Error con matriz", JOptionPane.ERROR_MESSAGE);
    }
    
    public void matrizEscaExpo()
    {
        this.matrizExponencial();
        this.matrizEscalar();
    }
    
    public int multiplicar(int a, int b)
    {
        String aux1 = "";
        String aux2 = "";
        int result = 0;
        
        for(int i=0;i<tamCols;i++)
        {
            int multip = this.matriz[a][i]*this.matriz_inicial[i][b];
            result += multip;

            aux1 += "("+this.matriz[a][i]+" x "+this.matriz_inicial[i][b]+")";
            aux2 += multip;
            if(i < (this.getTamCols()-1))
            {
                aux2 += " + ";
                aux1 += " + ";
            }
        }
        
        String aux = aux1+" = "+aux2+" = "+result;
        this.setMessage(aux);
        return result;
    }
    
    
    public void pedirValoresJOPane(String mat)
    {
        for(int i=0;i<matriz.length;i++)
        {
            for(int j=0;j<matriz[i].length;j++)
            {
                do {
                    try
                    {
                        int val = Integer.parseInt(JOptionPane.showInputDialog(mat+" matriz: "+this.getTam()+"\nValor de la matriz "+mat+" ["+ (i+1) +"]["+ (j+1) +"]  ?"));
                        matriz[i][j] = val;
                        break;
                    }
                    catch(NullPointerException e)
                    {
                        System.exit(0);
                    }
                    catch(NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Introduce numeros. "+e.getMessage());
                    }
                } while(true);
            }
        }
    }
    
    public String mostrarMatriz(String mat)
    {
        String out = mat+" =  ";
        for (int[] viewMat : matriz) {
            for (int j = 0; j < viewMat.length; j++) {
                out += viewMat[j] + "   ";
            }
            out += "\n";
        }
        return out+= "\n";
    }
    
    public int getMatriz(int i, int j)
    {
        return this.matriz[i][j];
    }
    
    public String getTam()
    {
        return this.tamRows+"x"+this.tamCols;
    }
    
    public int getTamRows()
    {
        return this.tamRows;
    }
    
    public int getTamCols()
    {
        return this.tamCols;
    }
    
    public String getMat()
    {
        return this.mat;
    }
    
    public void setTamRows(int rows)
    {
        this.tamRows = rows;
    }
    
    public void setTamCols(int cols)
    {
        this.tamCols = cols;
    }
    
    public void setMessage(String msge)
    {
        this.message += msge+"\n";
    }
    
    public String getMessage()
    {
        return this.message;
    }

    public String toString() {
        return "***Matrices{" /*+ "matriz=" + Arrays.toString(matriz[0]) + ", tamRows=" + tamRows + ", tamCols=" + tamCols + ", escalar=" + escalar + ", expo=" + expo + ", mat=" + mat + '}';
    //  }
} */

/**
 *
 * @author Diego Bernal
 */
import javax.swing.JOptionPane;
import fraccion.Fracciones;

public final class MatricesFra
{
    // Variables necesarias declaradas
    private Fracciones[][] matriz;
    private Fracciones[][] matrizAux;
    private Fracciones[][] matriz_inicial;
    private int tamRows;
    private int tamCols;
    private Fracciones escalar;
    private Fracciones expo;
    private String mat;
    private String message;
    
    // Constructor que recibe las filas, columnas y la matriz en arreglo de numeros
    public MatricesFra(int rows, int cols, int[][] mtz)
    {
        // Utiliza las tres variables recibidas y las demas las inicaliza en 0 o vacias
        this.matriz_inicial = new Fracciones[rows][cols];
        this.matriz_inicial = Fracciones.convertir(tamCols, tamRows, mtz);
        this.matriz = new Fracciones[rows][cols];
        this.matriz = Fracciones.convertir(tamCols, tamRows, mtz);
        this.tamRows = rows;
        this.tamCols = cols;
        this.escalar = new Fracciones(1);
        this.mat = "";
        this.expo = new Fracciones(1);
        this.message = "";
    }
    
    // Contrustor que recibe la matriz, el escalar y el exponente.
    public MatricesFra(String mat, String escalar, String expo)
    {
        // Mensaje de error vacia.
        this.message = "";
        // Recibe y guarda la matriz, el escalar y el exponente
        this.mat = mat;
        this.escalar = new Fracciones(Integer.parseInt(escalar));
        this.expo = new Fracciones(Integer.parseInt(expo));
        
        // Llama al metodo iniciar, para inicializar las otras variables
        this.iniciar();
    }
    
    public void iniciar()
    {
        // llama al metodo obtenerRows() y obtenerCols() para obtener las filas y columnas de la matriz
        this.obtenerRows();
        this.obtenerCols();
        // Inicializa el arreglo de la matriz de salida y la matriz inicial
        this.matriz = new Fracciones[this.getTamRows()][this.getTamCols()];
        this.matriz_inicial = new Fracciones[this.getTamRows()][this.getTamCols()];
        // Obtiene la matriz recibida (inicial) y la matriz que saldra
        this.obtenerMatrizInicial();
        this.obtenerMatriz();
        // Verifica errores que podrian surgir con la matriz recibida.
        this.verificarMatriz();
    }
    
    public void verificarMatriz()
    {
        for(int i=0; i<this.tamRows; i++)
        {
            // Separa por filas la matriz
            String[] ok = this.getMat().split("\n");
            // Verifica la fila 'i'
            int check = ok[i].split("\\ ").length;
            // Si el tamaño de la fila guardada no coincide con la fila actual revisando
            if(this.tamCols != check)
            {
                JOptionPane.showMessageDialog(null, "Las filas y/o columnas no estan completas o hay espacios", "Matriz incompleta", JOptionPane.ERROR_MESSAGE);
                break;
            }
        }
    }
    
    // Obtiene las filas de la matriz y guarda el numero en una variable
    public void obtenerRows()
    {
        this.tamRows = this.getMat().split("\n").length;
    }
    
    // Obtiene las colulmnas de la matriz y guarda el numero en una variable
    public void obtenerCols()
    {
        String[] ok = this.getMat().split("\n", 0);
        this.tamCols = ok[0].split("\\ ").length;
    }
    
    // Guarda la matriz inicial (Sin operaciones) en una arreglo
    public void obtenerMatrizInicial()
    {
        String[] mtz;
        mtz = mat.split("\\ |\n");
        int cont = 0;
        
        for (int i=0; i<tamRows; i++) 
        {
            for (int j=0; j<tamCols; j++) 
            {
                try
                {
                    this.matriz_inicial[i][j] = new Fracciones(mtz[cont]);
                    cont++;
                } 
                catch(NumberFormatException e) 
                {
                    JOptionPane.showMessageDialog(null, "No puedes dejar la matrice A o B vacia", "Matriz vacía", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Error: "+e);
                    break;
                }
            }
        }
    }
    
    // Realiza las operaciones necesarias para mandar la matriz final
    public void obtenerMatriz()
    {
        this.matriz = this.matriz_inicial.clone();
        if(escalar.GetNumerador() == 1 && expo.GetNumerador() == 1)
            this.matriz = matriz_inicial;
        else if(escalar.GetNumerador() > 1 || expo.GetNumerador() > 1)
        {
            if(escalar.GetNumerador() > 1 && expo.GetNumerador() == 1)
                matrizEscalar();
            else if(escalar.GetNumerador() == 1 && expo.GetNumerador() > 1)
                matrizExponencial();
            else
                matrizEscaExpo();
        }
        else
            System.out.println("error.");
    }
    
    // Realiza operaciones para obtener las matriz con escalar
    public void matrizEscalar()
    {
        this.setMessage("Operación escalar con: "+this.escalar+"\n");
        matrizAux = new Fracciones[this.getTamRows()][this.getTamCols()];
        
        for (int i=0;i<this.tamRows;i++) 
        {
            for (int j = 0; j < matriz[i].length; j++) 
            {
                matrizAux[i][j] = Fracciones.multiplicacion(matriz[i][j], escalar);
               this.setMessage(matriz[i][j].toString() + " * " + escalar + " = " + matrizAux[i][j].toString());
            }
        }
        System.arraycopy(matrizAux, 0, matriz, 0, matriz.length);
        this.setMessage("\n---Fin escalar---\n");
    }
    
    // Realiza las operaciones necesarias para obtener la matriz final con exponencial
    public void matrizExponencial()
    {   
        if(this.tamCols == this.tamRows)
        {
            this.setMessage("Operacion exponencial con: "+this.expo+"\n");
            for(int count=1;count<expo.GetNumerador();count++)
            {
                matrizAux = new Fracciones[this.getTamRows()][this.getTamCols()];
                for(int i=0; i<tamRows; i++)
                {
                    for(int j=0; j<tamCols; j++)
                    {
                        this.matrizAux[i][j] = multiplicar(i, j);
                    }
                }
                System.arraycopy(matrizAux, 0, matriz, 0, matriz.length);
            }
            this.setMessage("\n---Fin exponencial---\n");
        }
        else
            JOptionPane.showMessageDialog(null, "No puede tener exponente esa matriz", "Error con matriz", JOptionPane.ERROR_MESSAGE);
    }
    
    public void matrizEscaExpo()
    {
        this.matrizExponencial();
        this.matrizEscalar();
    }
    
    public Fracciones multiplicar(int a, int b)
    {
        String aux1 = "";
        String aux2 = "";
        Fracciones result = new Fracciones(0);
        
        for(int i=0;i<tamCols;i++)
        {
            Fracciones multip = Fracciones.multiplicacion(this.matriz[a][i], this.matriz_inicial[i][b]);
            result = Fracciones.suma(result, multip);

            aux1 += "("+this.matriz[a][i].toString()+" x "+this.matriz_inicial[i][b].toString()+")";
            aux2 += multip;
            if(i < (this.getTamCols()-1))
            {
                aux2 += " + ";
                aux1 += " + ";
            }
        }
        
        String aux = aux1+" = "+aux2+" = "+result;
        this.setMessage(aux);
        return result;
    }
    
    /*
    public void pedirValoresJOPane(String mat)
    {
        for(int i=0;i<matriz.length;i++)
        {
            for(int j=0;j<matriz[i].length;j++)
            {
                do {
                    try
                    {
                        int val = Integer.parseInt(JOptionPane.showInputDialog(mat+" matriz: "+this.getTam()+"\nValor de la matriz "+mat+" ["+ (i+1) +"]["+ (j+1) +"]  ?"));
                        matriz[i][j] = val;
                        break;
                    }
                    catch(NullPointerException e)
                    {
                        System.exit(0);
                    }
                    catch(NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Introduce numeros. "+e.getMessage());
                    }
                } while(true);
            }
        }
    }*/
    
    public String mostrarMatriz(String mat)
    {
        String out = mat+" =  ";
        for (Fracciones[] viewMat : matriz) {
            for (int j = 0; j < viewMat.length; j++) {
                out += viewMat[j].toString() + "   ";
            }
            out += "\n";
        }
        return out+= "\n";
    }
    
    public Fracciones getMatriz(int i, int j)
    {
        return this.matriz[i][j];
    }
    
    public String getTam()
    {
        return this.tamRows+"x"+this.tamCols;
    }
    
    public int getTamRows()
    {
        return this.tamRows;
    }
    
    public int getTamCols()
    {
        return this.tamCols;
    }
    
    public String getMat()
    {
        return this.mat;
    }
    
    public void setTamRows(int rows)
    {
        this.tamRows = rows;
    }
    
    public void setTamCols(int cols)
    {
        this.tamCols = cols;
    }
    
    public void setMessage(String msge)
    {
        this.message += msge+"\n";
    }
    
    public String getMessage()
    {
        return this.message;
    }

    @Override
    public String toString() {
        return "***Matrices{" /*+ "matriz=" + Arrays.toString(matriz[0])*/ + ", tamRows=" + tamRows + ", tamCols=" + tamCols + ", escalar=" + escalar.toString() + ", expo=" + expo.toString() + ", mat=" + mat + '}';
    }
}
