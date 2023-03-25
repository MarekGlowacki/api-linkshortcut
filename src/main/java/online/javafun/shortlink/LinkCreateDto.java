package online.javafun.shortlink;

public class LinkCreateDto {
    private String name;
    private String targetUrl;
    private String password;

    public LinkCreateDto(String name, String targetUrl, String password) {
        this.name = name;
        this.targetUrl = targetUrl;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
