
    create table "SYSTEM"."APPLICANTSKILLS"(
        "APPLICANTID" NUMBER(20) not null,
       "SKILLS" VARCHAR2(25),
       "EXPERIENCE" NUMBER(20),
       "LASTUSED" VARCHAR2(25),
        constraint "AID1" primary key ("APPLICANTID")
    );

    create unique index "SYSTEM"."AID1" on "SYSTEM"."APPLICANTSKILLS"("APPLICANTID");