
    create table "SYSTEM"."INTERVIEWDETAILS"(
        "INTERVIEWID" NUMBER(10) not null,
       "APPLICANTID" NUMBER(10),
        constraint "IID2" primary key ("INTERVIEWID")
    );

    alter table "SYSTEM"."INTERVIEWDETAILS"
        add constraint "FAID1"
        foreign key ("APPLICANTID")
        references "SYSTEM"."APPLICANTSKILLS"("APPLICANTID");
    create unique index "SYSTEM"."IID2" on "SYSTEM"."INTERVIEWDETAILS"("INTERVIEWID");