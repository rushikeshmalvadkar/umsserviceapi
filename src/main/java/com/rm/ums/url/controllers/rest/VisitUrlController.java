package com.rm.ums.url.controllers.rest;

import com.rm.ums.url.enums.VisitUrlStatusEnum;
import com.rm.ums.url.model.response.UrlVisitResponse;
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
    public String visit(@PathVariable("slug") String slug, Model model) {

        UrlVisitResponse urlVisitResponse = visitUrlService.visit(slug);
        if (urlAvailableIn(urlVisitResponse)) {
            String originalUrl = urlVisitResponse.getOriginalUrl();
            log.info("URL found for slug :: {} URL :: {}", slug, originalUrl);
            return "redirect:" + originalUrl;
        }
        log.info("URL not found for slug :: {}", slug);
        model.addAttribute("message", VisitUrlStatusEnum.NOT_FOUND.getMessage());
        return "visit-url-error";
    }

    private static boolean urlAvailableIn(UrlVisitResponse urlVisitResponse) {
        return urlVisitResponse.getVisitStatus() == VisitUrlStatusEnum.FOUND;
    }

}
