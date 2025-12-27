package com.sssoft.isatt.data.generated.pojo;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QReplacementStrings is a Querydsl query type for QReplacementStrings
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QReplacementStrings extends com.mysema.query.sql.RelationalPathBase<QReplacementStrings> {

    private static final long serialVersionUID = 1543156044;

    public static final QReplacementStrings replacementStrings = new QReplacementStrings("replacement_strings");

    public final NumberPath<Integer> appID = createNumber("AppID", Integer.class);

    public final NumberPath<Integer> encrypted = createNumber("ENCRYPTED", Integer.class);

    public final StringPath foreignID = createString("Foreign_ID");

    public final NumberPath<Integer> id = createNumber("ID", Integer.class);

    public final StringPath level = createString("Level");

    public final StringPath name = createString("Name");

    public final StringPath value = createString("Value");

    public final com.mysema.query.sql.PrimaryKey<QReplacementStrings> primary = createPrimaryKey(id);

    public final com.mysema.query.sql.ForeignKey<QApplication> replacementStringsApplicationFK = createForeignKey(appID, "AppID");

    public QReplacementStrings(String variable) {
        super(QReplacementStrings.class, forVariable(variable), "null", "replacement_strings");
    }

    @SuppressWarnings("all")
    public QReplacementStrings(Path<? extends QReplacementStrings> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "replacement_strings");
    }

    public QReplacementStrings(PathMetadata<?> metadata) {
        super(QReplacementStrings.class, metadata, "null", "replacement_strings");
    }

}

