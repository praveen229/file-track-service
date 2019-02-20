package com.qcom.search.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.qcom.search.document.TagsFavorites;
import com.qcom.search.exception.CustomErrorType;
import com.qcom.search.exception.TagsFavsServiceException;
import com.qcom.search.service.TagsFavsService;

@RestController
@RequestMapping("/favs/")
public class TagsFavServicesController {
	
	private static final Logger LOGGER = LogManager.getLogger(TagsFavServicesController.class);
	
	@Autowired
	private final TagsFavsService service=null;
	
	public TagsFavServicesController() {
		
	}
	
    @PostMapping(value="create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody @Valid TagsFavorites tagsFavs,UriComponentsBuilder ucBuilder) {
    	LOGGER.info("Creating TagsFavorites : {}", tagsFavs);
        service.create(tagsFavs);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/favs/{id}").buildAndExpand(tagsFavs.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
 
    
    @PutMapping(value = "update/{id}")
    public TagsFavorites update(@PathVariable String id,@RequestBody @Valid TagsFavorites tagsFavorites) {
    	tagsFavorites.setId(id);
    	return service.update(tagsFavorites);
    }
    
    
	@GetMapping(value = "find/{id}")
    public ResponseEntity<?> getTagFavsById(@PathVariable String id) {
        LOGGER.info("Fetching TagsFavorites  with id {}", id);
        TagsFavorites result = service.findById(id);
        if (result == null) {
        	LOGGER.error("Results not found with ", id);
            return new ResponseEntity<>(new CustomErrorType("Results with id " + id   + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TagsFavorites>(result, HttpStatus.OK);
    }
 
    @PostMapping(value = "saveBookmarkCollections")
    public ResponseEntity<?> saveBookmarkCollections(@RequestBody @Valid TagsFavorites tagsFavorites,UriComponentsBuilder ucBuilder) {
    	LOGGER.info("Creating TagsFavorites : {}", tagsFavorites);
        service.saveBookmarkCollections(tagsFavorites);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/find/{id}").buildAndExpand(tagsFavorites.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
	@GetMapping(value="getRecentQueries/{user}/{appName}/{action}")
    public ResponseEntity<?> getRecentQueries(@PathVariable String user,@PathVariable String appName,@PathVariable String action,@RequestParam("limit") int limit) {
    	List<TagsFavorites> searchResults =	service.findRecentSearches(user,appName,action,limit);
    	  if (searchResults.isEmpty()) {
              return new ResponseEntity<>(HttpStatus.NO_CONTENT);
              // You many decide to return HttpStatus.NOT_FOUND
          }
          return new ResponseEntity<List<TagsFavorites>>(searchResults, HttpStatus.OK);     
    }
    
    @GetMapping(value="clearRecentSearch/{user}/{appName}/{action}")
    public ResponseEntity<?> clearRecentSearch(@PathVariable String user,@PathVariable String appName,@PathVariable String action,
    											  @RequestParam("id") String id ) {
    	LOGGER.info(" Deleting TagsFavs Record with id "+id);
    	service.clearRecentSearches(user, appName, action, id);
        return new ResponseEntity<TagsFavorites>(HttpStatus.NO_CONTENT);       
    }
  
	@GetMapping(value="clearAllRecentSearches/{user}/{appName}/{action}")
    public ResponseEntity<?> clearAllRecentSearches(@PathVariable String user,@PathVariable String appName,@PathVariable String action) {
    	LOGGER.info(" Deleting TagsFavs Records ");
    	if(action.equalsIgnoreCase("query")) {
    		service.clearAllRecentSearches(user, appName, action);
    		return new ResponseEntity<TagsFavorites>(HttpStatus.NO_CONTENT);  		
    	}
    	return new ResponseEntity<>(new CustomErrorType(" Clear All option is only for action Query"), HttpStatus.NOT_FOUND); 
    	
    }
  
    
	@GetMapping(value="getUserBookmarks/{user}/{appName}/{action}")
    public ResponseEntity<?> getUserBookmarks(@PathVariable String user,@PathVariable String appName,@PathVariable String action) {
    	List<TagsFavorites> searchResults =	service.getUserBookmarks(user, appName, action);
    	  if (searchResults.isEmpty()) {
              return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }
          return new ResponseEntity<List<TagsFavorites>>(searchResults, HttpStatus.OK);           
    }
    
	@GetMapping(value = "getUserBookmarksByCollectionName/{user}/{appName}/{action}")
	public ResponseEntity<?> getUserBookmarksByCollectionName(@PathVariable String user, @PathVariable String appName,
			@PathVariable String action, @RequestParam("collectionName") String collectionName) {
		List<TagsFavorites> searchResults = null;
		if (!StringUtils.isEmpty(collectionName)) {
			List<String> collectionNames = Arrays.asList(collectionName.split(","));
			searchResults = service.getUserBookmarksByCollectionName(user, appName, action, collectionNames);
			  if (searchResults.isEmpty()) {
	              return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	          }
	          return new ResponseEntity<List<TagsFavorites>>(searchResults, HttpStatus.OK);    
		} else {
			
			return ResponseEntity.badRequest().body("Please Provide Collection Name ");
		}
	}

	@GetMapping(value="getLikeOrDislikeUrls/{user}/{appName}/{action}")
    public ResponseEntity<?> getLikeOrDislikeUrls(@PathVariable String user,@PathVariable String appName,@PathVariable String action,
    											  @RequestParam("likeOrdislike") Boolean likeOrdislike) {
    	List<TagsFavorites> searchResults =	service.getLikeOrDislikeUrls(user, appName, action, likeOrdislike);
    	  if (searchResults.isEmpty()) {
              return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }
          return new ResponseEntity<List<TagsFavorites>>(searchResults, HttpStatus.OK);      
    }
    
    
    @DeleteMapping(value="delete")
    public ResponseEntity<String> delete(@RequestBody Map<String, Object> searchCriteria) {
    	if(!searchCriteria.containsKey("operator"))
   		 return ResponseEntity.badRequest().body("Operator is missing");
        service.delete(searchCriteria);
        return ResponseEntity.ok().body("successfully deleted ");
    }
    
	@PostMapping(value="search")
    public ResponseEntity<?> findBy(@RequestBody Map<String, Object> searchCriteria ) {
    	if(!searchCriteria.containsKey("operator"))
    	return ResponseEntity.badRequest().body("Operator is missing");
    	List<TagsFavorites> searchResults =	service.find(searchCriteria);
    	if (searchResults.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<TagsFavorites>>(searchResults, HttpStatus.OK);
    }
  
  
   @ExceptionHandler
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public void handleTodoNotFound(TagsFavsServiceException ex) {
        LOGGER.error("Handling error with message: {}", ex.getMessage());
    }
   
}
