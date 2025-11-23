package client;

import compute.Task;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Pi implements Task<BigDecimal>, Serializable {
    
    private static final long serialVersionUID = 227L;
    
    /** constantes utilizadas en el cálculo de pi */
    private static final BigDecimal CUATRO = BigDecimal.valueOf(4);
    
    /** modo de redondeo a utilizar durante el cálculo de pi */
    private static final int roundingMode = BigDecimal.ROUND_HALF_EVEN;
    
    /** dígitos de precisión después del punto decimal */
    private final int digits;
    
    /**
     * Construir una tarea para calcular pi con la precisión especificada.
     */
    public Pi(int digits) {
        this.digits = digits;
    }
    
    /**
     * Calcular pi.
     */
    public BigDecimal execute() {
        return computePi(digits);
    }
    
    /**
     * Calcular el valor de pi hasta el número especificado de
     * dígitos después del punto decimal, usando la fórmula de Machin.
     */
    public static BigDecimal computePi(int digits) {
        int scale = digits + 5;
        BigDecimal arctan1_5 = arctan(5, scale);
        BigDecimal arctan1_239 = arctan(239, scale);
        
        // pi/4 = 4*arctan(1/5) - arctan(1/239)
        BigDecimal pi = arctan1_5.multiply(CUATRO).subtract(
                                  arctan1_239).multiply(CUATRO);
        
        // Redondea al número de dígitos solicitado
        return pi.setScale(digits,
                           BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * Calcular arctan(1/inverseX) usando la expansión de la serie de potencias.
     */   
    public static BigDecimal arctan(int inverseX,
                                    int scale)
    {
        BigDecimal resultado, numero, termino;
        BigDecimal invX = BigDecimal.valueOf(inverseX);
        BigDecimal invX2 = BigDecimal.valueOf((long)inverseX * inverseX);
        
        numero = BigDecimal.ONE.divide(invX, scale, roundingMode);
        resultado = numero;
        
        int i = 1;
        do {
            numero = numero.divide(invX2, scale, roundingMode);
            int denominador = 2 * i + 1;
            
            termino = numero.divide(BigDecimal.valueOf(denominador),
                             scale, roundingMode);
            
            if ((i % 2) != 0) {
                resultado = resultado.subtract(termino); // Restar (términos impares)
            } else {
                resultado = resultado.add(termino);    // Sumar (términos pares)
            }
            i++;
        } while (termino.compareTo(BigDecimal.ZERO) != 0);
        
        return resultado;
    }
}