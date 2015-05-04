package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AgenteIdentificaEjemplarTb;
import modelo.InstitucionTb;
import modelo.PerfilTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-05-04T11:45:34")
@StaticMetamodel(AgenteTb.class)
public class AgenteTb_ { 

    public static volatile SingularAttribute<AgenteTb, String> cEmail;
    public static volatile SingularAttribute<AgenteTb, InstitucionTb> eIdinstitucion;
    public static volatile SingularAttribute<AgenteTb, Date> fFecham;
    public static volatile SingularAttribute<AgenteTb, Integer> ePostal;
    public static volatile ListAttribute<AgenteTb, AgenteIdentificaEjemplarTb> agenteIdentificaEjemplarTbList;
    public static volatile SingularAttribute<AgenteTb, String> cApellido;
    public static volatile SingularAttribute<AgenteTb, String> cFax;
    public static volatile SingularAttribute<AgenteTb, Date> fFechanac;
    public static volatile SingularAttribute<AgenteTb, String> cOcupacion;
    public static volatile SingularAttribute<AgenteTb, String> cTelefono;
    public static volatile ListAttribute<AgenteTb, PerfilTb> perfilTbList;
    public static volatile SingularAttribute<AgenteTb, String> cNombre;
    public static volatile SingularAttribute<AgenteTb, String> mDireccion;
    public static volatile SingularAttribute<AgenteTb, String> cIniciales;
    public static volatile SingularAttribute<AgenteTb, Integer> eIdagente;

}