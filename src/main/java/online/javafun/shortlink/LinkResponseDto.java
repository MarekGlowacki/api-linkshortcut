package online.javafun.shortlink;

public class LinkResponseDto {
    private String id;
    private String name;
    private String targetUrl;
    private String redirectUrl;
    private Long visits;

    public LinkResponseDto(String id, String name, String targetUrl, String redirectUrl, Long visits) {
        this.id = id;
        this.name = name;
        this.targetUrl = targetUrl;
        this.redirectUrl = redirectUrl;
        this.visits = visits;
    }

    public LinkResponseDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Long getVisits() {
        return visits;
    }

    public void setVisits(Long visits) {
        this.visits = visits;
    }
}
