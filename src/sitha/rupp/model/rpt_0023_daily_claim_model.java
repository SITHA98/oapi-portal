package sitha.rupp.model;

public class rpt_0023_daily_claim_model {
	String no="";
	String clmh_sys_id="";
	String pint_sys_id="";
	String papprh_pay_to="";
	String papprh_number="";
	String papprh_intmtnh_clmnum="";
	String papprh_intmtnh_polnum="";
	String papprh_srno="";
	String papprh_settle_yn="";
	String settlement_type="";
	String clmh_cl_status="";
	String claim_status_desc="";
	String reason_of_claim_status="";
	String settlement_date="";
	String papprh_approval_dt="";
	String claim_number="";
	String clmh_cob="";
	String clmh_extnum="";
	String ori_policy="";
	String policy_number="";
	String clmh_uwyr="";
	String ac_month="";
	String insured_name="";
	String inception_date="";
	String expire_date="";
	String product="";
	String risk_detail="";
	String lost_date="";
	String reported_date="";
	String transaction_date="";
	String os_days="";
	String event="";
	String clmh_place_accident="";
	String cause_of_loss="";
	String category_of_loss="";
	String narration_remark="";
	String claimant="";
	String hospital_name="";	
	double initimated_amount;
	double payment_amount;
	double loss_100;
	double claim_expense;
	double obligatory;
	double cedant_paid;
	double amount;
	double own_retension;
	double quota_share;
	double surplus;
	String created_date="";
	String approved_date="";
	String created_by="";
	String approved_by="";
	String emp_seqnum="";
	String emp_name_of_insured="";
	String clmd_cov_sys_id="";
	String clmd_cover="";
	double pcov_si_bc;
	String to_date="";
	
	
	
	public String getTo_date() {
		return to_date;
	}
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getClmh_sys_id() {
		return clmh_sys_id;
	}
	public void setClmh_sys_id(String clmh_sys_id) {
		this.clmh_sys_id = clmh_sys_id;
	}
	public String getPint_sys_id() {
		return pint_sys_id;
	}
	public void setPint_sys_id(String pint_sys_id) {
		this.pint_sys_id = pint_sys_id;
	}
	public String getPapprh_pay_to() {
		return papprh_pay_to;
	}
	public void setPapprh_pay_to(String papprh_pay_to) {
		this.papprh_pay_to = papprh_pay_to;
	}
	public String getPapprh_number() {
		return papprh_number;
	}
	public void setPapprh_number(String papprh_number) {
		this.papprh_number = papprh_number;
	}
	public String getPapprh_intmtnh_clmnum() {
		return papprh_intmtnh_clmnum;
	}
	public void setPapprh_intmtnh_clmnum(String papprh_intmtnh_clmnum) {
		this.papprh_intmtnh_clmnum = papprh_intmtnh_clmnum;
	}
	public String getPapprh_intmtnh_polnum() {
		return papprh_intmtnh_polnum;
	}
	public void setPapprh_intmtnh_polnum(String papprh_intmtnh_polnum) {
		this.papprh_intmtnh_polnum = papprh_intmtnh_polnum;
	}
	public String getPapprh_srno() {
		return papprh_srno;
	}
	public void setPapprh_srno(String papprh_srno) {
		this.papprh_srno = papprh_srno;
	}
	public String getPapprh_settle_yn() {
		return papprh_settle_yn;
	}
	public void setPapprh_settle_yn(String papprh_settle_yn) {
		this.papprh_settle_yn = papprh_settle_yn;
	}
	public String getSettlement_type() {
		return settlement_type;
	}
	public void setSettlement_type(String settlement_type) {
		this.settlement_type = settlement_type;
	}
	public String getClmh_cl_status() {
		return clmh_cl_status;
	}
	public void setClmh_cl_status(String clmh_cl_status) {
		this.clmh_cl_status = clmh_cl_status;
	}
	public String getClaim_status_desc() {
		return claim_status_desc;
	}
	public void setClaim_status_desc(String claim_status_desc) {
		this.claim_status_desc = claim_status_desc;
	}
	public String getReason_of_claim_status() {
		return reason_of_claim_status;
	}
	public void setReason_of_claim_status(String reason_of_claim_status) {
		this.reason_of_claim_status = reason_of_claim_status;
	}
	public String getSettlement_date() {
		return settlement_date;
	}
	public void setSettlement_date(String settlement_date) {
		this.settlement_date = settlement_date;
	}
	public String getPapprh_approval_dt() {
		return papprh_approval_dt;
	}
	public void setPapprh_approval_dt(String papprh_approval_dt) {
		this.papprh_approval_dt = papprh_approval_dt;
	}
	public String getClaim_number() {
		return claim_number;
	}
	public void setClaim_number(String claim_number) {
		this.claim_number = claim_number;
	}
	public String getClmh_cob() {
		return clmh_cob;
	}
	public void setClmh_cob(String clmh_cob) {
		this.clmh_cob = clmh_cob;
	}
	public String getClmh_extnum() {
		return clmh_extnum;
	}
	public void setClmh_extnum(String clmh_extnum) {
		this.clmh_extnum = clmh_extnum;
	}
	public String getOri_policy() {
		return ori_policy;
	}
	public void setOri_policy(String ori_policy) {
		this.ori_policy = ori_policy;
	}
	public String getPolicy_number() {
		return policy_number;
	}
	public void setPolicy_number(String policy_number) {
		this.policy_number = policy_number;
	}
	public String getClmh_uwyr() {
		return clmh_uwyr;
	}
	public void setClmh_uwyr(String clmh_uwyr) {
		this.clmh_uwyr = clmh_uwyr;
	}
	public String getAc_month() {
		return ac_month;
	}
	public void setAc_month(String ac_month) {
		this.ac_month = ac_month;
	}
	public String getInsured_name() {
		return insured_name;
	}
	public void setInsured_name(String insured_name) {
		this.insured_name = insured_name;
	}
	public String getInception_date() {
		return inception_date;
	}
	public void setInception_date(String inception_date) {
		this.inception_date = inception_date;
	}
	public String getExpire_date() {
		return expire_date;
	}
	public void setExpire_date(String expire_date) {
		this.expire_date = expire_date;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getRisk_detail() {
		return risk_detail;
	}
	public void setRisk_detail(String risk_detail) {
		this.risk_detail = risk_detail;
	}
	public String getLost_date() {
		return lost_date;
	}
	public void setLost_date(String lost_date) {
		this.lost_date = lost_date;
	}
	public String getReported_date() {
		return reported_date;
	}
	public void setReported_date(String reported_date) {
		this.reported_date = reported_date;
	}
	public String getTransaction_date() {
		return transaction_date;
	}
	public void setTransaction_date(String transaction_date) {
		this.transaction_date = transaction_date;
	}
	public String getOs_days() {
		return os_days;
	}
	public void setOs_days(String os_days) {
		this.os_days = os_days;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getClmh_place_accident() {
		return clmh_place_accident;
	}
	public void setClmh_place_accident(String clmh_place_accident) {
		this.clmh_place_accident = clmh_place_accident;
	}
	public String getCause_of_loss() {
		return cause_of_loss;
	}
	public void setCause_of_loss(String cause_of_loss) {
		this.cause_of_loss = cause_of_loss;
	}
	public String getCategory_of_loss() {
		return category_of_loss;
	}
	public void setCategory_of_loss(String category_of_loss) {
		this.category_of_loss = category_of_loss;
	}
	public String getNarration_remark() {
		return narration_remark;
	}
	public void setNarration_remark(String narration_remark) {
		this.narration_remark = narration_remark;
	}
	public String getClaimant() {
		return claimant;
	}
	public void setClaimant(String claimant) {
		this.claimant = claimant;
	}
	public double getInitimated_amount() {
		return initimated_amount;
	}
	public void setInitimated_amount(double initimated_amount) {
		this.initimated_amount = initimated_amount;
	}
	public double getPayment_amount() {
		return payment_amount;
	}
	public void setPayment_amount(double payment_amount) {
		this.payment_amount = payment_amount;
	}
	public double getLoss_100() {
		return loss_100;
	}
	public void setLoss_100(double loss_100) {
		this.loss_100 = loss_100;
	}
	public double getClaim_expense() {
		return claim_expense;
	}
	public void setClaim_expense(double claim_expense) {
		this.claim_expense = claim_expense;
	}
	public double getObligatory() {
		return obligatory;
	}
	public void setObligatory(double obligatory) {
		this.obligatory = obligatory;
	}
	public double getCedant_paid() {
		return cedant_paid;
	}
	public void setCedant_paid(double cedant_paid) {
		this.cedant_paid = cedant_paid;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getOwn_retension() {
		return own_retension;
	}
	public void setOwn_retension(double own_retension) {
		this.own_retension = own_retension;
	}
	public double getQuota_share() {
		return quota_share;
	}
	public void setQuota_share(double quota_share) {
		this.quota_share = quota_share;
	}
	public double getSurplus() {
		return surplus;
	}
	public void setSurplus(double surplus) {
		this.surplus = surplus;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getApproved_date() {
		return approved_date;
	}
	public void setApproved_date(String approved_date) {
		this.approved_date = approved_date;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getApproved_by() {
		return approved_by;
	}
	public void setApproved_by(String approved_by) {
		this.approved_by = approved_by;
	}
	public String getEmp_seqnum() {
		return emp_seqnum;
	}
	public void setEmp_seqnum(String emp_seqnum) {
		this.emp_seqnum = emp_seqnum;
	}
	public String getEmp_name_of_insured() {
		return emp_name_of_insured;
	}
	public void setEmp_name_of_insured(String emp_name_of_insured) {
		this.emp_name_of_insured = emp_name_of_insured;
	}
	public String getClmd_cov_sys_id() {
		return clmd_cov_sys_id;
	}
	public void setClmd_cov_sys_id(String clmd_cov_sys_id) {
		this.clmd_cov_sys_id = clmd_cov_sys_id;
	}
	public String getClmd_cover() {
		return clmd_cover;
	}
	public void setClmd_cover(String clmd_cover) {
		this.clmd_cover = clmd_cover;
	}
	public double getPcov_si_bc() {
		return pcov_si_bc;
	}
	public void setPcov_si_bc(double pcov_si_bc) {
		this.pcov_si_bc = pcov_si_bc;
	}
	public String getHospital_name() {
		return hospital_name;
	}
	public void setHospital_name(String hospital_name) {
		this.hospital_name = hospital_name;
	}

	
}
