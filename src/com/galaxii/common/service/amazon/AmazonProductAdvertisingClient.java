package com.galaxii.common.service.amazon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.galaxii.common.dto.AmazonItem;
import com.galaxii.common.dto.AmazonItemImage;

/**
 * @see http://www.coderanch.com/t/553315/Spring/access-properties-file-values-Spring
 */
@Scope("prototype")
@Service
public class AmazonProductAdvertisingClient {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Value("#{appProperties[ 'app.amazon.apiKey' ]}") 
    private String accessKeyId;
	@Value("#{appProperties[ 'app.amazon.secretKey' ]}")
    private String secretKey;
	@Value("#{appProperties[ 'app.amazon.associateTag' ]}")
	private String associateTag;
	private CountryCode countryCode = CountryCode.JA;
	
    /*  
     * Use one of the following end-points, according to the region you are
     * interested in:
     * 
     *      US: ecs.amazonaws.com 
     *      CA: ecs.amazonaws.ca 
     *      UK: ecs.amazonaws.co.uk 
     *      DE: ecs.amazonaws.de 
     *      FR: ecs.amazonaws.fr 
     *      JP: ecs.amazonaws.jp
     * 
     */
	public enum CountryCode {
		US("ecs.amazonaws.com"),
		CA("ecs.amazonaws.ca"),
		UK("ecs.amazonaws.co.uk"),
		DE("ecs.amazonaws.de"),
		FR("ecs.amazonaws.fr"),
		JA("ecs.amazonaws.jp");

		private String endpoint;

		CountryCode(String endpoint) {
			this.endpoint = endpoint;
		}

		public String getEndpoint() {
			return endpoint;
		}
	}
	
	public AmazonProductAdvertisingClient() {
		
	}
	
	public AmazonProductAdvertisingClient(
			String accessKeyId, CountryCode countryCode, String secretKey, String associateTag) {
		this.accessKeyId = accessKeyId;
		this.secretKey = secretKey;
		this.countryCode = countryCode;
		this.associateTag = associateTag;
	}
	
	public AmazonItem findByAsin(String asin) throws Exception {
        /*  
         * Set up the signed requests helper 
         */
        SignedRequestsHelper helper;
        try {
            helper = SignedRequestsHelper.getInstance(
				countryCode.getEndpoint(), accessKeyId, secretKey);
        } catch (Exception e) {
			throw e;
        }

		Map<String, String> params = getCommonParams();
        params.put("Operation", "ItemLookup");
		params.put("ItemId", asin);
        params.put("ResponseGroup", "Medium");

		Document doc = fetch(helper.sign(params));
		List<AmazonItem> amazonItems = perse(doc);
		return amazonItems == null ? null : amazonItems.get(0);
	}

	public List<AmazonItem> itemSearch(String keyword) throws Exception {
        /*  
         * Set up the signed requests helper 
         */
        SignedRequestsHelper helper;
        try {
            helper = SignedRequestsHelper.getInstance(
				countryCode.getEndpoint(), accessKeyId, secretKey);
        } catch (Exception e) {
			throw e;
        }

		Map<String, String> params = getCommonParams();
        params.put("Operation", "ItemSearch");
		params.put("SearchIndex", "All");
		params.put("Keywords", keyword);
        params.put("ResponseGroup", "Medium");

		Document doc = fetch(helper.sign(params));
		return perse(doc);
	}
	
	private List<AmazonItem> perse(Document doc) {
        if(doc == null) {
            return null;
        }   
        NodeList items = doc.getElementsByTagName("Item");
        if(items == null || items.getLength() <= 0) {
            return null;
        }
        List<AmazonItem> amazonItems = new ArrayList<AmazonItem>();
        for(int i=0; i<items.getLength(); i++) {
            Element item = (Element)items.item(i);
            AmazonItem row = new AmazonItem();
            row.setAsin(
            	item.getElementsByTagName("ASIN").item(0).getTextContent() );
            row.setTitle(
                item.getElementsByTagName("Title").item(0).getTextContent() );
            row.setDetailPageUrl(
                item.getElementsByTagName("DetailPageURL").item(0).getTextContent() );
            /*
            row.setPublishedAt(DateUtils.parseDate(
                item.getElementsByTagName("PublicationDate").item(0).getTextContent(),
                "yyyy-MM-dd", "yyyy-MM"));
                */
            row.setProductGroup(
                item.getElementsByTagName("ProductGroup").item(0).getTextContent() );

            AmazonItemImage amazonItemImage = new AmazonItemImage();
            NodeList mImages = item.getElementsByTagName("MediumImage");
            if(mImages != null && 0 < mImages.getLength() ) { 
                Element mImage = (Element)mImages.item(0);
                amazonItemImage.setUrl(
                    mImage.getElementsByTagName("URL").item(0).getTextContent() );
                amazonItemImage.setWidth(
                	Integer.parseInt(mImage.getElementsByTagName("Width").item(0).getTextContent()));
                amazonItemImage.setHeight(
                    	Integer.parseInt(mImage.getElementsByTagName("Height").item(0).getTextContent()));
                row.setMediumImage(amazonItemImage);
            }
            
            AmazonItemImage amazonItemLImage = new AmazonItemImage();
            NodeList lImages = item.getElementsByTagName("LargeImage");
            if(lImages != null && 0 < lImages.getLength() ) { 
                Element lImage = (Element)lImages.item(0);
                amazonItemLImage.setUrl(
                    lImage.getElementsByTagName("URL").item(0).getTextContent() );
                amazonItemLImage.setWidth(
                	Integer.parseInt(lImage.getElementsByTagName("Width").item(0).getTextContent()));
                amazonItemLImage.setHeight(
                    	Integer.parseInt(lImage.getElementsByTagName("Height").item(0).getTextContent()));
                row.setLargeImage(amazonItemLImage);
            }   

            amazonItems.add(row);
        }   
        return amazonItems;
	}

	private Map<String, String> getCommonParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Version", "2009-03-31");
		params.put("AssociateTag", associateTag);
		return params;
	}

	private static Object lock = new Object();

    private static Document fetch(String requestUrl) throws Exception {
	  synchronized(lock) {
		// https://images-na.ssl-images-amazon.com/images/G/09/associates/paapi/dg/index.html?ErrorNumbers.html
		Thread.sleep( 1 * 1000 );

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
			return db.parse(requestUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	  }
    }
}
