package com.rm.ums.url.controllers.rest;

import com.rm.ums.url.model.response.VisitUrlResponse;
import com.rm.ums.url.services.VisitUrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/ums/urls")
@RequiredArgsConstructor
@Slf4j
public class VisitUrlController {

    private static final String ENDPOINT_VISIT_URL = "/visit-url/{slug}";
    private final VisitUrlService visitUrlService;

    @GetMapping(ENDPOINT_VISIT_URL)
    public String visitUrl(@PathVariable String slug, Model model) {
        VisitUrlResponse visitUrlResponse = visitUrlService.visitUrl(slug);
        return switch (visitUrlResponse.urlStatusEnum()) {
            case VALID_SLUG -> forValidSlug(visitUrlResponse);
            case UNKNOWN_SLUG, INACTIVE_SLUG -> forNotValidSlug(visitUrlResponse, model);
            case SHORT_URL_EXPIRED -> urlExpired(visitUrlResponse, model);
            case SHORT_URL_NOT_AVAILABLE_YET -> urlNotAvailableYet(visitUrlResponse,model);
        };
    }

    private static String forNotValidSlug(VisitUrlResponse visitUrlResponse, Model model) {
        model.addAttribute("message", visitUrlResponse.urlStatusEnum().message());
        return "visit-url-error";
    }

    private static String  urlExpired(VisitUrlResponse visitUrlResponse, Model model) {
        model.addAttribute("message", visitUrlResponse.urlStatusEnum().message());
        return "visit-url-error";
    }

    private static String urlNotAvailableYet(VisitUrlResponse visitUrlResponse, Model model) {
        model.addAttribute("message", visitUrlResponse.urlStatusEnum().message());
        return "visit-url-error";
    }

    private static String forValidSlug(VisitUrlResponse visitUrlResponse) {
        return "redirect:" + visitUrlResponse.originalUrl();
    }
}
