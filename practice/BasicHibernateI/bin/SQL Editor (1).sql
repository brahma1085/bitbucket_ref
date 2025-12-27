
    create table "SYSTEM"."STUDENT"(
        "SNO" NUMBER(19) not null,
       "SNAME" VARCHAR2(80),
        constraint "SYS_C005360" primary key ("SNO")
    );

    create unique index "SYSTEM"."SYS_C005360" on "SYSTEM"."STUDENT"("SNO");