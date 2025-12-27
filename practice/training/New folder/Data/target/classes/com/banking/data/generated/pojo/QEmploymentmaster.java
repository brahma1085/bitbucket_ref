package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QEmploymentmaster is a Querydsl query type for QEmploymentmaster
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QEmploymentmaster extends com.mysema.query.sql.RelationalPathBase<QEmploymentmaster> {

    private static final long serialVersionUID = 486248291;

    public static final QEmploymentmaster employmentmaster = new QEmploymentmaster("employmentmaster");

    public final NumberPath<Integer> acNo = createNumber("ac_no", Integer.class);

    public final StringPath acType = createString("ac_type");

    public final StringPath addr = createString("addr");

    public final NumberPath<Double> amtMnth = createNumber("amt_mnth", Double.class);

    public final NumberPath<Double> avgTurnoverMnth = createNumber("avg_turnover_mnth", Double.class);

    public final NumberPath<Integer> bankAcNo = createNumber("bank_ac_no", Integer.class);

    public final StringPath bankAcType = createString("bank_ac_type");

    public final StringPath bankName = createString("bank_name");

    public final StringPath concernName = createString("concern_name");

    public final StringPath department = createString("department");

    public final StringPath designation = createString("designation");

    public final StringPath empConfirmed = createString("emp_confirmed");

    public final StringPath empNo = createString("emp_no");

    public final StringPath empType = createString("emp_type");

    public final StringPath employerName = createString("employer_name");

    public final StringPath goodsCond = createString("goods_cond");

    public final StringPath goodsType = createString("goods_type");

    public final StringPath natureOfEmp = createString("nature_of_emp");

    public final NumberPath<Double> netMonthIncome = createNumber("net_month_income", Double.class);

    public final StringPath phNo = createString("ph_no");

    public final StringPath salCertEnclosed = createString("sal_cert_enclosed");

    public final NumberPath<Integer> servLength = createNumber("serv_length", Integer.class);

    public final StringPath servTrans = createString("serv_trans");

    public final NumberPath<Double> stockVal = createNumber("stock_val", Double.class);

    public final NumberPath<Double> taxExpMnth = createNumber("tax_exp_mnth", Double.class);

    public QEmploymentmaster(String variable) {
        super(QEmploymentmaster.class, forVariable(variable), "null", "employmentmaster");
    }

    @SuppressWarnings("all")
    public QEmploymentmaster(Path<? extends QEmploymentmaster> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "employmentmaster");
    }

    public QEmploymentmaster(PathMetadata<?> metadata) {
        super(QEmploymentmaster.class, metadata, "null", "employmentmaster");
    }

}

