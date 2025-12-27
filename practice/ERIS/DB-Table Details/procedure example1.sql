
  CREATE OR REPLACE PROCEDURE "SYSTEM"."STD1" (sid in int,sname out varchar)as begin select name into sname from student1 where id=sid;
end std1;
