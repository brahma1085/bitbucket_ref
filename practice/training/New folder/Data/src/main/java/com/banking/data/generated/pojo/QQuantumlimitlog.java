package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QQuantumlimitlog is a Querydsl query type for QQuantumlimitlog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QQuantumlimitlog extends com.mysema.query.sql.RelationalPathBase<QQuantumlimitlog> {

    private static final long serialVersionUID = -1315665649;

    public static final QQuantumlimitlog quantumlimitlog = new QQuantumlimitlog("quantumlimitlog");

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

    public QQuantumlimitlog(String variable) {
        super(QQuantumlimitlog.class, forVariable(variable), "null", "quantumlimitlog");
    }

    @SuppressWarnings("all")
    public QQuantumlimitlog(Path<? extends QQuantumlimitlog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "quantumlimitlog");
    }

    public QQuantumlimitlog(PathMetadata<?> metadata) {
        super(QQuantumlimitlog.class, metadata, "null", "quantumlimitlog");
    }

}

