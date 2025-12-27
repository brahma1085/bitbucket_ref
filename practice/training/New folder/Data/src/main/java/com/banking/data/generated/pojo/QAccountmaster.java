package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAccountmaster is a Querydsl query type for QAccountmaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAccountmaster extends com.mysema.query.sql.RelationalPathBase<QAccountmaster> {

    private static final long serialVersionUID = 875265242;

    public static final QAccountmaster accountmaster = new QAccountmaster("accountmaster");

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

    public final com.mysema.query.sql.PrimaryKey<QAccountmaster> primary = createPrimaryKey(acNo, acType);

    public QAccountmaster(String variable) {
        super(QAccountmaster.class, forVariable(variable), "null", "accountmaster");
    }

    @SuppressWarnings("all")
    public QAccountmaster(Path<? extends QAccountmaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "accountmaster");
    }

    public QAccountmaster(PathMetadata<?> metadata) {
        super(QAccountmaster.class, metadata, "null", "accountmaster");
    }

}

