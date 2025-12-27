
  CREATE OR REPLACE PROCEDURE "SYSTEM"."STD" (sid in int,sname in varchar,ss1 in int,ss2 in int,ss3 in int)as stotal int;
begin
stotal:=ss1+ss2+ss3;
insert into student1 values(sid,sname,ss1,ss2,ss3,stotal);
end std;
