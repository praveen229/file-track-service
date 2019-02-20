package com.qcom.search.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.qcom.search.document.TagsFavorites;
import com.qcom.search.helper.SearchCriteriaHelper;
import com.qcom.search.repository.TagsFavsRepository;


@Service
public class TagsFavsServiceImpl implements TagsFavsService {
	
	@Autowired
	TagsFavsRepository tagsFavsRepository;
	
	@Autowired
	MongoOperations mongoOperations;
	
	@Autowired
	SearchCriteriaHelper criteriaHelper;
	
	public TagsFavsServiceImpl() {
	}
	
	public TagsFavsServiceImpl(TagsFavsRepository bookmarkRepository, MongoOperations mongoOperations) {
		super();
		this.tagsFavsRepository = bookmarkRepository;
		this.mongoOperations = mongoOperations;
	}

	@Override
	public TagsFavorites create(TagsFavorites tagsFavsInput) {
		TagsFavorites created = tagsFavsRepository.save(tagsFavsInput);
		return created;
	}
	
	@Override
	public void delete(Map<String, Object> searchCriteria) {
		Query query = new Query();
		List<Criteria> criteriaList = criteriaHelper.prepareCriteriaList(searchCriteria);
		query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
		mongoOperations.remove(query,TagsFavorites.class);
	}

	@Override
	public List<TagsFavorites> findAll() {
		List<TagsFavorites> list = tagsFavsRepository.findAll();
		return list;
	}
	
	@Override
	public List<TagsFavorites> find(Map<String, Object> searchCriteria) {
		List<Criteria> criteriaList = criteriaHelper.prepareCriteriaList(searchCriteria);
		Query query = criteriaHelper.createQuery(criteriaList, searchCriteria.get("operator").toString());
		List<TagsFavorites> tagsFavsList =  mongoOperations.find(query, TagsFavorites.class);
		return tagsFavsList;
	}
	
	@Override
	public TagsFavorites update(TagsFavorites tagsFavsInput) {
		TagsFavorites favsResults = tagsFavsRepository.findOne(tagsFavsInput.getId());
		if(favsResults != null)
			tagsFavsRepository.save(tagsFavsInput);
		return tagsFavsInput;
	}
	
	@Override
	public List<TagsFavorites> findRecentSearches(String userId,String appName,String action,int limit) {
		/*Query query = new Query();
		query.limit(limit);
		query.with(new Sort(Sort.Direction.DESC,"createdDate"));
		query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));*/
		//List<TagsFavsInput> tagsFavsList =  mongoOperations.find(query, TagsFavsInput.class);
	
		//PageRequest request = new PageRequest(0, 1, new Sort(Direction.DESC, "createdDate"));
	    Pageable pageable = new PageRequest(0, limit, new Sort(Direction.DESC, "createdDate"));
		return tagsFavsRepository.getRecentSearches(userId,appName,action,pageable);
	}
	
	@Override
	public List<TagsFavorites> getLikeOrDislikeUrls(/*Map<String, Object> searchCriteria*/ String userId,String appName,String action,Boolean likeOrdislike) {
		//Query query = new Query();
		//query.addCriteria(new Criteria(Criteria.where()));
		//List<Criteria> criteriaList = criteriaHelper.createCriteriaList(searchCriteria);
		//query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
		//List<TagsFavsInput> tagsFavsList =  mongoOperations.find(query, TagsFavsInput.class);
		return tagsFavsRepository.getUserLikesOrDisLikes(userId, appName, action, likeOrdislike);
	}
	
	/*@Override
	public void savLike(String userId,String appName,String action) {
		return bookmarkRepository.getUserBookmarks(userId,appName,action);
	}*/
	
	@Override
	public void saveBookmarkCollections(TagsFavorites tagsFavsInput) {
		List<String> collectionNameList = tagsFavsInput.getCollectionNames();
		for (String collectionName : collectionNameList) {
			tagsFavsInput.setSourceName(collectionName);
			tagsFavsRepository.save(tagsFavsInput);
		}
	}
	
	@Override
	public List<TagsFavorites> getUserBookmarks(String userId,String appName,String action) {
		return tagsFavsRepository.getUserBookmarks(userId,appName,action);
	}
	
	@Override
	public List<TagsFavorites> getUserBookmarksByCollectionName(String userId,String appName,String action,List<String> collectionName) {
		return tagsFavsRepository.getUserBookmarksByCollectionName(userId,appName,action,collectionName);
	}

	@Override
	public TagsFavorites clearRecentSearches(String userId, String appName, String action, String id) {
		TagsFavorites  result = tagsFavsRepository.findRecentSearchById(userId, appName, action, id);
		if(result != null) {
			result.setStatus(false);
			tagsFavsRepository.save(result);
		}
		return result;
	}
	
	@Override
	public void clearAllRecentSearches(String userId, String appName, String action) {
		List<TagsFavorites> favsList = tagsFavsRepository.findRecentSearches(userId, appName, action);
		for (TagsFavorites tagsFavs : favsList) {
			if (tagsFavs != null) {
				tagsFavs.setStatus(false);
				tagsFavsRepository.save(tagsFavs);
			}
		}
	}

	@Override
	public TagsFavorites findById(String id) {
		return tagsFavsRepository.findOne(id);
	}

	/*@Override
	public void clearRecentSearches(Map<String, Object> searchCriteria) {
		Query query = new Query();
		List<Criteria> criteriaList = criteriaHelper.createCriteriaList(searchCriteria);
		query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
		List<TagsFavsInput> tagsFavsList =  mongoOperations.find(query, TagsFavsInput.class);
		if(tagsFavsList.size() >0) {
			for (TagsFavsInput tagsFavsInput : tagsFavsList) {
				tagsFavsInput.setStatus(false);
				mongoOperations.save(tagsFavsInput);
			}
		}
	}*/

	/*@Override
	public List<TagsFavsInput> findAnd(List<SearchCriteria> searchCriteria) {
		Query query = new Query();
		List<Criteria> andCriteriaList = criteriaHelper.createQuery(searchCriteria);
		query.addCriteria(new Criteria().andOperator(andCriteriaList.toArray(new Criteria[andCriteriaList.size()])));
		List<TagsFavsInput> tagsFavsList =  mongoOperations.find(query, TagsFavsInput.class);
		return tagsFavsList;
	}
	
	@Override
	public List<TagsFavsInput> findOr(List<SearchCriteria> searchCriteria) {
		Query query = new Query();
		List<Criteria> orCriteriaList = criteriaHelper.createQuery(searchCriteria);
		query.addCriteria(new Criteria().orOperator(orCriteriaList.toArray(new Criteria[orCriteriaList.size()])));
		List<TagsFavsInput> tagsFavsList =  mongoOperations.find(query, TagsFavsInput.class);
		return tagsFavsList;
	}*/

	/*@Override
	public BookmarkInput update(BookmarkInput indexerMetrics) {
		BookmarkInput updated = bookmarkRepository.findOne(indexerMetrics.getId());
		updated.update(indexerMetrics.getApplicationName(), indexerMetrics.getIndexerName(), indexerMetrics.getStartTime(),indexerMetrics.getEndTime(), indexerMetrics.getTypeOfJob(), indexerMetrics.getEnvironment(), indexerMetrics.getStatus());
		updated = bookmarkRepository.save(updated);
		return updated;
	}*/
	
}
