
package controlador.util;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import modelo.DepartamentoTb;

/**
 *
 * @author Endy
 */
@Named(value = "controladorRequest")
@RequestScoped
public class ControladorRequest {
    private DepartamentoTb departamento;

    public DepartamentoTb getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DepartamentoTb departamento) {
        this.departamento = departamento;
    }
    
    
    

    /**
     * Creates a new instance of ControladorRequest
     */
    public ControladorRequest() {
    }
    
}
