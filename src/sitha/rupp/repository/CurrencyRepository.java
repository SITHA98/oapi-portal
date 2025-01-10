package sitha.rupp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import sitha.rupp.model.Currency;

@Repository
public interface CurrencyRepository {

	
	@Select("Select * from currency")
	@Results({
		@Result(column="CURR_ID",property="id")
		,@Result(column="CURR_CODE",property="code")
		,@Result(column="DESCRIPTION_KH",property="description")
	})
	List<Currency>getAllCcy();
	
}
