package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.EspecieTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-04T09:01:02")
@StaticMetamodel(ImagenTb.class)
public class ImagenTb_ { 

    public static volatile SingularAttribute<ImagenTb, byte[]> iImagen;
    public static volatile SingularAttribute<ImagenTb, Integer> eIdimagen;
    public static volatile SingularAttribute<ImagenTb, String> cNombre;
    public static volatile SingularAttribute<ImagenTb, EspecieTb> eIdespecie;

}