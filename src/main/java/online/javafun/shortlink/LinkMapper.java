package online.javafun.shortlink;

import org.springframework.stereotype.Service;

@Service
public class LinkMapper {

    Link map(LinkDto dto) {
        Link link = new Link();
        link.setName(dto.getName());
        link.setTargetUrl(dto.getTargetUrl());
        return link;
    }
}
