package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLockers is a Querydsl query type for QLockers
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLockers extends com.mysema.query.sql.RelationalPathBase<QLockers> {

    private static final long serialVersionUID = -286772570;

    public static final QLockers lockers = new QLockers("lockers");

    public final NumberPath<Integer> cabNo = createNumber("cab_no", Integer.class);

    public final NumberPath<Integer> colNo = createNumber("col_no", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath descptn = createString("descptn");

    public final NumberPath<Integer> keyNo = createNumber("key_no", Integer.class);

    public final NumberPath<Integer> lockerNo = createNumber("locker_no", Integer.class);

    public final StringPath lockerSt = createString("locker_st");

    public final StringPath lockerTy = createString("locker_ty");

    public final NumberPath<Integer> rowNo = createNumber("row_no", Integer.class);

    public final StringPath siezeInd = createString("sieze_ind");

    public final com.mysema.query.sql.PrimaryKey<QLockers> primary = createPrimaryKey(lockerNo);

    public QLockers(String variable) {
        super(QLockers.class, forVariable(variable), "null", "lockers");
    }

    @SuppressWarnings("all")
    public QLockers(Path<? extends QLockers> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "lockers");
    }

    public QLockers(PathMetadata<?> metadata) {
        super(QLockers.class, metadata, "null", "lockers");
    }

}

