
    create table "SYSTEM"."INTERVIEWDETAILS1"(
        "INTERVIEWID" NUMBER(10) not null,
       "APPLICANTID" NUMBER(10),
        constraint "IID3" primary key ("INTERVIEWID")
    );

    alter table "SYSTEM"."INTERVIEWDETAILS1"
        add constraint "FAID2"
        foreign key ("APPLICANTID")
        references "SYSTEM"."APPLICANTDETAILS"("APPLICANTID");
    create unique index "SYSTEM"."IID3" on "SYSTEM"."INTERVIEWDETAILS1"("INTERVIEWID");