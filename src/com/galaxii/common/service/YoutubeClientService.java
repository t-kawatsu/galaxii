package com.galaxii.common.service;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.io.IOException;

import com.galaxii.common.dto.YoutubeItem;
import com.galaxii.common.dto.YoutubeItemImage;
import com.google.gdata.client.youtube.*;
import com.google.gdata.data.media.mediarss.*;
import com.google.gdata.data.youtube.*;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@Service
// https://developers.google.com/gdata/articles/eclipse?hl=ja
// http://code.google.com/p/google-collections/
public class YoutubeClientService {

	private YouTubeService ytService = new YouTubeService(null);
	private static final String SEARCH_VIDEO_URL = "http://gdata.youtube.com/feeds/api/videos";
	private static final int MAX_RESULT_NUM = 10;
	
	public YoutubeItem findVideoEntryById(String id) throws IOException, Exception {
		String url = SEARCH_VIDEO_URL + "/" + id;
		VideoEntry videoEntry = ytService.getEntry(new URL(url), VideoEntry.class);
		if(videoEntry == null) {
			return null;
		}
		return perseEntry(videoEntry);
	}
	
	public YoutubeItem findVideoEntryByUrl(String url) throws IOException, Exception {
		URL u = new URL(url);
		String entryId = null;
		if(ArrayUtils.contains(new String[]{
			"www.youtube.com", "youtube.com"
		}, u.getHost())) {
		    String[] params = u.getQuery().split("&");  
		    Map<String, String> map = new HashMap<String, String>();  
		    for (String param : params) {  
		        String[] p = param.split("=");  
		        map.put(p[0], p[1]);  
		    }
		    entryId = map.get("v");
		} else if(ArrayUtils.contains(new String[]{
			"www.youtu.be", "youtu.be"
		}, u.getHost())) {
			entryId = StringUtils.trimLeadingCharacter(u.getPath(), '/');
		}
		if(entryId == null) {
			return null;
		}
		return findVideoEntryById(entryId);
	}
	
	public List<YoutubeItem> search(String word) throws IOException, Exception {
		YouTubeQuery query = new YouTubeQuery(new URL( SEARCH_VIDEO_URL ));
		query.setOrderBy(YouTubeQuery.OrderBy.PUBLISHED);
		query.setSafeSearch(YouTubeQuery.SafeSearch.STRICT);
		query.setFullTextQuery(word);
		query.setMaxResults(MAX_RESULT_NUM);
		
		VideoFeed videoFeed = ytService.query(query, VideoFeed.class);
		if(videoFeed == null) {
			return null;
		}

		List<YoutubeItem> youtubeItems = new ArrayList<YoutubeItem>();
		for(VideoEntry entry : videoFeed.getEntries()) {
			youtubeItems.add(perseEntry(entry));
		}
		return youtubeItems;
	}

	private YoutubeItem perseEntry(VideoEntry entry) {
		YoutubeItem youtubeItem = new YoutubeItem();
		String[] idMeta = entry.getId().split(":");
		youtubeItem.setEntryId(idMeta[idMeta.length-1]);
		youtubeItem.setTitle(entry.getTitle().getPlainText());
		youtubeItem.setDescription(entry.getMediaGroup().getDescription().getPlainTextContent());
		if(entry.getMediaGroup().getThumbnails() != null) {
			for(MediaThumbnail mt : entry.getMediaGroup().getThumbnails()) {
				YoutubeItemImage youtubeItemImage = new YoutubeItemImage();
				youtubeItemImage.setUrl(mt.getUrl());
				youtubeItemImage.setWidth(mt.getWidth());
				youtubeItemImage.setHeight(mt.getHeight());
				youtubeItem.setImage(youtubeItemImage);
				break;
			}
		}
		return youtubeItem;
	}
}
