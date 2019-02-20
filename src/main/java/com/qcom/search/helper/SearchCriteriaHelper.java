package com.qcom.search.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.qcom.search.document.TagsFavorites;

@Component
public class SearchCriteriaHelper {

	public List<SearchCriteria> parseQueryParams(String search) {
		List<SearchCriteria> params = new ArrayList<SearchCriteria>();
		if (search != null) {
			Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
			Matcher matcher = pattern.matcher(search + ",");
			while (matcher.find()) {
				params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
			}
		}
		return params;
	}

	public List<Criteria> prepareCriteriaList(Map<String, Object> searchCriteria) {
		List<Criteria> criteriasList = new ArrayList<Criteria>();
		Set<Entry<String, Object>> criteriaParamMap = searchCriteria.entrySet();
		for (Entry<String, Object> entry : criteriaParamMap) {
				criteriasList.add(Criteria.where(entry.getKey()).is(entry.getValue()));
		}
		return criteriasList;
	}
	
	
	
	public Query createQuery(List<Criteria> criteriasList,String operator) {
		Query query = new Query();
		if(operator.equalsIgnoreCase("AND")) {
			query.addCriteria(new Criteria().andOperator(criteriasList.toArray(new Criteria[criteriasList.size()])));
		}else if(operator.equalsIgnoreCase("OR")) {
			query.addCriteria(new Criteria().orOperator(criteriasList.toArray(new Criteria[criteriasList.size()])));
		}
		return query;
	}
	
	public Query createRecentSearchQuery(TagsFavorites rsquery) {
		//Query query = new Query();
		//query.addCriteria(Criteria.where("name").is("Eric"));
		//List<User> users = mongoTemplate.find(query, User.class);
		Query query = new Query();
		
		query.addCriteria(Criteria.where("action").
				is("query").and("appName").is(rsquery.getAppName()).and("user").is(rsquery.getUser()).and("limit").is(rsquery.getAdditionalData().get("limit")));
		/*if(operator.equalsIgnoreCase("AND")) {
			query.addCriteria(new Criteria().andOperator(criteriasList.toArray(new Criteria[criteriasList.size()])));
		}else if(operator.equalsIgnoreCase("OR")) {
			query.addCriteria(new Criteria().orOperator(criteriasList.toArray(new Criteria[criteriasList.size()])));
		}*/
		
		return query;
	}
	
	public List<Criteria> createCriteriaList(Map<String, Object> rsquery) {
		List<Criteria> criteriasList = new ArrayList<Criteria>();
		Set<Entry<String, Object>> criteriaParamMap = rsquery.entrySet();
		for (Entry<String, Object> entry : criteriaParamMap) {
			if(!rsquery.containsKey("limit"))
				criteriasList.add(Criteria.where(entry.getKey()).is(entry.getValue()));
		}
		return criteriasList;
		//query.addCriteria(Criteria.where("action").
			//	is("query").and("appName").is(rsquery.getAppName()).and("user").is(rsquery.getUser()).and("limit").is(rsquery.getAdditionalData().get("limit")));
		/*if(operator.equalsIgnoreCase("AND")) {
			query.addCriteria(new Criteria().andOperator(criteriasList.toArray(new Criteria[criteriasList.size()])));
		}else if(operator.equalsIgnoreCase("OR")) {
			query.addCriteria(new Criteria().orOperator(criteriasList.toArray(new Criteria[criteriasList.size()])));
		}*/
		
	}
	
	

}
