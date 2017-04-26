package net.jojoaddison.xmserv.security;

import java.util.Arrays;
import java.util.List;


public class ResourcesConstant {
	public enum Resource {
        USER_RESOURCE,
        ROLE_RESOURCE
        /* jhipster-needle-resource-add-item */
    }

    public static List<Resource> getAllResources() {
        return Arrays.asList(Resource.values());
    }
}
