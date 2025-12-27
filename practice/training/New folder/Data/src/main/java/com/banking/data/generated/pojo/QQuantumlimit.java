package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QQuantumlimit is a Querydsl query type for QQuantumlimit
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QQuantumlimit extends com.mysema.query.sql.RelationalPathBase<QQuantumlimit> {

    private static final long serialVersionUID = -1199249899;

    public static final QQuantumlimit quantumlimit = new QQuantumlimit("quantumlimit");

    public final StringPath backup = createString("backup");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTime = createString("de_time");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> frLmt = createNumber("fr_lmt", Integer.class);

    public final StringPath lmtHdg = createString("lmt_hdg");

    public final NumberPath<Integer> modTy = createNumber("mod_ty", Integer.class);

    public final NumberPath<Integer> srlNo = createNumber("srl_no", Integer.class);

    public final NumberPath<Integer> toLmt = createNumber("to_lmt", Integer.class);

    public QQuantumlimit(String variable) {
        super(QQuantumlimit.class, forVariable(variable), "null", "quantumlimit");
    }

    @SuppressWarnings("all")
    public QQuantumlimit(Path<? extends QQuantumlimit> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "quantumlimit");
    }

    public QQuantumlimit(PathMetadata<?> metadata) {
        super(QQuantumlimit.class, metadata, "null", "quantumlimit");
    }

}

