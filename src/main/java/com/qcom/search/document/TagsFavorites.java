package com.qcom.search.document;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "favs")
public class TagsFavorites {
	
	@Id
	public String id;
	
	@NotBlank @NotEmpty
	public String user;
	
	@NotBlank @NotEmpty
	public String appName;
	
	public String url;
	public String quryTerm;
	public Boolean like;
	
	public Boolean bookmark;
	public String bookmarkTitle;
	public List<String> collectionNames;
	public String sourceName;
	
	@NotBlank @NotEmpty
	public String action;
	
	public Date createdDate = new Date();
	public Date modifiedDate = new Date();
	
	public String urlPosition;
	public String pageNumber;
	public String resultsPerPage;
	public String clickZone;
	public String group;
	public String filter;
	
	//clear query status
	public Boolean status;
	
	private Map<String, String>  additionalData = new  HashMap<String,String>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getQuryTerm() {
		return quryTerm;
	}

	public void setQuryTerm(String quryTerm) {
		this.quryTerm = quryTerm;
	}

	public Boolean getLike() {
		return like;
	}

	public void setLike(Boolean like) {
		this.like = like;
	}

	public Boolean getBookmark() {
		return bookmark;
	}

	public void setBookmark(Boolean bookmark) {
		this.bookmark = bookmark;
	}

	public String getBookmarkTitle() {
		return bookmarkTitle;
	}

	public void setBookmarkTitle(String bookmarkTitle) {
		this.bookmarkTitle = bookmarkTitle;
	}

	public List<String> getCollectionNames() {
		return collectionNames;
	}

	public void setCollectionNames(List<String> collectionNames) {
		this.collectionNames = collectionNames;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getUrlPosition() {
		return urlPosition;
	}

	public void setUrlPosition(String urlPosition) {
		this.urlPosition = urlPosition;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getResultsPerPage() {
		return resultsPerPage;
	}

	public void setResultsPerPage(String resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}

	public String getClickZone() {
		return clickZone;
	}

	public void setClickZone(String clickZone) {
		this.clickZone = clickZone;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Map<String, String> getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(Map<String, String> additionalData) {
		this.additionalData = additionalData;
	}
}

