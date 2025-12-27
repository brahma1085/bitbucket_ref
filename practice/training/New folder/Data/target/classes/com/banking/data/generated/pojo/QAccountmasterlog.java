package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAccountmasterlog is a Querydsl query type for QAccountmasterlog
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAccountmasterlog extends com.mysema.query.sql.RelationalPathBase<QAccountmasterlog> {

    private static final long serialVersionUID = 280477738;

    public static final QAccountmasterlog accountmasterlog = new QAccountmasterlog("accountmasterlog");

    public final StringPath acClosedate = createString("ac_closedate");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acOpendate = createString("ac_opendate");

    public final StringPath acStatus = createString("ac_status");

    public final StringPath acType = createString("ac_type");

    public final NumberPath<Integer> addrType = createNumber("addr_type", Integer.class);

    public final StringPath chBkIssue = createString("ch_bk_issue");

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath freezeInd = createString("freeze_ind");

    public final StringPath intPblDate = createString("int_pbl_date");

    public final NumberPath<Integer> intrAcNo = createNumber("intr_ac_no", Integer.class);

    public final StringPath intrAcType = createString("intr_ac_type");

    public final NumberPath<Integer> lastLine = createNumber("last_line", Integer.class);

    public final StringPath lastTrDate = createString("last_tr_date");

    public final NumberPath<Integer> lastTrSeq = createNumber("last_tr_seq", Integer.class);

    public final NumberPath<Integer> ledgerSeq = createNumber("ledger_seq", Integer.class);

    public final NumberPath<Integer> noJtHldr = createNumber("no_jt_hldr", Integer.class);

    public final NumberPath<Integer> nomNo = createNumber("nom_no", Integer.class);

    public final NumberPath<Integer> psBkSeq = createNumber("ps_bk_seq", Integer.class);

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public QAccountmasterlog(String variable) {
        super(QAccountmasterlog.class, forVariable(variable), "null", "accountmasterlog");
    }

    @SuppressWarnings("all")
    public QAccountmasterlog(Path<? extends QAccountmasterlog> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "accountmasterlog");
    }

    public QAccountmasterlog(PathMetadata<?> metadata) {
        super(QAccountmasterlog.class, metadata, "null", "accountmasterlog");
    }

}

