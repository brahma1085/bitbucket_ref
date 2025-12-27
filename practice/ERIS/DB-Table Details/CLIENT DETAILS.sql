
    create table "SYSTEM"."CLIENTDETAILS"(
        "CLIENTID" NUMBER(10) not null,
       "COMPANYNAME" VARCHAR2(25),
       "CONTACTNAME" VARCHAR2(25),
       "ADDRESS" VARCHAR2(40),
       "CITY" VARCHAR2(25),
       "STATE" VARCHAR2(25),
       "ZIP" VARCHAR2(10),
       "TITLE" VARCHAR2(25),
       "EXTENSION" VARCHAR2(25),
       "TELEPHONE" VARCHAR2(25),
       "FAX" VARCHAR2(25),
       "COUNTRY" VARCHAR2(25),
       "EMAIL" VARCHAR2(40),
       "DEPARTMENT" VARCHAR2(25),
        constraint "CID1" primary key ("CLIENTID")
    );

    create unique index "SYSTEM"."CID1" on "SYSTEM"."CLIENTDETAILS"("CLIENTID");