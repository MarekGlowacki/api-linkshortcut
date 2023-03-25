package online.javafun.shortlink;

import org.springframework.stereotype.Service;

@Service
public class LinkMapper {

    Link map(LinkCreateDto dto) {
        Link link = new Link();
        link.setName(dto.getName());
        link.setTargetUrl(dto.getTargetUrl());
        link.setPassword(dto.getPassword());
        return link;
    }

    LinkResponseDto map(Link link) {
        LinkResponseDto dto = new LinkResponseDto();
        dto.setId(link.getId());
        dto.setName(link.getName());
        dto.setTargetUrl(link.getTargetUrl());
        dto.setRedirectUrl(link.getRedirectUrl());
        dto.setVisits(link.getVisits());
        return dto;
    }
}
