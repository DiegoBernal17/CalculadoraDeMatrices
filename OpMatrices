package matrices;

/**
 *
 * @author Diego Bernal
 */
import java.util.Arrays;
import javax.swing.JOptionPane;

public class OpMatrices 
{
    private int[][] opMatriz;
    private Matrices matriz_1;
    private Matrices matriz_2;
    private int tamRows;
    private int tamCols;
    private char op;
    private String message;
    
    public OpMatrices(Matrices ma_1, Matrices ma_2, char operacion)
    {
        this.matriz_1 = ma_1;
        this.matriz_2 = ma_2;
        this.tamRows = ma_1.getTamRows();
        this.tamCols = ma_2.getTamCols();
        this.op = operacion;
        this.opMatriz = new int[this.tamRows][this.tamCols];
        this.message = "";
    }
    
    public boolean calcular()
    {
        if(op == '+' || op == '-')
        {
            if(matriz_1.getTam() == null ? matriz_2.getTam() == null : matriz_1.getTam().equals(matriz_2.getTam()))
            {
                if(op=='+')
                    this.sumarMatrices();
                else if(op=='-')
                    this.restarMatrices();
                
                return true;
            }
            else
                JOptionPane.showMessageDialog(null, "Las matrices no son del mismo tamaño para poder sumarlas", "Error al sumar", JOptionPane.ERROR_MESSAGE);
        }
        else if(op == '*')
        {
            if(matriz_1.getTamCols() == matriz_2.getTamRows())
            {
                this.multiplicarMatrices();
                return true;
            }
            else
                JOptionPane.showMessageDialog(null, matriz_1.getTam()+" y "+matriz_2.getTam()+" no se puede multiplicar.");
        }
        
        return false;
    }
    
    public void sumarMatrices()
    {
        int val;
        this.setMessage("Operación de sumar.\n");
        for(int i=0;i<this.tamRows;i++)
        {
            for(int j=0;j<this.tamCols;j++)
            {
                val = matriz_1.getMatriz(i,j);
                opMatriz[i][j] = matriz_1.getMatriz(i,j) + matriz_2.getMatriz(i,j);
                this.setMessage(val+" + "+matriz_2.getMatriz(i,j)+" = "+opMatriz[i][j]);
            }
        }
        this.setMessage("\n---Fin suma---\n");
    }
    
    public void restarMatrices()
    {
        this.setMessage("Operación de restar\n");
        for(int i=0;i<this.tamRows;i++)
        {
            for(int j=0;j<this.tamCols;j++)
            {
                opMatriz[i][j] = matriz_1.getMatriz(i,j) - matriz_2.getMatriz(i,j);
                this.setMessage(matriz_1.getMatriz(i,j)+" - "+matriz_2.getMatriz(i,j)+" = "+opMatriz[i][j]);
            }
        }
        this.setMessage("\n---Fin resta---\n");
    }
    
    public void multiplicarMatrices()
    {
        this.setMessage("Operacion multiplicar.\n");
        for(int i=0; i<tamRows; i++)
        {
            for(int j=0; j<tamCols; j++)
            {
                opMatriz[i][j] = multiplicar(i, j);
            }
        }
        this.setMessage("\n---Fin multiplicar---\n");
    }
    
    public int multiplicar(int a, int b)
    {
        String aux1 = "";
        String aux2 = "";
        int result = 0;
        for(int i=0;i<matriz_1.getTamCols();i++)
        {
            int multip = this.matriz_1.getMatriz(a, i)*this.matriz_2.getMatriz(i, b);
            result += multip;
            // this.verOperaciones(a, b, i, multip);
            aux1 += "("+this.matriz_1.getMatriz(a, i)+" x "+this.matriz_2.getMatriz(i, b)+")";
            aux2 += multip;
            if(i < (matriz_1.getTamCols()-1))
            {
                aux2 += " + ";
                aux1 += " + ";
            }
        }
        String aux = aux1+" = "+aux2+" = "+result;
        this.setMessage(aux);
        return result;
    }
    
    public String mostrarMatrices()
    {
        String out = "";
        for (int[] viewMat : opMatriz) {
            for (int j = 0; j < viewMat.length; j++) {
                out += viewMat[j] + " ";
            }
            out += "\n";
        }
        return out;
    }
    
    public void mostrarMatricesDialogo()
    {
        String a = matriz_1.mostrarMatriz("A");
        String b = matriz_2.mostrarMatriz("B");
        String out = a+b+"A"+op+"B = ";
        for (int[] viewMat : opMatriz) {
            for (int j = 0; j < viewMat.length; j++) {
                out += viewMat[j] + "   ";
            }
            out += "\n           ";
        }
        JOptionPane.showMessageDialog(null, out);
    }
    
    public void verOperaciones(int a, int u, int b, int multip)
    {
        String aux = "";
        for(int i=0; i<tamRows*tamCols; i++)
        {
            if(b==0)
                 aux += "("+this.matriz_1.getMatriz(a, u)+" x "+this.matriz_2.getMatriz(u, b)+" = "+multip+")";
            else if(b%2==0)
                aux += " + ("+this.matriz_1.getMatriz(a, u)+" x "+this.matriz_2.getMatriz(u, b)+" = "+multip+")";
            else
            {
                aux += " "+this.matriz_1.getMatriz(a, u)+" x "+this.matriz_2.getMatriz(u, b)+" = "+multip;
                this.setMessage(aux);
            }
        }
    }
    
    public String getTam()
    {
        return this.tamRows+"x"+this.tamCols;
    }
    
    public int getTamRows()
    {
        return tamRows;
    }
    
    public int getTamCols()
    {
        return tamCols;
    }
    
    public int[][] getMatriz()
    {
        return opMatriz;
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
        return "OpMatrices{" + "opMatriz=" + Arrays.toString(opMatriz) + ", matriz_1=" + matriz_1 + ", matriz_2=" + matriz_2 + ", tamRows=" + tamRows + ", tamCols=" + tamCols + ", op=" + op + '}';
    }
}
