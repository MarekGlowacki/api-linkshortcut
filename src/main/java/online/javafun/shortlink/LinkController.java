package online.javafun.shortlink;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    ResponseEntity<LinkResponseDto> saveLink(@RequestBody LinkCreateDto linkCreateDto) {
        LinkResponseDto savedLink = linkService.save(linkCreateDto);
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

    @PatchMapping("/{id}")
    ResponseEntity<?> update(@PathVariable String id,
                             @RequestBody LinkCreateDto link) {
        try {
            linkService.updateLink(id, link);
            return ResponseEntity.noContent().build();
        } catch (LinkNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .header("reason", e.getMessage())
                    .build();
        }
    }
}
