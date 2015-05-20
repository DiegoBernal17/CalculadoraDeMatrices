/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fraccion;

/**
 *
 * @author Bernal
 */
public class Fracciones
{
    private int numerador;
    private int denominador;
    
    public Fracciones(String dato)
    {
        if(dato.contains("/"))
            {
                //entra cuando hay un slash en el string
                this.numerador = Integer.parseInt(dato.substring(0,dato.indexOf("/")));
                this.denominador = Integer.parseInt(dato.substring(dato.indexOf("/")+1));
            }
        else
            {
                this.numerador = Integer.parseInt(dato);
                this.denominador = 1;
            }  
    }
    
    public Fracciones()
    {  
        this.numerador = 0;
        this.denominador = 1;
    }
    
    public Fracciones(final int n)
    {
        this.numerador = n;
        this.denominador = 1;
    }
    
    public Fracciones(final int n, final int d)
    {
        if(d==0)
        {
            System.out.println("Error el denominador no puede ser cero");
        }
        this.numerador = n;
        this.denominador = d;
    }
   
 
    public static Fracciones suma(Fracciones f1, Fracciones f2)
    {
        int numerador, denominador;
        
        numerador =(f1.GetNumerador() * f2.GetDenominador()) + (f2.GetNumerador() * f1.GetDenominador());
        denominador = f1.GetDenominador()*f2.GetDenominador();
        return new Fracciones(numerador, denominador);
    }
    
    public static Fracciones suma(Fracciones[] f1)
    {
        Fracciones fraccion;
        fraccion = Fracciones.suma(f1[0], f1[1]);
        
        for(int i=2; i<f1.length; i++)
        {
            fraccion = Fracciones.suma(fraccion, f1[i]);
        }
        return fraccion;
    }
    
    public static Fracciones resta(Fracciones f1, Fracciones f2)
    {
        int numerador, denominador;
        
        numerador = (f1.GetNumerador() * f2.GetDenominador()) - (f2.GetNumerador() * f1.GetDenominador());
        denominador = f1.GetDenominador() * f2.GetDenominador();
        return new Fracciones(numerador, denominador);
    }
    
    public static Fracciones resta(Fracciones[] f1)
    {
        Fracciones fraccion;
        fraccion = Fracciones.resta(f1[0], f1[1]);
        
        for(int i=2; i<f1.length; i++)
        {
            fraccion = Fracciones.resta(fraccion, f1[i]);
        }
        return fraccion;
    }
    
    public static Fracciones  multiplicacion(Fracciones f1, Fracciones f2)
    {
        int numerador, denominador;
        
        numerador = f1.GetNumerador() * f2.GetNumerador();
        denominador = f1.GetDenominador() * f2.GetDenominador();
        return new Fracciones(numerador, denominador);
    }
    
    public static Fracciones division(Fracciones f1, Fracciones f2)
    {
        int numerador, denominador;
        
        numerador = f1.GetNumerador() * f2.GetDenominador();
        denominador = f1.GetDenominador() * f2.GetNumerador();
        int[] fr = f1.simpli(numerador, denominador);
        return new Fracciones(fr[0], fr[1]);
    }
 
    public void simpli()
    {
        //simplificacion...    
        int a,b,mod;
        
        if(this.numerador%this.denominador == 0)
        {
            this.numerador = this.numerador/this.denominador;
            this.denominador = 1;
        }
        if(this.numerador > this.denominador)
        {
            a = this.numerador;
            b = this.denominador;
        }
        else
        {
            a = this.denominador;
            b = this.numerador;
        }
        while(b != 0)
        {
            mod=a%b;
            a=b;
            b=mod;
        }  
        this.denominador = this.denominador/a;
        this.numerador = this.numerador/a;
    }
    
    public int[] simpli(int numerador, int denominador)
    {
        //simplificacion...    
        int a,b,mod;
        int[] result = new int[2];
                
        if(numerador%denominador == 0)
        {
            numerador = numerador/denominador;
            denominador = 1;
            result[0] = numerador;
            result[1] = denominador;
            return result;
        }
        if(numerador > denominador)
        {
            a = numerador;
            b = denominador;
        }
        else
        {
            a = denominador;
            b = numerador;
        }
        while(b != 0)
        {
            mod=a%b;
            a=b;
            b=mod;
        }
        
        result[0]= numerador/a;
        result[1] = denominador/a;
        return result;
    }
    
    public static Fracciones[][] convertir(int tamCols, int tamRows, int[][] mtz)
    {
        Fracciones[][] nuevaMatriz = new Fracciones[tamCols][tamRows];
        for(int rows=0; rows<mtz.length; rows++)
        {
            for(int cols=0; cols<mtz[rows].length; cols++)
            {
                nuevaMatriz[rows][cols] = new Fracciones(mtz[rows][cols]);
            }
        }
        return nuevaMatriz;
    }
 
    public void printf()
    {
        System.out.print(this.numerador+((this.denominador==1)?" ":"/"+this.denominador));  
    }
    public int  GetNumerador()
    {
        return numerador;
    }
    public int GetDenominador()
    {
        return denominador;
    }
    
    public String toString() 
    {
        return this.numerador+((this.denominador==1)?" ":"/"+this.denominador);
    }
}
