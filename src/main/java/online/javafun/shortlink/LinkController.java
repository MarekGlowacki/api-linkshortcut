package online.javafun.shortlink;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
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
        return linkService.incrementVisitsById(id)
                .map(Link::getTargetUrl)
                .map(targetUrl -> ResponseEntity
                        .status(HttpStatus.FOUND)
                        .location(URI.create(targetUrl))
                        .build())
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/api/links/{id}")
    ResponseEntity<?> linkInformation(@PathVariable String id) {
        return linkService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
