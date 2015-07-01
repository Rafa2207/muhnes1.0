package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.InstitucionTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T11:59:21")
@StaticMetamodel(DonacionTb.class)
public class DonacionTb_ { 

    public static volatile SingularAttribute<DonacionTb, String> cCalificativo;
    public static volatile SingularAttribute<DonacionTb, String> cTipo;
    public static volatile SingularAttribute<DonacionTb, String> cResponsable;
    public static volatile SingularAttribute<DonacionTb, InstitucionTb> eIdinstitucion;
    public static volatile SingularAttribute<DonacionTb, Integer> eIddonacion;
    public static volatile SingularAttribute<DonacionTb, Integer> eCantduplicados;
    public static volatile SingularAttribute<DonacionTb, Date> fFecha;
    public static volatile SingularAttribute<DonacionTb, String> mDescripcion;

}