package com.amb.wikishare.filter;

import junit.framework.TestCase;
import com.amb.wikishare.mock.HttpServletRequestMock;

public class AuthenticationFilterTests extends TestCase {

    private final String PASS_URL_REGEXP =
        "(/WikiShare/public/(.+?))|(/WikiShare/wiki/frontpage)";
    private final String VALID_IMG_URI = "/WikiShare/public/files/adam1-public.jpg";

    public void testPassRequestedUrl() {
        HttpServletRequestMock request = new HttpServletRequestMock();
        request.setRequestURI(VALID_IMG_URI);
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setPassUrl(PASS_URL_REGEXP);
        assertTrue(filter.passRequestedUrl(request));
    }


}
