/* Formatted on 3/10/2019 2:45:41 PM (QP5 v5.215.12089.38647) */
SELECT BRANCH_NAME,
       BRANCH_ADDR3,
       BRANCH_ADDR2,
       BRANCH_ADDR1,
       BR_TEL,
       AC_NO,
       FULL_NAME AC_DESC,
       AC_BRANCH,
       TRN_DT,
       VALUE_DT,
       TRN_CODE,
       DEBIT,
       CREDIT,
       ACY_BAL,
       (SUM (acy_bal) OVER (PARTITION BY AC_NO ORDER BY ROWNUM)) BALANCE,
       AC_CCY,
       TRN_DESC,
       USER_ID,
       AUTH_ID,
       ADDRESS_LINE4,
       ADDRESS_LINE3,
       ADDRESS_LINE2,
       ADDRESS_LINE1,
       MOBILE_NUMBER,(
        SELECT AC_DESC FROM STTM_CUST_ACCOUNT A WHERE A.CUST_AC_NO=$P{parAccountNo} ) AC_NAME
       ,(SELECT MOBILE_NUMBER FROM STTM_CUST_PERSONAL WHERE CUSTOMER_NO=(SELECT CUST_NO FROM STTM_CUST_ACCOUNT A WHERE A.CUST_AC_NO=$P{parAccountNo} ))CUST_PHONE
  FROM (  SELECT BRANCH_NAME,
                 BRANCH_ADDR3,
                 BRANCH_ADDR2,
                 BRANCH_ADDR1,
                 B.BR_TEL,
                 ADDRESS_LINE4,
                 ADDRESS_LINE3,
                 ADDRESS_LINE2,
                 ADDRESS_LINE1,
                 AC_NO,
                 C.FULL_NAME,
                 MOBILE_NUMBER,
                 AC_BRANCH,
                 TRN_DT,
                 VALUE_DT,
                 TRN_CODE,
                   DECODE (DRCR_IND, 'D', 1, 0)
                 * DECODE (AC_CCY, 'USD', LCY_AMOUNT, NVL (FCY_AMOUNT, 0))
                    DEBIT,
                   DECODE (DRCR_IND, 'C', 1, 0)
                 * DECODE (AC_CCY, 'USD', LCY_AMOUNT, NVL (FCY_AMOUNT, 0))
                    CREDIT,
                   DECODE (DRCR_IND, 'D', -1, 1)
                 * DECODE (AC_CCY, 'USD', LCY_AMOUNT, NVL (FCY_AMOUNT, 0))
                    ACY_BAL,
                 AC_CCY,
                 (SELECT TRN_DESC
                    FROM STTM_TRN_CODE
                   WHERE A.TRN_CODE = TRN_CODE)
                    TRN_DESC,
                 USER_ID,
                 AUTH_ID
            FROM ACVW_ALL_AC_ENTRIES A
                 INNER JOIN (SELECT BRANCH_CODE,
                                    BRANCH_NAME,
                                    BRANCH_ADDR3,
                                    BRANCH_ADDR2,
                                    BRANCH_ADDR1,
                                    '023 991 168' BR_TEL
                               FROM STTM_BRANCH) B
                    ON A.AC_BRANCH = B.BRANCH_CODE
                 LEFT JOIN (SELECT CUSTOMER_NO,
                                   FULL_NAME,
                                   MOBILE_NUMBER,
                                   ADDRESS_LINE4,
                                   ADDRESS_LINE3,
                                   ADDRESS_LINE2,
                                   ADDRESS_LINE1
                              FROM    (SELECT A.CUSTOMER_NO,
                                              A.FULL_NAME,
                                              B.MOBILE_NUMBER,
                                              B.P_PINCODE
                                         FROM    STTM_CUSTOMER A
                                              INNER JOIN
                                                 (SELECT CUSTOMER_NO,
                                                         MOBILE_NUMBER,
                                                         P_PINCODE
                                                    FROM STTM_CUST_PERSONAL) B
                                              ON A.CUSTOMER_NO = B.CUSTOMER_NO) C
                                   INNER JOIN
                                      STTM_ADDRESS D
                                   ON C.P_PINCODE = D.ADDRESS_CODE) C
                    ON A.RELATED_CUSTOMER = C.CUSTOMER_NO
           WHERE AC_NO = $P{parAccountNo}
                 AND TRN_DT BETWEEN $P{parDateFrom} AND $P{parDateTo}
                 AND A.RELATED_CUSTOMER IN
                        (SELECT CUSTOMER_NO
                           FROM STTM_CUSTOMER
                          --WHERE CUSTOMER_CATEGORY <> 'STAFF'
                         )
        ORDER BY VALUE_DT);
        
        
        
        
/* Formatted on 3/10/2019 2:45:41 PM (QP5 v5.215.12089.38647) */
SELECT BRANCH_NAME,
       BRANCH_ADDR3,
       BRANCH_ADDR2,
       BRANCH_ADDR1,
       BR_TEL,
       AC_NO,
       FULL_NAME AC_DESC,
       AC_BRANCH,
       TRN_DT,
       VALUE_DT,
       TRN_CODE,
       DEBIT,
       CREDIT,
       0 ACY_BAL,
       BALANCE,
       AC_CCY,
       TRN_DESC,
       USER_ID,
       AUTH_ID,
       ADDRESS_LINE4,
       ADDRESS_LINE3,
       ADDRESS_LINE2,
       ADDRESS_LINE1,
       MOBILE_NUMBER,(
        SELECT AC_DESC FROM STTM_CUST_ACCOUNT A WHERE A.CUST_AC_NO='000029796') AC_NAME
       ,(SELECT MOBILE_NUMBER FROM STTM_CUST_PERSONAL WHERE CUSTOMER_NO=(SELECT CUST_NO FROM STTM_CUST_ACCOUNT A WHERE A.CUST_AC_NO='000029796'))CUST_PHONE
FROM (select a.*,(SUM (a.acy_bal1) OVER (ORDER BY a.AC_ENTRY_SR_NO)) BALANCE from (
        SELECT 0 rec,
        min(BRANCH_NAME)BRANCH_NAME,
        min(BRANCH_ADDR3)BRANCH_ADDR3,
        min(BRANCH_ADDR2)BRANCH_ADDR2,
        min(BRANCH_ADDR1)BRANCH_ADDR1,
        ''         BR_TEL,
        min(ADDRESS_LINE4)ADDRESS_LINE4,
        min(ADDRESS_LINE3)ADDRESS_LINE3,
        min(ADDRESS_LINE2)ADDRESS_LINE2,
        min(ADDRESS_LINE1)ADDRESS_LINE1,
        min(AC_NO)AC_NO,
        min(FULL_NAME)FULL_NAME,
        min(MOBILE_NUMBER)MOBILE_NUMBER,
        min(AC_BRANCH)AC_BRANCH,
        TO_DATE('21-FEB-2019')    TRN_DT,
        TO_DATE('21-FEB-2019')    VALUE_DT,
        ''       TRN_CODE,
                 nvl(SUM(  DECODE (DRCR_IND, 'D', 1, 0)
                 * DECODE (AC_CCY, 'USD', LCY_AMOUNT, NVL (FCY_AMOUNT, 0))),0)
                    DEBIT,
                 nvl(SUM(  DECODE (DRCR_IND, 'C', 1, 0)
                 * DECODE (AC_CCY, 'USD', LCY_AMOUNT, NVL (FCY_AMOUNT, 0))),0)
                    CREDIT,
                 nvl( SUM(  DECODE (DRCR_IND, 'C', 1, 0)
                 * DECODE (AC_CCY, 'USD', LCY_AMOUNT, NVL (FCY_AMOUNT, 0)))-
                 SUM(  DECODE (DRCR_IND, 'D', 1, 0)
                 * DECODE (AC_CCY, 'USD', LCY_AMOUNT, NVL (FCY_AMOUNT, 0))),0) ACY_BAL1
                 
                 ,'D' DORC,min(AC_CCY)AC_CCY,
                 'Opening Balance 'TRN_DESC,
                 ''USER_ID,
                 ''AUTH_ID,0 AC_ENTRY_SR_NO
       FROM ACVW_ALL_AC_ENTRIES A 
       INNER JOIN (SELECT BRANCH_CODE,
                                    BRANCH_NAME,
                                    BRANCH_ADDR3,
                                    BRANCH_ADDR2,
                                    BRANCH_ADDR1,
                                    '023 991 168' BR_TEL
                               FROM STTM_BRANCH) B
                    ON A.AC_BRANCH = B.BRANCH_CODE
                 LEFT JOIN (SELECT CUSTOMER_NO,
                                   FULL_NAME,
                                   MOBILE_NUMBER,
                                   ADDRESS_LINE4,
                                   ADDRESS_LINE3,
                                   ADDRESS_LINE2,
                                   ADDRESS_LINE1
                              FROM    (SELECT A.CUSTOMER_NO,
                                              A.FULL_NAME,
                                              B.MOBILE_NUMBER,
                                              B.P_PINCODE
                                         FROM    STTM_CUSTOMER A
                                              INNER JOIN
                                                 (SELECT CUSTOMER_NO,
                                                         MOBILE_NUMBER,
                                                         P_PINCODE
                                                    FROM STTM_CUST_PERSONAL) B
                                              ON A.CUSTOMER_NO = B.CUSTOMER_NO) C
                                   INNER JOIN
                                      STTM_ADDRESS D
                                   ON C.P_PINCODE = D.ADDRESS_CODE) C
                    ON A.RELATED_CUSTOMER = C.CUSTOMER_NO
       WHERE A.AC_NO='000029796' AND A.TRN_DT<='21-FEB-2019'
       
       union all
       
       SELECT    rownum rec,
                 BRANCH_NAME,
                 BRANCH_ADDR3,
                 BRANCH_ADDR2,
                 BRANCH_ADDR1,
                 B.BR_TEL,
                 ADDRESS_LINE4,
                 ADDRESS_LINE3,
                 ADDRESS_LINE2,
                 ADDRESS_LINE1,
                 AC_NO,
                 C.FULL_NAME,
                 MOBILE_NUMBER,
                 AC_BRANCH,
                 TRN_DT,
                 VALUE_DT,
                 TRN_CODE,
                   DECODE (DRCR_IND, 'D', 1, 0)
                 * DECODE (AC_CCY, 'USD', LCY_AMOUNT, NVL (FCY_AMOUNT, 0))
                    DEBIT,
                   DECODE (DRCR_IND, 'C', 1, 0)
                 * DECODE (AC_CCY, 'USD', LCY_AMOUNT, NVL (FCY_AMOUNT, 0))
                    CREDIT,
                   DECODE (DRCR_IND, 'D', -1, 1)
                 * DECODE (AC_CCY, 'USD', LCY_AMOUNT, NVL (FCY_AMOUNT, 0))
                 ACY_BAL1 ,A.DRCR_IND,
                 AC_CCY,
                 (SELECT TRN_DESC
                    FROM STTM_TRN_CODE
                   WHERE A.TRN_CODE = TRN_CODE)
                 TRN_DESC,
                 USER_ID,
                 AUTH_ID,a.AC_ENTRY_SR_NO
            FROM ACVW_ALL_AC_ENTRIES A
                 INNER JOIN (SELECT BRANCH_CODE,
                                    BRANCH_NAME,
                                    BRANCH_ADDR3,
                                    BRANCH_ADDR2,
                                    BRANCH_ADDR1,
                                    '023 991 168' BR_TEL
                               FROM STTM_BRANCH) B
                    ON A.AC_BRANCH = B.BRANCH_CODE
                 LEFT JOIN (SELECT CUSTOMER_NO,
                                   FULL_NAME,
                                   MOBILE_NUMBER,
                                   ADDRESS_LINE4,
                                   ADDRESS_LINE3,
                                   ADDRESS_LINE2,
                                   ADDRESS_LINE1
                              FROM    (SELECT A.CUSTOMER_NO,
                                              A.FULL_NAME,
                                              B.MOBILE_NUMBER,
                                              B.P_PINCODE
                                         FROM    STTM_CUSTOMER A
                                              INNER JOIN
                                                 (SELECT CUSTOMER_NO,
                                                         MOBILE_NUMBER,
                                                         P_PINCODE
                                                    FROM STTM_CUST_PERSONAL) B
                                              ON A.CUSTOMER_NO = B.CUSTOMER_NO) C
                                   INNER JOIN
                                      STTM_ADDRESS D
                                   ON C.P_PINCODE = D.ADDRESS_CODE) C
                    ON A.RELATED_CUSTOMER = C.CUSTOMER_NO
           WHERE AC_NO = '000029796'
                 AND TRN_DT > '21-FEB-2019' AND TRN_DT<= TO_DATE(sysdate)
                 AND A.RELATED_CUSTOMER IN
                        (SELECT CUSTOMER_NO
                           FROM STTM_CUSTOMER
                          --WHERE CUSTOMER_CATEGORY <> 'STAFF'
                         )
        ORDER BY AC_ENTRY_SR_NO ) a order by A.VALUE_DT)