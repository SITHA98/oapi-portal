expdp APIREPORT/APIREPORT content=all parallel=1 directory=DATA_PUMP_DIR dumpfile=APIREPORT.dmp logfile=APIREPORT1.log version=11
expdp system/LHI@LHI schemas=(APIREPORT) directory=DATA_PUMP_DIR dumpfile=APIREPORT.dmp logfile=APIREPORT.log

drop user APIREPORT cascade;
DROP TABLESPACE MYCORE INCLUDING CONTENTS AND DATAFILES;

alter session set "_ORACLE_SCRIPT"=true;  
Create user APIREPORT identified by APIREPORT account unlock;
grant EXP_FULL_DATABASE,IMP_FULL_DATABASE,connect to APIREPORT;
grant create session, create table, create sequence, create trigger to APIREPORT;
GRANT ALL PRIVILEGES TO APIREPORT;
#CREATE TABLESPACE "MYCORE" DATAFILE 'D:\APP\SITHA.SOPHEAP\ORADATA\LHI\MYCORE.dbf' SIZE 10M AUTOEXTEND ON NEXT 2M MAXSIZE UNLIMITED LOGGING EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO;
CREATE TABLESPACE "MYCORE" DATAFILE 'D:\app\Administrator\oradata\LHIDB3\MYCORE.dbf' SIZE 10M AUTOEXTEND ON NEXT 2M MAXSIZE UNLIMITED LOGGING EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO;
CREATE TABLESPACE "MYCORE" DATAFILE 'C:\app\Administrator\oradata\LHI\MYCORE.dbf' SIZE 10M AUTOEXTEND ON NEXT 2M MAXSIZE UNLIMITED LOGGING EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO;
alter user APIREPORT quota unlimited on MYCORE;
alter user APIREPORT DEFAULT TABLESPACE "MYCORE" ACCOUNT UNLOCK;
commit;

CREATE DIRECTORY DIR_ORA AS '/u01/app/oracle/admin/dpdump';
GRANT READ, WRITE ON DIRECTORY DIR_ORA TO APIREPORT;

impdp APIREPORT/APIREPORT schemas=APIREPORT dumpfile=APIREPORT.DMP logfile=APIREPORT.DMP222.log
#impdp PRINCEBANK/PRINCEBANK schemas=PRINCEBANK directory=DIR_ORA dumpfile=PRINCEBANK.dmp logfile=APIREPORT.log remap_tablespace=TBS_PRINCEBANK:APIREPORT REMAP_SCHEMA=PRINCEBANK:APIREPORT

============================================================================
https://www.thegeekstuff.com/2016/04/oracle-undo-tablespace/
show parameter undo_tablespace
select tablespace_name, contents from dba_tablespaces where contents = 'UNDO';
CREATE UNDO TABLESPACE UNDOTBS2 DATAFILE '/media/data/oradata/mydb/UNDOTBS2.dbf' SIZE 1024M AUTOEXTEND ON NEXT 100M MAXSIZE 2048M;
ALTER SYSTEM SET UNDO_TABLESPACE = UNDOTBS2;
DROP TABLESPACE UNDOTBS1;
rm /u01/oradata/devdb/undotbs_01.dbf
exec dbms_space_admin.drop_empty_segments(schema_name=>'PRINCEBANK');
exec dbms_space_admin.drop_empty_segments(schema_name=>'SYSTEM');

===============================================================

Git global setup

git config --global user.name "Sitha Sopheap"
git config --global user.email "sopheap.sitha@gmail.com"

Create a new repository

git clone https://gitlab.com/sopheap.sitha/oapi-portal.git
cd oapi-portal
touch README.md
git add README.md
git commit -m "add README"
git push -u origin master

Push an existing folder

cd existing_folder
git init
git remote add origin https://gitlab.com/sopheap.sitha/oapi-portal.git
git add .
git commit -m "Initial commit"
git push -u origin master

Push an existing Git repository

cd existing_repo
git remote rename origin old-origin
git remote add origin https://gitlab.com/sopheap.sitha/oapi-portal.git
git push -u origin --all
git push -u origin --tags



S.SITHA1
123456Aa!

DEMO
user:demo
password:123456Zz@