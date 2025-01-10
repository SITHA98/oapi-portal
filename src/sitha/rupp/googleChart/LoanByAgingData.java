package sitha.rupp.googleChart;
//package com.princebank.googleChart;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.princebank.model.LoanByAging_TENURE;
//import com.princebank.service.BizLoanByAgingService;
//
//public class LoanByAgingData {
//
//    private static final List<KeyValue> pieDataList;
//
//    static {
//        pieDataList = new ArrayList<LoanByAgingData.KeyValue>();
//        BizLoanByAgingService loan = new BizLoanByAgingService();
//        List<LoanByAging_TENURE> alist = new ArrayList<LoanByAging_TENURE>();
//        alist = loan.getLoanByAging();
//        for(int i=0; i<alist.size();i++) {
//        	pieDataList.add(new KeyValue(alist.get(i).getTENURE_M(), alist.get(i).getNO_LOAN()));
//        }
//        /*
//        pieDataList.add(new KeyValue("Russia", "17098242"));
//        pieDataList.add(new KeyValue("Canada", "9984670"));
//        pieDataList.add(new KeyValue("USA", "9826675"));
//        pieDataList.add(new KeyValue("China", "9596961"));
//        pieDataList.add(new KeyValue("Brazil", "8514877"));
//        pieDataList.add(new KeyValue("Australia", "7741220"));
//        pieDataList.add(new KeyValue("India", "3287263"));
//        */
//    }
//
//    public static List<KeyValue> getPieDataList() {
//        return pieDataList;
//    }
//
//    public static class KeyValue {
//        String key;
//        String value;
//
//        public KeyValue(String key, String value) {
//            super();
//            this.key = key;
//            this.value = value;
//        }
//
//        public String getKey() {
//            return key;
//        }
//
//        public void setKey(String key) {
//            this.key = key;
//        }
//
//        public String getValue() {
//            return value;
//        }
//
//        public void setValue(String value) {
//            this.value = value;
//        }
//
//    }
//
//}