expdp PRINCEBANK/PRINCEBANK content=all parallel=1 directory=DATA_PUMP_DIR dumpfile=PRINCEBANK.dmp logfile=PRINCEBANK.log.txt

drop user PRINCEBANK cascade;
DROP TABLESPACE TBS_PRINCEBANK INCLUDING CONTENTS AND DATAFILES;

sqlplus sys/123456@local-lhidb as sysdba
alter session set "_oracle_script"=true;
Create user PRINCEBANK identified by PRINCEBANK account unlock;
grant EXP_FULL_DATABASE,IMP_FULL_DATABASE,connect to PRINCEBANK;
grant create session, create table, create sequence, create trigger to PRINCEBANK;
CREATE TABLESPACE "TBS_PRINCEBANK" DATAFILE 'D:\app\sitha.sopheap\oradata\LHI\MYDB\TBS_PRINCEBANK.dbf' SIZE 10M AUTOEXTEND ON NEXT 2M MAXSIZE UNLIMITED LOGGING EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO;
alter user PRINCEBANK quota unlimited on TBS_PRINCEBANK;
alter user PRINCEBANK DEFAULT TABLESPACE "TBS_PRINCEBANK" ACCOUNT UNLOCK;
commit;

CREATE DIRECTORY DIR_ORA AS '/media/data/oradata/dpdump';
GRANT READ, WRITE ON DIRECTORY DIR_ORA TO PRINCEBANK;

impdp PRINCEBANK/PRINCEBANK schemas=PRINCEBANK directory=DIR_ORA dumpfile=PRINCEBANK.dmp logfile=PRINCEBANK.log 
impdp PRINCEBANK/PRINCEBANK schemas=PRINCEBANK dumpfile=princedb.dmp logfile=PRINCEBANK.log 


============================================================================

Create user FADB identified by FADB account unlock;
grant EXP_FULL_DATABASE,IMP_FULL_DATABASE,connect to FADB;
grant create session, create table, create sequence, create trigger to FADB;
CREATE TABLESPACE "FADB" DATAFILE 'D:\app\sitha.sopheap\oradata\LHI\MYDB\FADB.dbf' SIZE 10M AUTOEXTEND ON NEXT 2M MAXSIZE UNLIMITED LOGGING EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO;
alter user FADB quota unlimited on FADB;
alter user FADB DEFAULT TABLESPACE "FADB" ACCOUNT UNLOCK;
commit;

impdp FADB/FADB schemas=FADB dumpfile=FADB.dmp logfile=fadb.log

============================================================================

Create user MBWEBSERVICES identified by MBWEBSERVICES account unlock;
grant EXP_FULL_DATABASE,IMP_FULL_DATABASE,connect to MBWEBSERVICES;
grant create session, create table, create sequence, create trigger to MBWEBSERVICES;
CREATE TABLESPACE "MBWEBSERVICES" DATAFILE 'D:\app\sitha.sopheap\oradata\LHI\MYDB\MBWEBSERVICES.dbf' SIZE 10M AUTOEXTEND ON NEXT 2M MAXSIZE UNLIMITED LOGGING EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO;
alter user MBWEBSERVICES quota unlimited on MBWEBSERVICES;
alter user MBWEBSERVICES DEFAULT TABLESPACE "MBWEBSERVICES" ACCOUNT UNLOCK;
commit;

impdp MBWEBSERVICES/MBWEBSERVICES schemas=MBWEBSERVICES dumpfile=MBWEBSERVICES.dmp logfile=MBWEBSERVICES.log


-------------------------------------------------------
expdp system/LHI@LHI schemas=(APIREPORT) directory=DATA_PUMP_DIR dumpfile=APIREPORT.dmp logfile=APIREPORT.log






