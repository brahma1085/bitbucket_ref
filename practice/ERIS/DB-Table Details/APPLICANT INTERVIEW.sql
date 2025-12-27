
    create table "SYSTEM"."APPLICANTINTERVIEW"(
        "INTERVIEWID" NUMBER(10) not null,
       "INTERVIEWTYPE" VARCHAR2(25),
       "INTERVIEWBY" VARCHAR2(25),
       "INTERVIEWDATE" DATE,
       "SKILLRATE" NUMBER(20),
       "COMMRATE" NUMBER(20),
       "COMMENTS" VARCHAR2(60),
        constraint "IID1" primary key ("INTERVIEWID")
    );

    create unique index "SYSTEM"."IID1" on "SYSTEM"."APPLICANTINTERVIEW"("INTERVIEWID");