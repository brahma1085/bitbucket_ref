package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCustomermaster is a Querydsl query type for QCustomermaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCustomermaster extends com.mysema.query.sql.RelationalPathBase<QCustomermaster> {

    private static final long serialVersionUID = -174805227;

    public static final QCustomermaster customermaster = new QCustomermaster("customermaster");

    public final StringPath addrproof = createString("addrproof");

    public final NumberPath<Integer> brCode = createNumber("br_code", Integer.class);

    public final StringPath caste = createString("caste");

    public final NumberPath<Integer> cid = createNumber("cid", Integer.class);

    public final StringPath courtdate = createString("courtdate");

    public final NumberPath<Integer> custtype = createNumber("custtype", Integer.class);

    public final StringPath dateofexpiry = createString("dateofexpiry");

    public final StringPath dateofissue = createString("dateofissue");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final StringPath dob = createString("dob");

    public final StringPath fathername = createString("fathername");

    public final StringPath fname = createString("fname");

    public final StringPath guardianaddress = createString("guardianaddress");

    public final StringPath guardianname = createString("guardianname");

    public final StringPath guardianrelation = createString("guardianrelation");

    public final NumberPath<Integer> guardiansalute = createNumber("guardiansalute", Integer.class);

    public final StringPath guardiansex = createString("guardiansex");

    public final StringPath guardiantype = createString("guardiantype");

    public final NumberPath<Integer> introid = createNumber("introid", Integer.class);

    public final StringPath lname = createString("lname");

    public final StringPath marital = createString("marital");

    public final StringPath mname = createString("mname");

    public final StringPath mothername = createString("mothername");

    public final StringPath nameproof = createString("nameproof");

    public final StringPath nationality = createString("nationality");

    public final StringPath occupation = createString("occupation");

    public final StringPath panno = createString("panno");

    public final StringPath passportno = createString("passportno");

    public final SimplePath<byte[]> photo = createSimple("photo", byte[].class);

    public final StringPath religion = createString("religion");

    public final StringPath salute = createString("salute");

    public final StringPath scst = createString("scst");

    public final StringPath sex = createString("sex");

    public final SimplePath<byte[]> sign = createSimple("sign", byte[].class);

    public final StringPath spousename = createString("spousename");

    public final NumberPath<Integer> subCategory = createNumber("sub_category", Integer.class);

    public final StringPath suboccupation = createString("suboccupation");

    public final StringPath veDate = createString("ve_date");

    public final StringPath veTml = createString("ve_tml");

    public final StringPath veUser = createString("ve_user");

    public final com.mysema.query.sql.PrimaryKey<QCustomermaster> primary = createPrimaryKey(cid);

    public QCustomermaster(String variable) {
        super(QCustomermaster.class, forVariable(variable), "null", "customermaster");
    }

    @SuppressWarnings("all")
    public QCustomermaster(Path<? extends QCustomermaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "customermaster");
    }

    public QCustomermaster(PathMetadata<?> metadata) {
        super(QCustomermaster.class, metadata, "null", "customermaster");
    }

}

