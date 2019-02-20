package com.qcom.search.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.qcom.search.document.TagsFavorites;

public interface TagsFavsRepository extends MongoRepository<TagsFavorites, String>{
	
	@Query("{'user':?0,'appName':?1,'action':?2}")
	List<TagsFavorites> getRecentSearches(String userName,String appName,String action,Pageable request);
	
	@Query("{ 'user':?0,'appName':?1,'action':'bookmark','collectionNames':{$in:?3}}")
	List<TagsFavorites> getUserBookmarksByCollectionName(String userName,String appName,String action,List<String> collectionName);
	
	@Query("{ 'user':?0,'appName':?1,'action':'bookmark'}")
	List<TagsFavorites> getUserBookmarks(String userName,String appName,String action);
	
	@Query("{ 'user':?0,'appName':?1,'action':'like','like':?3}")
	List<TagsFavorites> getUserLikesOrDisLikes(String userName,String appName,String action,Boolean likeOrdislike);
	
	@Query("{ 'user':?0,'appName':?1,'action':'query','_id':?3}")
	TagsFavorites findRecentSearchById(String userName,String appName,String action,String id);
	
	@Query("{ 'user':?0,'appName':?1,'action':'query'}")
	List<TagsFavorites> findRecentSearches(String userName,String appName,String action);
	
}
