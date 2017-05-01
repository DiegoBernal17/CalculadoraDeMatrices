package matrices;

/**
 *
 * @author Diego Bernal
 */
import javax.swing.JOptionPane;
import fraccion.Fracciones;

public final class Matrices
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
    public Matrices(int rows, int cols, int[][] mtz)
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
        this.obtenerMatriz();
    }
    
    public Matrices(Fracciones[][] mtz, int escalar, int expo)
    {
        this.tamRows = mtz.length;
        this.tamCols = mtz[0].length;
        
        this.matriz_inicial = new Fracciones[tamRows][tamCols];
        this.matriz_inicial = mtz;
        this.matriz = new Fracciones[tamRows][tamCols];
        this.matriz = mtz;
        this.escalar = new Fracciones(escalar);
        this.mat = "";
        this.expo = new Fracciones(expo);
        this.message = "";
        this.obtenerMatriz();
    }
    
    public Matrices(Fracciones[][] mtz, Fracciones escalar, Fracciones expo)
    {
        this.tamRows = mtz.length;
        this.tamCols = mtz[0].length;
        
        this.matriz_inicial = new Fracciones[tamRows][tamCols];
        this.matriz_inicial = mtz;
        this.matriz = new Fracciones[tamRows][tamCols];
        this.matriz = mtz;
        this.escalar = escalar;
        this.mat = "";
        this.expo = expo;
        this.message = "";
        this.obtenerMatriz();
    }
    
    // Contrustor que recibe la matriz, el escalar y el exponente.
    public Matrices(String mat, String escalar, String expo)
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
                	if(!mtz[cont].equals("")) {
	                    this.matriz_inicial[i][j] = new Fracciones(mtz[cont]);
	                    cont++;
                	} else {
                		throw new Exception("Dato Nulo.");
                	}
                } catch(NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Ingresa solamente numeros.", "No números", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Error: "+e);
                    break;
                } catch(Exception e) {
                	JOptionPane.showMessageDialog(null, "No puedes dejar la matriz A ni B vacia", "Matrices sola", JOptionPane.ERROR_MESSAGE);
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
        if((escalar.GetNumerador() == 1 && escalar.GetDenominador() == 1) && (expo.GetNumerador() == 1 && expo.GetDenominador() == 1))
            this.matriz = matriz_inicial;
        else
        {
            if(expo.GetNumerador() == 1 && expo.GetDenominador() == 1)
                matrizEscalar();
            else if(escalar.GetNumerador() == 1 && escalar.GetDenominador() == 1)
                matrizExponencial();
            else
                matrizEscaExpo();
        }
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
    
    public void convertirTraspuesta()
    {
        if(tamRows == tamCols)
        {
            for(int i=0; i<tamRows; i++)
            {
                for(int j=0; j<tamCols; i++)
                {
                    matriz[j][i] = matriz[i][j];
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Error al sacar matriz traspuesta.\n Las columnas y filas deben ser del mismo tañano");
    }
    
    public Fracciones[][] sacarTraspuesta()
    {
        return this.sacarTraspuesta(matriz);
    }
    
        // Este metodo invierte filas y columnas recibiendo una matrz. Matriz Trasversal
    public Fracciones[][] sacarTraspuesta(Fracciones[][] mtz)
    {
        Fracciones[][] mtzAux = new Fracciones[mtz.length][mtz[0].length];
        if(mtz.length ==  mtz[0].length)
        {
            for(int i=0; i< mtz.length; i++)
            {
                for(int j=0; j<mtz[0].length; j++)
                {
                    mtzAux[j][i] = mtz[i][j];
                }
            }
            return mtzAux;
        }
        JOptionPane.showMessageDialog(null, "Error al sacar matriz traspuesta.\n Las columnas y filas deben ser del mismo tañano");
        return null;
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
    
    public String getTraspuesta()
    {
    	Fracciones[][] tras = this.sacarTraspuesta();
    	
        String message = "";
        for (Fracciones[] trasp1 : tras)
        {
            for (int cols=0; cols<tras[0].length; cols++) {
                message += trasp1[cols]+" ";
            }
            message += "\n";
        }
        
        return message;
    }
    
    public Fracciones[][] getMatriz()
    {
        return this.matriz;
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

    public String toString() {
        return "***Matrices{" /*+ "matriz=" + Arrays.toString(matriz[0])*/ + ", tamRows=" + tamRows + ", tamCols=" + tamCols + ", escalar=" + escalar.toString() + ", expo=" + expo.toString() + ", mat=" + mat + '}';
    }
}
