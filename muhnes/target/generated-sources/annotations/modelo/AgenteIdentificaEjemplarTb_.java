package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AgenteIdentificaEjemplarTbPK;
import modelo.AgenteTb;
import modelo.EjemplarTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-30T15:51:51")
@StaticMetamodel(AgenteIdentificaEjemplarTb.class)
public class AgenteIdentificaEjemplarTb_ { 

    public static volatile SingularAttribute<AgenteIdentificaEjemplarTb, String> cTipo;
    public static volatile SingularAttribute<AgenteIdentificaEjemplarTb, Integer> eSecuencia;
    public static volatile SingularAttribute<AgenteIdentificaEjemplarTb, AgenteIdentificaEjemplarTbPK> agenteIdentificaEjemplarTbPK;
    public static volatile SingularAttribute<AgenteIdentificaEjemplarTb, EjemplarTb> ejemplarTb;
    public static volatile SingularAttribute<AgenteIdentificaEjemplarTb, AgenteTb> agenteTb;

}