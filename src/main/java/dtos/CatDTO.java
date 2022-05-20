package dtos;

public class CatDTO {
    private String fact;
    private String url;

    public CatDTO(FactDTO factDTO, CatImageDTO catImageDTO) {
        this.fact = factDTO.getFact();
        this.url = catImageDTO.getUrl();

    }

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
