package online.javafun.shortlink;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;
import java.util.Random;

@Service
public class LinkService {
    private final LinkRepository linkRepository;
    private final LinkMapper linkMapper;

    public LinkService(LinkRepository linkRepository, LinkMapper linkMapper) {
        this.linkRepository = linkRepository;
        this.linkMapper = linkMapper;
    }

    Link save(LinkDto linkDto) {
        String generatedId = "";
        do {
            generatedId = generateId();
        } while (linkRepository.existsById(generatedId));
        Link link = linkMapper.map(linkDto);
        link.setId(generatedId);
        link.setRedirectUrl(buildRedirectUrlFromId(generatedId));
        link.setVisits(0L);
        return linkRepository.save(link);
    }

    private String generateId() {
        StringBuilder randomId = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int randomNumber = random.nextInt(10);
            char randomLetter = (char) (randomNumber + 'a');
            randomId.append(randomLetter);
            randomId.append(randomNumber);
        }
        return randomId.toString();
    }

    Optional<Link> findById(String id) {
        return linkRepository.findById(id);
    }

    @Transactional
    public Optional<Link> incrementVisitsById(String id) {
        Optional<Link> link = linkRepository.findById(id);
        link.ifPresent(l -> l.setVisits(l.getVisits() + 1));
        return link;
    }

    private static String buildRedirectUrlFromId(String id) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/redir/{id}")
                .buildAndExpand(id)
                .toUriString();
    }
}
