package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QIdentifiertype is a Querydsl query type for QIdentifiertype
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QIdentifiertype extends com.mysema.query.sql.RelationalPathBase<QIdentifiertype> {

    private static final long serialVersionUID = -207086945;

    public static final QIdentifiertype identifiertype = new QIdentifiertype("identifiertype");

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final StringPath createdBy = createString("CreatedBy");

    public final DatePath<java.sql.Date> createdDateTime = createDate("CreatedDateTime", java.sql.Date.class);

    public final StringPath description = createString("Description");

    public final NumberPath<Integer> identifierTypeID = createNumber("IdentifierTypeID", Integer.class);

    public final StringPath identifierTypeName = createString("IdentifierTypeName");

    public final StringPath updatedBy = createString("UpdatedBy");

    public final DatePath<java.sql.Date> updatedDateTime = createDate("UpdatedDateTime", java.sql.Date.class);

    public final com.mysema.query.sql.PrimaryKey<QIdentifiertype> primary = createPrimaryKey(identifierTypeID);

    public final com.mysema.query.sql.ForeignKey<QApplication> identifierTypeAPPLICATIONFK = createForeignKey(appID, "AppID");

    public final com.mysema.query.sql.ForeignKey<QObjects> _objectsIdentifierTypeFK = createInvForeignKey(identifierTypeID, "IdentifierTypeID");

    public QIdentifiertype(String variable) {
        super(QIdentifiertype.class, forVariable(variable), "null", "identifiertype");
    }

    @SuppressWarnings("all")
    public QIdentifiertype(Path<? extends QIdentifiertype> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "identifiertype");
    }

    public QIdentifiertype(PathMetadata<?> metadata) {
        super(QIdentifiertype.class, metadata, "null", "identifiertype");
    }

}

