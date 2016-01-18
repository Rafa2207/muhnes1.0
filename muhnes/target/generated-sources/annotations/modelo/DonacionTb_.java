package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.EjemplarDonacionTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-01-15T16:29:27")
@StaticMetamodel(DonacionTb.class)
public class DonacionTb_ { 

    public static volatile ListAttribute<DonacionTb, EjemplarDonacionTb> ejemplarDonacionTbList;
    public static volatile SingularAttribute<DonacionTb, Integer> eIdinstitucion;
    public static volatile SingularAttribute<DonacionTb, Integer> eIddonacion;
    public static volatile SingularAttribute<DonacionTb, Date> fFecha;
    public static volatile SingularAttribute<DonacionTb, String> mDescripcion;

}