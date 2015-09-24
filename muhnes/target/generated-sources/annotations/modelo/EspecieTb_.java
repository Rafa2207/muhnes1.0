package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AgenteEspecieTb;
import modelo.EjemplarTb;
import modelo.ImagenTb;
import modelo.NombrecomunTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-23T23:35:28")
@StaticMetamodel(EspecieTb.class)
public class EspecieTb_ { 

    public static volatile SingularAttribute<EspecieTb, Integer> eIdgenero;
    public static volatile ListAttribute<EspecieTb, EjemplarTb> ejemplarTbList;
    public static volatile ListAttribute<EspecieTb, AgenteEspecieTb> agenteEspecieTbList;
    public static volatile ListAttribute<EspecieTb, NombrecomunTb> nombrecomunTbList;
    public static volatile SingularAttribute<EspecieTb, String> cNombre;
    public static volatile ListAttribute<EspecieTb, ImagenTb> imagenTbList;
    public static volatile SingularAttribute<EspecieTb, Integer> eIdespecie;
    public static volatile SingularAttribute<EspecieTb, String> cEstado;

}