package net.jojoaddison.xmserv.security;

import java.util.Arrays;
import java.util.List;


public class ResourcesConstant {
	public enum Resource {
        USER_RESOURCE,
        ROLE_RESOURCE,
        SERVICE_RESOURCE,
        GALLERY_RESOURCE,
        PARTNER_RESOURCE,
        SUBSCRIBER_RESOURCE,
        ARTICLE_RESOURCE,
        SHOP_RESOURCE,
        ALBUM_RESOURCE
        /* jhipster-needle-resource-add-item */
    }

    public static List<Resource> getAllResources() {
        return Arrays.asList(Resource.values());
    }
}
