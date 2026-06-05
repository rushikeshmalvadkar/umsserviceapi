package com.rm.ums.assertions;

import io.restassured.response.Response;

public class VisitUrlAssertions {

    public static void assertVisitUrlFound(Response response,String exceptedVisitUrl){

          response.then()
                  .statusCode(302);
          response.then()
                  .header("Location",exceptedVisitUrl);

    }
}
