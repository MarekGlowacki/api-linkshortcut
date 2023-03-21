package online.javafun.shortlink;

import org.springframework.stereotype.Service;

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
        String generatedId = generateId();
        String redirectLinkBeginning = "http://localhost:8080/redir/";
        Link link = linkMapper.map(linkDto);
        link.setId(generatedId);
        link.setRedirectUrl(redirectLinkBeginning + generatedId);
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

    void addVisit(Link link) {
        link.setVisits(link.getVisits() + 1);
        linkRepository.save(link);
    }
}
