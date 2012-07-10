package org.broadleafcommerce.cms.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.cms.page.dto.PageDTO;
import org.broadleafcommerce.cms.page.service.PageService;
import org.broadleafcommerce.cms.web.controller.BroadleafPageController;
import org.broadleafcommerce.common.web.BLCAbstractHandlerMapping;
import org.broadleafcommerce.common.web.BroadleafRequestContext;

/**
 * This handler mapping works with the Page entity to determine if a page has been configured for
 * the passed in URL.   
 * 
 * If the URL represents a valid PageUrl, then this mapping returns 
 * 
 * Allows configuration of the controller name to use if a Page was found.
 *
 * @author bpolster
 * @since 2.0
 * @see org.broadleafcommerce.cms.page.domain.Page
 * @see BroadleafPageController
 */
public class PageHandlerMapping extends BLCAbstractHandlerMapping {
	
	private String controllerName="blPageController";
	
    @Resource(name = "blPageService")
    private PageService pageService;
    
    public static final String PAGE_ATTRIBUTE_NAME = "BLC_PAGE";

	@Override
	protected Object getHandlerInternal(HttpServletRequest request)
			throws Exception {		
		BroadleafRequestContext context = BroadleafRequestContext.getBroadleafRequestContext();
        PageDTO p = pageService.findPageByURI(context.getSandbox(), context.getLocale(), context.getRequestURIWithoutContext(), context.isSecure());
        
        if (p != null) {
            context.getRequest().setAttribute(PAGE_ATTRIBUTE_NAME, p);
        	return controllerName;
        } else {
        	return null;
        }
	}
}