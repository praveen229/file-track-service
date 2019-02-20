package com.qcom.search.service;

import java.util.List;
import java.util.Map;

import com.qcom.search.document.TagsFavorites;


public interface TagsFavsService {

	public TagsFavorites create(TagsFavorites saveInputData);
	  
	public void delete(Map<String, Object> searchCriteria);
	 
	public List<TagsFavorites> findAll();
	
	public List<TagsFavorites> find(Map<String, Object> searchCriteria);
	
	public TagsFavorites findById(String id);
	
	public TagsFavorites update(TagsFavorites tagsFavsInput);
	
	public List<TagsFavorites> findRecentSearches(String userId, String appName, String action,int limit);
	
	public TagsFavorites  clearRecentSearches(String userId, String appName, String action, String id);
	
	public void saveBookmarkCollections(TagsFavorites saveInputData);
	
	public List<TagsFavorites> getUserBookmarks(String userId, String appName, String action);

	public List<TagsFavorites> getUserBookmarksByCollectionName(String userId, String appName, String action,List<String> collectionName);

	public List<TagsFavorites> getLikeOrDislikeUrls(String userId, String appName, String action, Boolean likeOrDislike);

	public void clearAllRecentSearches(String userId, String appName, String action);

	
}
