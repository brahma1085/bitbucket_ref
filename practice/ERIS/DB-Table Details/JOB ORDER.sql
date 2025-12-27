
    create table "SYSTEM"."JOBORDER"(
        "JOBID" NUMBER(10) not null,
       "CLIENTID" NUMBER(10),
       "OPENDATE" DATE,
       "ENDDATE" DATE,
       "EDUCATION" VARCHAR2(25),
       "EXPERIENCE" NUMBER(10),
       "EXPERIENCEINFIELD" VARCHAR2(25),
       "STATUS" VARCHAR2(15),
       "JOBCATEGORY" VARCHAR2(20),
       "NUMBEROFVACANCIES" NUMBER(10),
       "RATE" NUMBER(10),
       "ASSIGNED" VARCHAR2(25),
       "JOBDESCRIPTION" VARCHAR2(40),
        constraint "JID1" primary key ("JOBID")
    );

    alter table "SYSTEM"."JOBORDER"
        add constraint "FCID1"
        foreign key ("CLIENTID")
        references "SYSTEM"."CLIENTDETAILS"("CLIENTID");
    create unique index "SYSTEM"."JID1" on "SYSTEM"."JOBORDER"("JOBID");