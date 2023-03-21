package online.javafun.shortlink;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Controller
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/api/links")
    ResponseEntity<Link> saveLink(@RequestBody LinkDto linkDto) {
        Link savedLink = linkService.save(linkDto);
        URI savedLinkUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedLink.getId())
                .toUri();
        return ResponseEntity.created(savedLinkUri).body(savedLink);
    }

    @GetMapping("/redir/{id}")
    ResponseEntity<?> redirect(@PathVariable String id) {
        Optional<Link> foundedLinkOptional = linkService.findById(id);
        if (foundedLinkOptional.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            Link foundedLink = foundedLinkOptional.get();
            headers.add("Location", foundedLink.getTargetUrl());
            linkService.addVisit(foundedLink);
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/api/links/{id}")
    ResponseEntity<?> linkInformation(@PathVariable String id) {
        return linkService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
