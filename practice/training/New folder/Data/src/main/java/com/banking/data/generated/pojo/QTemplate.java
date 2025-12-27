package com.banking.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTemplate is a Querydsl query type for QTemplate
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTemplate extends com.mysema.query.sql.RelationalPathBase<QTemplate> {

    private static final long serialVersionUID = 763564943;

    public static final QTemplate template = new QTemplate("template");

    public final StringPath acType = createString("ac_type");

    public final StringPath deDate = createString("de_date");

    public final StringPath deTml = createString("de_tml");

    public final StringPath deUser = createString("de_user");

    public final NumberPath<Integer> stageNo = createNumber("stage_no", Integer.class);

    public final NumberPath<Integer> tempNo = createNumber("temp_no", Integer.class);

    public final SimplePath<byte[]> text = createSimple("text", byte[].class);

    public QTemplate(String variable) {
        super(QTemplate.class, forVariable(variable), "null", "template");
    }

    @SuppressWarnings("all")
    public QTemplate(Path<? extends QTemplate> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "template");
    }

    public QTemplate(PathMetadata<?> metadata) {
        super(QTemplate.class, metadata, "null", "template");
    }

}

