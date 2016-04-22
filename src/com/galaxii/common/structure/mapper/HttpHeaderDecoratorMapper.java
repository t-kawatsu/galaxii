package com.galaxii.common.structure.mapper;
 
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
 
import javax.servlet.http.HttpServletRequest;
 

import com.opensymphony.module.sitemesh.Config;
import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.DecoratorMapper;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.mapper.AbstractDecoratorMapper;
 
/**
 * http://c0ffee.me/sitemesh-2-undecorated-ajax-requests/
 */
public class HttpHeaderDecoratorMapper extends AbstractDecoratorMapper {
	
    private Map<String, String> headerMap = null;
 
    public void init(Config config, Properties properties, DecoratorMapper parent) 
    		throws InstantiationException {
        super.init(config, properties, parent);
        headerMap = new HashMap<String, String>();
        initMap(properties);
    }
 
    public Decorator getDecorator(HttpServletRequest request, Page page) {
        try {
            Decorator result = null;
 
            final List<String> headers = Collections.list(request.getHeaderNames());
            for(final String header : headerMap.keySet()) {
                if(headers.contains(header) && 
                  request.getHeader(header).matches(headerMap.get(header)) ) {
                    result = super.getNamedDecorator(request, headerMap.get("decorator"));
                    break;
                }
            }
 
            return result == null ? super.getDecorator(request, page) : result;
        }
        catch (NullPointerException e) {
            return super.getDecorator(request, page);
        }
    }
 
    /** Initialize the header mappings. */
    private void initMap(final Properties props) {
        final Iterator<Entry<Object, Object>> it = props.entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) it.next();
            final String key = (String) entry.getKey();
            final String ext = (String) entry.getValue();
            headerMap.put(key, ext);
        }
    }
}