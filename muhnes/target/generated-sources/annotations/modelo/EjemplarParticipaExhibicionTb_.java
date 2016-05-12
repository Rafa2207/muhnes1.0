package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.EjemplarParticipaExhibicionTbPK;
import modelo.EjemplarTb;
import modelo.ExhibicionTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-11T14:43:08")
@StaticMetamodel(EjemplarParticipaExhibicionTb.class)
public class EjemplarParticipaExhibicionTb_ { 

    public static volatile SingularAttribute<EjemplarParticipaExhibicionTb, Integer> eEstado;
    public static volatile SingularAttribute<EjemplarParticipaExhibicionTb, EjemplarTb> ejemplarTb;
    public static volatile SingularAttribute<EjemplarParticipaExhibicionTb, EjemplarParticipaExhibicionTbPK> ejemplarParticipaExhibicionTbPK;
    public static volatile SingularAttribute<EjemplarParticipaExhibicionTb, Date> fFecha;
    public static volatile SingularAttribute<EjemplarParticipaExhibicionTb, ExhibicionTb> exhibicionTb;

}